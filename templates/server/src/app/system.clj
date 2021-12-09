(ns app.system
  (:require
   [com.stuartsierra.component :as component]
   [app.server :as server]
   ;; [app.db :as db]
   ))

(defn new-system
  []
  (merge (component/system-map)
         (server/new-server)
         ;; (db/new-db)
         ))
