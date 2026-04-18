# cljs-mjml

Write [MJML](https://mjml.io/) email templates with [Hiccup](https://github.com/weavejester/hiccup) syntax
in [ClojureScript](https://clojurescript.org/) (or [Node Babashka](https://github.com/babashka/nbb))

## Justification

MJML is a markup language which takes much of the suffering out of designing responsive HTML emails that work across
most popular email clients. MJML allows us to decide how to populate templates with data and, while conventional
templating languages suffice, we can do better. If Hiccup is the best tool to dynamically form HTML (I believe it is),
then
it stands to reason that the same syntax would serve us equally well for MJML. The resulting combination is, in my
opinion, the second-best way of generating emails!

**Why only second-best?** You could just send plain text...

**Why not Clojure?** This library wraps the [MJML Node package](https://www.npmjs.com/package/mjml) and is therefore
tied to JS. If you wish to integrate cljs-mjml with a JVM stack (or anything else) I recommend running it in
a [Node Babashka Lambda](https://github.com/babashka/nbb/blob/main/doc/aws_lambda.md).
