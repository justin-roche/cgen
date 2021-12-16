(ns app.user
  (:require
   [clojure.tools.namespace.repl :as rp]
   [clojure.test :as t]
   [app.app-tests :as ts]
   [app.system :as system]
   [clojure.data.json :as json]
   [clj-http.client :as client]
   [com.stuartsierra.component :as component]))

(defn refreshed []
  (t/run-tests 'app.app-tests)
  (println "refreshed"))

(rp/refresh :after 'app.user/refreshed)


