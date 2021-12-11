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

(defn db-connect [db-config]
  (let [cred (mgc/create "root" "admin" "rootpassword")
        conn (mg/connect-with-credentials "localhost" 27017 cred)
        db   (mg/get-db conn "monger-test")]
    {:db db :conn conn}))

(defrecord Database [db-config db]

  component/Lifecycle
  (start [this]
    (if db
      this
      (cond-> db-config
        true                      db-connect
        true                      ((fn [db-and-conn]
                                     ;; (println db-and-conn)
                                     (merge this db-and-conn))))))

  (stop [this]
    (assoc this :conn nil)
    (assoc this :db nil)))

(defn new-database
  []
  (map->Database {}))

