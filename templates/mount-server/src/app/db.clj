(ns app.db
  (:require
   [monger.collection :as mc]
   [sc.api :as sc :refer [spy letsc defsc ep-info]]
   [app.user]
   [aprint.core :refer [aprint]]
   [monger.core :as mg]
   [monger.credentials :as mgc]
   [mount.core :refer [start defstate]]
   [app.config :refer [config]]))

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

(defn db-connect [{:keys [cred-db cred-user cred-password host port db]}]
  (println "initiating db conn")
  (let [cred (mgc/create cred-user cred-db cred-password)
        conn (mg/connect-with-credentials host port cred)
        db   (mg/get-db conn db)]
    {:db db :conn conn}))

(defstate conn :start (db-connect config)
  :stop (db-disconnect conn))

;; (start)
;; (defsc 4)
;; (ep-info 2)
;; (println config)
