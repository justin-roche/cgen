(ns app.core
  (:require
   [app.system :as system]
   [com.stuartsierra.component :as component]))

(defonce system (system/new-system))

(defn main
  []
  (component/start-system system)
   ;; (route/try-routing-for routes/routes :prefix-tree "/greet" :get)
  )

