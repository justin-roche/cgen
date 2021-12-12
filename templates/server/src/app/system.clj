(ns app.system
  (:require [com.stuartsierra.component :as component]
            [app.db :as db]
            [io.pedestal.http :as http]
            [app.server :as server]
            [app.routes :as routes]))

(def env 'dev')

(defn new-system
  []
  (component/system-map
   :service-map
   {:env          env
    ::http/routes routes/routes
    ;; ::http/router :linear-search
    ::http/type   :jetty
    ::http/port   8890
    ::http/join?  false}
   :db-config
   {:env          env
    :port   27017
    :host   "localhost"
    :db "mongo-test"
    :cred-user "root"
    :cred-db "admin"
    :cred-password "rootpassword"}
   :db
   (component/using (db/new-database) [:db-config])
   :pedestal
   (component/using (server/new-server) [:service-map
                                         :db])))

