(ns watch
  (:require [cljs.pprint :as pprint]
            [email :as email]
            [cljs-mjml.core :as cljs-mjml]
            ["fs" :as fs]
            [promesa.core :as p]))

(defn to-file+ []
  (p/let [{:keys [html errors]} (cljs-mjml/render+ {:minify true}
                                                   (email/my-email "Will"))]
    (pprint/pprint errors)
    (when-not (fs/existsSync "target") (fs/mkdirSync "target"))
    (fs/writeFileSync "target/email.html" html)))

(defn -main
  [& _]
  (to-file+)
  (fs/watch "email.cljs"
            #js {:persistent true}
            (fn [event-type filename]
              (println)
              (println event-type filename)
              (-> (nbb.core/load-file "email.cljs")
                  (p/then #(to-file+))
                  (p/catch #(js/console.error %))))))
