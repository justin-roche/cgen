(ns app.hero
  (:require [com.stuartsierra.component :as component]
            [app.db :as db]
            [io.pedestal.interceptor :as i]
            [io.pedestal.http.body-params :as bp]
            [io.pedestal.http.route :as route]))

(defn get-heroes [request]
  {:status 200 :body "Hello, world!"})

(defn get-hero [request]
  {:status 200 :body "Hello, world!"})

(defn add-heroes [{:keys [json-params db]}]
  (clojure.pprint/pprint db)
  {:status 200})

(defn add-hero [{:keys [json-params db]}]
  (clojure.pprint/pprint db)
  {:status 200})


(defn get-hero [{{:keys [hero]} :path-params
                 {:keys [extended]} :query-params}]
  (if-let [hero (->> []
                     (filter #(= hero (:hero %)))
                     first)]
    {:status 200 :body (if extended hero (dissoc hero :hero))}
    {:status 404}))
