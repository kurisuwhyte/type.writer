(defproject typewriter "0.1.0-SNAPSHOT"
  :description "Old-school documentation, meet Simple."
  :url "http://kurisuwhyte.github.io/type.writer"
  :license {:name "MIT"
            :url "http://github.com/kurisuwhyte/type.writer/blob/master/LICENCE"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [enlive "1.1.4"]
                 [docopt "0.6.1"]
                 [fs "1.3.3"]
                 [myguidingstar/clansi "1.3.0"]
                 [de.ubercode.clostache/clostache "1.3.1"]
                 [clj-yaml "0.4.0"]]
  :main typewriter.core)
