(ns user
  (:require
   [com.walmartlabs.lacinia :as lacinia]
   [clojure.java.browse :refer [browse-url]]
   [app.system :as system]
   [clojure.walk :as walk]
   [com.stuartsierra.component :as component])
  (:import (clojure.lang IPersistentMap)))

(defonce system (system/new-system))

;; (defn q
;;   [query-string]
;;   (-> system
;;       :schema-provider
;;       :schema
;;       (lacinia/execute query-string nil nil)
;;       simplify))

(defn start
  []
  (alter-var-root #'system component/start-system)
  (browse-url "http://localhost:8888/")
  :started)

(defn stop
  []
  (alter-var-root #'system component/stop-system)
  :stopped)
