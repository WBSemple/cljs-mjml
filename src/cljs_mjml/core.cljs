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
  (if (sequential? x)
    (if (keyword? (first x))
      (let [tag (name (first x))
            [attrs & children] (cond-> (rest x) (not (map? (second x))) (conj nil))]
        (str "<" tag (->xml-attrs attrs) ">\n"
             (str/join (map ->xml children))
             "</" tag ">\n"))
      (str/join (map ->xml x)))
    (str (escape-html x) "\n")))

(defn render+
  [options hiccup]
  (-> (mjml2html (->xml hiccup) (clj->js options))
      (.then #(js->clj % :keywordize-keys true))))
