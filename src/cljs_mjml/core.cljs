(ns cljs-mjml.core
  (:require [clojure.string :as str]
            ["mjml$default" :as mjml2html]))

(defn escape-html
  [s]
  (str/escape (str s) {\& "&amp;"
                       \< "&lt;"
                       \> "&gt;"
                       \" "&quot;"}))

(defn ->xml-attrs
  [m]
  (str/join (map (fn [[k v]] (str " " (name k) "=\"" (escape-html v) "\"")) m)))

(defn ->xml
  [x]
  (cond
    (sequential? x)
    (if (keyword? (first x))
      (let [tag (name (first x))
            [attrs & children] (cond-> (rest x) (not (map? (second x))) (conj nil))]
        (str "<" tag (->xml-attrs attrs) ">\n"
             (str/join (map ->xml children))
             "</" tag ">\n"))
      (str/join (map ->xml x)))

    (nil? x)
    ""

    :else
    (str (escape-html x) "\n")))

(defn render+
  "Render a raw HTML string from a document of hiccup-style MJML

   options
   https://github.com/WBSemple/cljs-mjml#options

   document
   https://github.com/WBSemple/cljs-mjml#syntax
  "
  [options document]
  (-> (mjml2html (->xml document) (clj->js options))
      (.then #(js->clj % :keywordize-keys true))))
