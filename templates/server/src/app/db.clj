(ns app.db
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [com.stuartsierra.component :as component]
            [monger.conversion :as mcc]
            [monger.credentials :as mgc])
  (:import [com.mongodb MongoOptions ServerAddress]))

(defn get-collection [db coll]
  (mc/find-maps db coll))

(defn insert [db coll item]
  (mc/insert-and-return db coll item))

(defn remove-document [db coll doc]
  (mc/remove db coll doc))

(defn remove-collection [db coll]
  (mc/remove db coll))

(defn update-multiple [db coll q u]
  (mc/update db coll  q u {:multi true}))

(defn update-all [db coll u]
  (mc/update db coll {} u {:multi true}))

(defn db-disconnect [conn]
  mg/disconnect conn)

(defn db-connect [{:keys [ cred-db cred-user cred-password host port db ]}]
  (let [cred (mgc/create cred-user cred-db cred-password)
        conn (mg/connect-with-credentials host port cred)
        db   (mg/get-db conn db)]
    {:db db :conn conn}))

(defrecord Database [db-config db]

  component/Lifecycle
  (start [this]
    (if db
      this
      (cond-> db-config
        true                      db-connect
        true                      ((fn [db-and-conn]
                                     (merge this db-and-conn))))))

  (stop [this]
    (db-disconnect (:conn this))
    (assoc this :conn nil)
    (assoc this :db nil)))

(defn new-database
  []
  (map->Database {}))

