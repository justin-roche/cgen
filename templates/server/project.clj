(defproject server "0.1.0-SNAPSHOT"
  :description "A tiny BoardGameGeek clone written in Clojure with Lacinia"
  :url "https://github.com/walmartlabs/clojure-game-geek"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main app.core/main
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [com.stuartsierra/component "1.0.0"]
                 [com.walmartlabs/lacinia-pedestal "1.0"]
                 [io.aviso/logging "0.2.0"]]
  :repl-options {:init-ns server.core})
