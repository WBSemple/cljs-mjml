default:
    @just --list

repl:
    npx shadow-cljs node-repl

build:
    npx shadow-cljs compile lib

test:
    npx nbb nbb/test.cljs
