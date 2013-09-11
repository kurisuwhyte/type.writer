(ns typewriter.logging
  (:use [clansi.core :only (style)])
  (:require [clojure.edn :as edn])
  (:require [clojure.java.io :as jio])
  (:require [clostache.parser :as clostache]))


(def ^:dynamic *log-level* :normal)
(def ^:dynamic *exit-on-errors* true)
(def ^:private message-catalog
  (edn/read-string (slurp (jio/resource "messages.edn"))))


(defmacro with-log-level
  "Controls how much information gets displayed to the user.

Can be either `:normal` or `:verbose`. If `:verbose`, all sorts of
useless information will get displayed on the screen. "
  [log-level & forms]
  `(binding [*log-level* ~log-level]
     ~@forms))


(defmacro with-errors-ignored
  "Executes the given forms carrying on even when non-fatal errors occur.

By default, errors will kill the program so that the user can take some
time to fix the problem."
  [flag & forms]
  `(binding [*exit-on-errors* ~flag]
     ~@forms))


(defmacro when-verbose
  "Executes forms only when runnin in --verbose mode."
  [& forms]
  `(when (= *log-level* :verbose)
     ~@forms))


(defn info
  "Displays potentially irrelevant information."
  [& messages]
  (when-verbose
   (print (style "DEBUG:\t" :blue))
   (apply println messages)))


(defn warn
  "Displays warning information.

These are likely relevant, but not catastrophic."
  [& messages]
  (print (style "WARNING:\t" :yellow :bright))
  (apply println messages))


(defn error
  "Displays an error message and exits.

These are provided as a way to describe unexpected events, but which may
be circumvented by the user. If a `--force` flag is in place, we'll
carry on with the operation."
  [& messages]
  (print (style "ERROR:\t" :red :bright))
  (apply println messages)
  (if *exit-on-errors*
    (System/exit 1)))


(defn fatal
  "Displays a fatal error message and exits.

These will kill the program even if a `--force` flag is in place,
because there is no possible ways of carrying the current operation."
  [& messages]
  (print (style "FATAL:\t" :red :bright))
  (apply println messages)
  (System/exit 2))
  

(defn message
  "Renders a message from the catalog.

This is used to display long messages in a sane way. All of the messages
are stored in the `messages.edn` file in the CLASSPATH."
  [kind args]
  (let [text (get message-catalog kind)]
    (if (nil? text)  (fatal "Unknown message" kind)
        (do
          (println)
          (println
           (clostache/render text
                             (merge args
                                    {:verbose (= *log-level* :verbose)})))))))
