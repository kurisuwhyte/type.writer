(ns essay.core
  (:use [docopt.core :only (parse)])
  (:use [docopt.match :only (match-argv)])
  (:require [fs.core :as fs])
  (:require [clojure.java.io :as jio])
  (:require [clojure.edn :as edn])
  (:require [essay.processing :as ep])
  (:gen-class))

(def help "Essay --- The *essay-est* way of documenting your project.

Usage:
  essay new [template]
  essay generate
  essay -h | --help
  essay --version

Options:
  -h --help     Shows this screen.
  --version     Shows the version number.")


(defn- show-help? [args]
  (or (nil? args)
      (args "--help")))

(defn initialise [template]
  (let [root-dir (jio/resource (str "templates/" template))]
    (println "Copying template files...")
    (doseq [node (fs/list-dir root-dir)]
      (let [source (fs/file root-dir node)]
        (if (fs/directory? source)  (fs/copy-dir source ".")
            (fs/copy source node))))
    (println "Files copied. Edit `project.edn` and run `essay generate`.")))


(defn generate []
  (let [project (edn/read-string (slurp "project.edn"))
        layout  (ep/as-template (slurp "layout/index.html"))]
    (println "Creating build directory.")
    (fs/mkdirs "build")
    (println "Copying static resources.")
    (fs/copy-dir "static" "build")
    (doseq [file (fs/glob "source/*.md")]
      (println "Compiling" (fs/base-name file) "...")
      (spit (fs/file "build/" (str (fs/name file) ".html"))
            (ep/render (ep/process project (slurp file) layout))))
    (println "Finished generating the documentation.")))
      
    

(defn -main [& args]
  (let [arg-map (match-argv (parse help) args)]
    (cond
     (show-help? arg-map)       (println help)
     (arg-map "--version")      (println "Essay 0.1.0")
     (arg-map "new")            (initialise (or (arg-map "template") "default"))
     (arg-map "generate")       (generate))))
