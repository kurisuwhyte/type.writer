(ns typewriter.processing
  "Processes layout files and fill them with data from a source."
  (:use [net.cgrand.enlive-html :as h]))


;;;; Helper functions
(defn- property=
  "Selection predicate for (attr= :property)"
  [name]
  (attr= :property name))


;;;; Basic providers
(defn provide-title 
  "Provides the <title> of a layout."
  [title tree]
  (h/at tree
        [:head :title] (h/content title)))


(defn provide-meta-title
  "Provides content for the <meta og:title> of a layout."
  [text tree]
  (h/at tree
        [[:meta (property= "og:title")]]  (h/set-attr :content text)))


(defn provide-meta-description
  "Provides content for the <meta description> of a layout."
  [text tree]
  (h/at tree
        [[:meta (property= "description")]]  (h/set-attr :content text)))


(defn provide-heading
  "Provides the title of the document."
  [title tree]
  (h/at tree
        [:.tw-title]  (h/content title)))


(defn provide-source
  "Provides the source of the document."
  [source tree]
  (h/at tree
        [:.tw-source]  (h/content source)))


(defn provide-links
  "Provides a link structure."
  [project tree]
  (let [links (:links project)]
    (h/at tree
          [:.tw-navigation :.tw-link] (h/clone-for [{text :text url :url} links]
                                                   [:.tw-link] (h/content text)
                                                   [:.tw-link] (h/set-attr :href url)))))


(defn contextualise
  "Provide contextual/meta information for the layout."
  [project tree]
  (->> tree
       (provide-title (:title project))
       (provide-meta-title (:title project))
       (provide-meta-description (:description project))))


(defn process
  "Processes a layout and fills all fields in."
  [project source tree]
  (->> tree
       (contextualise project)
       (provide-links project)
       (provide-heading (:title project))
       (provide-source source)))


(defn render 
  "Returns an HTML representation of an Enlive tree."
  [tree]
  (apply str (h/emit* tree)))


(defn as-template
  "Reads a string as an Enlive template tree."
  [html]
  (h/html-resource (java.io.StringReader. html)))
