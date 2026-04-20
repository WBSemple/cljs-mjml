(ns build
  (:require [clojure.tools.build.api :as b]
            [deps-deploy.deps-deploy :as dd]))

(def lib 'io.github.wbsemple/cljs-mjml)
(def target "target")
(def class-dir (str target "/classes"))

(def pom-template
  [[:licenses
    [:license
     [:name "MIT"]
     [:url "https://opensource.org/license/MIT"]]]])

(def scm
  {:url "https://github.com/WBSemple/cljs-mjml"
   :connection "scm:git:https://github.com/WBSemple/cljs-mjml.git"
   :developerConnection "scm:git:ssh://git@github.com/WBSemple/cljs-mjml.git"})

(defn build [_]
  (b/delete {:path target})
  (let [version "0.1.0-SNAPSHOT"
        basis (dissoc (b/create-basis) :libs) ;; remove unwanted clojure dependency
        jar-file (format "%s/cljs-mjml-%s.jar" target version)]
    (b/write-pom {:basis basis
                  :pom-data pom-template
                  :lib lib
                  :scm scm
                  :class-dir class-dir
                  :version version})
    (b/copy-dir {:src-dirs ["src"]
                 :target-dir class-dir})
    (b/jar {:class-dir class-dir
            :jar-file jar-file})
    jar-file))

(defn release [_]
  (dd/deploy {:installer :remote
              :artifact (build nil)
              :pom-file (b/pom-path {:lib lib :class-dir class-dir})}))
