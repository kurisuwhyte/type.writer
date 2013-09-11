(ns typewriter.project
  "Handles type.writer projects."
  (:use [typewriter.logging])
  (:require [fs.core :as fs])
  (:require [clojure.java.io :as jio])
  (:require [clojure.edn :as edn])
  (:require [clj-yaml.core :as yaml])
  (:require [typewriter.processing :as tp]))


(defn- valid-template?
  "Checks if a template exists, given its pathname in the CLASSPATH."
  [path]
  (and path
       (fs/exists? path)
       (fs/exists? (fs/file path "project.edn"))))


(defn- initialise-project
  "Initialises a project at the `dest-dir` using `source-dir` as basis."
  [dest-dir source-dir]
  (info "Copying files over to" (str (fs/normalized-path dest-dir)))
  (fs/copy-dir source-dir dest-dir)
  (message :project/initialised-successfully {:dest (str dest-dir)}))


(defn- find-project-root
  "Finds the closest root for a type.writter project."
  [path]
  (cond
   (fs/exists? (fs/file path "project.edn"))    path
   (fs/parent path)                             (recur (fs/parent path))
   :else                                        nil))


(defn- as-html
  "Renames a file to HTML."
  [file]
  (str (fs/name file) ".html"))


(defn- parse-meta-data
  "Parses metadata from a text."
  [text]
  (let [[_ meta contents] (re-matches #"(?s)---\n(.*?)\n---\n(.*)" text)]
    [(yaml/parse-string meta) contents]))


(defn- parse-file
  "Parses meta-data and contents from a document file."
  [text]
  (if (.startsWith text "---\n")  (parse-meta-data text)
      [nil text]))


(defn- load-layout
  "Reads a layout from the current project."
  [name]
  (if (nil? name)  (fatal "A :layout field must be defined in the project or markdown source.")
      (let [path (fs/file "layout" name)]
        (if (not (fs/exists? path))  (fatal "Unknow layout" name)
            (tp/as-template (slurp path))))))


(defn- compile-file
  "Compiles a file and returns the HTML output."
  [project source]
  (let [[meta contents] (parse-file (slurp source))
        full-meta   (merge project meta)
        layout      (load-layout (:layout full-meta))]
    (tp/render (tp/process full-meta contents layout))))


(defn- build-project
  "Constructs a project."
  [path project]
  (fs/with-cwd path
    (info "Creating the `build` directory.")
    (fs/mkdirs "build")
    (info "Copying all of the static resources.")
    (fs/copy-dir "static" "build")
    (doseq [file (fs/glob "source/*.md")]
      (info "Compiling" (fs/base-name file) "...")
      (let [html (compile-file project file)
            dest (fs/file "build" (as-html file))]
        (spit dest html)))
    (println "Documentation generated successfully. Check the `build` folder.")))


(defn new
  "Initialises a new project in `dest-dir` using `template` as basis."
  [dest-dir template]
  (let [root-dir (jio/resource (str "templates/" template))]
    (if (valid-template? root-dir)
      (do
        (info "Using template" template)
        (initialise-project dest-dir root-dir))
      (fatal "Not a valid type.writter template:" template))))


(defn build
  "Builds a project at the given directory."
  [path]
  (let [project-path (find-project-root path)]
    (if (nil? project-path)  (fatal "Not inside a type.writer project.")
        (build-project project-path
                       (edn/read-string (slurp (fs/file project-path "project.edn")))))))    








  


  
