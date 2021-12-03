(defproject server "0.1.0-SNAPSHOT"
    :description "A tiny BoardGameGeek clone written in Clojure with Lacinia"
    :url "https://github.com/walmartlabs/clojure-game-geek"
    :license {:name "Eclipse Public License"
              :url "http://www.eclipse.org/legal/epl-v10.html"}
    :dependencies [[org.clojure/clojure "1.10.3"]
                   [com.walmartlabs/lacinia "0.21.0"]]
  :repl-options {:init-ns server.core})
