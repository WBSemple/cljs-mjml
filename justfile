default:
    @just --list

build:
    npx shadow-cljs compile lib

test:
    npx nbb nbb/test.cljs
