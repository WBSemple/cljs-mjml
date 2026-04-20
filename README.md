# cljs-mjml

Write [MJML](https://mjml.io/) email templates with [Hiccup](https://github.com/weavejester/hiccup)-style syntax
in [ClojureScript](https://clojurescript.org/) (or [Node Babashka](https://github.com/babashka/nbb))

## Justification

MJML is a markup language which takes much of the suffering out of designing responsive HTML emails that work across
popular email clients. MJML leaves it to us to decide how to populate templates with data and, while conventional
templating languages are sufficient in simple cases, we can do better. If Hiccup is the best tool to dynamically
generate HTML (I believe it is), then it stands to reason that the same syntax would serve us equally well for MJML. The
resulting combination is, in my opinion, the second-best way of generating emails!

**Why only second-best?** You could just send plain text...

**Why not Clojure?** This library wraps the [MJML Node package](https://www.npmjs.com/package/mjml) and is therefore
tied to JS. If you wish to integrate cljs-mjml with a JVM stack (or anything else) I recommend running it in
a [Node Babashka Lambda](https://github.com/babashka/nbb/blob/main/doc/aws_lambda.md).

## Installation

Install MJML

```
npm i mjml@5
```

Install cljs-mjml

[![Clojars Project](https://img.shields.io/clojars/v/io.github.wbsemple/cljs-mjml.svg)](https://clojars.org/io.github.wbsemple/cljs-mjml)

## Usage

ClojureScript data is rendered to HTML with `(render+ options document)`

```
(require '[cljs-mjml.core :as cljs-mjml])

(cljs-mjml/render+ {:minify true}
                   [:mjml
                    [:mj-body
                     [:mj-section
                      [:mj-column
                       [:mj-image {:width "100px"
                                   :src "https://mjml.io/assets/img/logo-small.png"}]
                       [:mj-divider {:border-color "#F45E43"}]
                       [:mj-text {:font-size "20px" :color "#F45E43" :font-family "helvetica"}
                        (str "Hello world!")]]]]])
```

This returns a promise resolving to a map of `:html` (the resulting HTML string containing your whole email) and
`:errors` (a vector of error messages).

There are a number of ways of dealing with promises, and you should choose whichever solution fits the rest of your
project. Example with [promesa](https://github.com/funcool/promesa):

```
(require '[promesa.core :as p])

(p/let [{:keys [html errors]} (cljs-mjml/render+ {:minify true} my-email-data-structure)]
  ;; Log or raise anything in `errors` here
  ;; Return/save/send `html`
  )
```

## Syntax

The document syntax is based on hiccup. Each element is represented by a vector of the form:

```
;; tag    attrs              & children
[:mj-text {:color "#F45E43"} "hello" "world!"]

;; attrs is optional
[:mj-text "hello!"]

;; children can be strings or elements
[:mj-text {:font-family "Helvetica" :color "#F45E43"}
 [:h1 "Title"]
 [:p "Paragraph"]
 [:p {:style "font-family:Comic Sans Ms"} "Another paragraph"]]

;; both vectors and sequences are acceptable and can be arbitrarily nested
[:mj-text {:font-family "Helvetica" :color "#F45E43"}
 (for [x xs]
   [:p x])]

;; nil children are ignored
[:mj-text "hello" (when x "there")]
```

## Tags

See MJML [Getting Started](https://documentation.mjml.io/#getting-started) to learn about designing MJML emails and find
documentation of all tags (called components).

## Options

See MJML [Inside Node.js](https://documentation.mjml.io/#inside-node-js) for all available options.

The `options` argument should be a ClojureScript map with keyword keys.

## Tips

### Live Preview

Consider setting up a reloading browser preview while implementing designs. See the [example](example) project for an
existing approach.

### Convert Examples

Tools like [html2hiccup](https://html2hiccup.dev/) can be used to convert example snippets from MJML docs into hiccup
syntax for faster experimentation. Note that the syntax is not identical (for example, it will convert inline styles to
maps which is not currently supported by cljs-mjml), but it works in most cases.
