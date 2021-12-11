(ns user
  (:require
   [app.system :as system]
   [app.core :as core]
   [app.routes :as routes]
   [io.pedestal.http.route :as route]
   [com.stuartsierra.component :as component]))

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
  :started)

(defn stop
  []
  (alter-var-root #'system component/stop-system)
  :stopped)

(defn tt []
  (route/try-routing-for routes/routes :prefix-tree "/greet" :get)
)
