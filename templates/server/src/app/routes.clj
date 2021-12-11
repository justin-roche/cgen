(ns app.routes
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http.route :as route]))

(defn respond-hello [request]
  (println request)
  {:status 200 :body "Hello, world!"}) 

(def routes
  (route/expand-routes
   #{["/greet" :get respond-hello :route-name :greet]})) 

;; (defn get-hero [{{:keys [hero]} :path-params
;;                  {:keys [extended]} :query-params}]
;;   (if-let [hero (->> []
;;                      (filter #(= hero (:hero %)))
;;                      first)]
;;     {:status 200 :body (if extended hero (dissoc hero :hero))}
;;     {:status 404}))







