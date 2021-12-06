(ns app.system
  (:require
   [com.stuartsierra.component :as component]
   [app.schema :as schema]
   [app.server :as server]))

(defn new-system
  []
  (merge (component/system-map)
         (server/new-server)
         (schema/new-schema-provider)))