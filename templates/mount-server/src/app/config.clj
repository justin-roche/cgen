(ns app.config
  (:require [mount.core :refer [defstate]]
            [environ.core :refer [env]]))

(def env-mode 'dev')

(defstate config
  :start
  (let [c  {:env          env-mode
            :port   27017
            :host (env :db-host)
            :db (env :db)
            :cred-user (env :cred-user)
            :cred-db (env :cred-db)
            :cred-password (env :cred-password)
            :http/type   :jetty
            :http/port   8890
            :http/join?  false
            :http/routes  []}]
    (println "start config")
    c))

