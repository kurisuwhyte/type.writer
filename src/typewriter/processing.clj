(ns typewriter.processing
  "Processes template files and enriches it with meta-data and markdown content.

Also makes sure files are copied to the relevant folders, etc."
  (:use [net.cgrand.enlive-html :as h]))


(defn provide-title [title tree]
  (h/at tree
        [:head :title] (h/content title)))

(defn provide-meta [title description tree]
  (-> tree
      (h/at
       [[:meta (attr= :property "og:title")]] (set-attr :content title))
      (h/at
       [[:meta (attr= :property "description")]] (set-attr :content description))))

(defn provide-heading [title tree]
  (h/at tree
        [:.project-title] (h/content title)))

(defn provide-source [source tree]
  (h/at tree
        [:#source] (h/html-content source)))

(defn provide-links [links tree]
  (h/at tree
        [:.project-navigation] (h/content
                                (h/html (map (fn [{text :text url :url}]
                                               [:li [:a {:href url} text]])
                                             links)))))

(defn contextualise [project tree]
  (->> tree
   (provide-title (:title project))
   (provide-meta (:title project) (:description project))
   (provide-heading (:title project))))


(defn process [project source tree]
  (->> tree
       (contextualise project)
       (provide-links (:links project))
       (provide-source source)))


(defn render [tree]
  (apply str (h/emit* tree)))


(defn as-template [html]
  (h/html-resource (java.io.StringReader. html)))
