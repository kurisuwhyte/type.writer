(ns typewriter.core
  (:use [docopt.core :only (parse)])
  (:use [docopt.match :only (match-argv)])
  (:use [typewriter.logging])
  (:require [typewriter.project :as project])
  (:gen-class))


(def help "type.writer --- Old-school documentation, meet Simple.

Usage:
  typewriter new <project-name> [--template=NAME] [options]
  typewriter build [options]
  typewriter templates [options]
  typewriter -h | --help
  typewriter --version

Options:
  --template=NAME       The name of the template [default: default]
  -h --help             Shows this screen.
  --version             Shows the version number.
  -v --verbose          Shows all the useless stuff.
  --force               Carries on when non-fatal errors occur.")


(defmacro version
  "An utility to grab the version from project.clj & intern it.

This will be compiled when AOT'd, so all's right with the world."
  []
  (nth (read-string (slurp "project.clj")) 2))


(defn- extract-log-level
  "Returns the correct log-level for the loggings library."
  [arg-map]
  (if (arg-map "--verbose") :verbose
      :normal))


(defn -main [& args]
  (let [arg-map (match-argv (parse help) args)]
    (if (nil? arg-map)  (do (println help) (System/exit 1))
      (with-log-level (extract-log-level arg-map)
        (with-errors-ignored (arg-map "--force")
          (cond
           (arg-map "--help")           (println help)
           (arg-map "--version")        (println "type.writer" (version))
           (arg-map "new")              (project/new (arg-map "<project-name>")
                                                     (arg-map "--template"))
           (arg-map "build")            (project/build ".")
     ))))))
