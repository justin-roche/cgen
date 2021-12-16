(defproject server "0.1.0-SNAPSHOT"
  :description ""
  :url ""
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main app.core/main

  :plugins [[lein-environ "1.2.0"]]
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.clojure/tools.namespace "1.2.0"]
                 [org.clojure/data.json "2.4.0"]
                 [environ "1.2.0"]
                 [clj-http "3.12.3"]
                 [com.stuartsierra/component "1.0.0"]
                 [io.pedestal/pedestal.service       "0.5.9"]
                 [io.pedestal/pedestal.service-tools "0.5.9"] ;; Only needed for ns-watching; WAR tooling
                 [io.pedestal/pedestal.jetty         "0.5.9"]
                 [io.pedestal/pedestal.log         "0.5.9"] ;; Logging and runtime metrics
                 [io.pedestal/pedestal.interceptor "0.5.9"] ;; The Interceptor chain and the Interceptor API
                 [io.pedestal/pedestal.route       "0.5.9"] ;; Efficient routing algorithms and data structures
                 [metosin/reitit "0.5.15"]
                 [metosin/reitit-pedestal "0.5.15"]
                 [buddy "2.0.0"]
                 [io.aviso/logging "0.2.0"]
                 [com.novemberain/monger "3.1.0"]
                 [org.slf4j/slf4j-simple       "1.7.28"]
                 [org.clojure/java.jdbc "0.7.8"]
                 [org.postgresql/postgresql "42.2.5.jre7"]
                 [com.mchange/c3p0 "0.9.5.2"]]
  :repl-options {:init-ns app.user}
  :profiles {})
