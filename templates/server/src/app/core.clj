(ns app.core
  (:require
   [clojure.java.browse :refer [browse-url]]
   [app.system :as system]
   [clojure.walk :as walk]
   [com.stuartsierra.component :as component])
  ;; (:import (clojure.lang IPersistentMap))
  )


(defonce system (system/new-system))

(defn start
  []
  (alter-var-root #'system component/start-system)
  ;; (browse-url "http://localhost:8888/")
  :started)

(defn main
  "I don't do a whole lot."
  []
  (println "generated server running...")
  (start))
