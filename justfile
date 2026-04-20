default:
    @just --list

repl:
    npx shadow-cljs node-repl

release version:
    clojure -T:build release :version '"{{version}}"'
