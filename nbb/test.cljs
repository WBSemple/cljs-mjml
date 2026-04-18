(ns test
  (:require ["../target/cljs-mjml.js" :as render]))

(render/default {:foo "bar"} [:aaaa ":)"])
