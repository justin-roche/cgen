(ns app.routes
  (:require [com.stuartsierra.component :as component]
            [app.db :as db]
            [io.pedestal.http.route :as route]))

(defn respond-hello [request]
  (println request)
  {:status 200 :body "Hello, world!"})

(defn add-hero [{:keys [db]}]
  (println db)
  {:status 200 }
  ;; (if-let [hero (-> (db/insert
  ;;                    db "heroes" "wow"))]
  ;;   {:status 200}
  ;;   {:status 404})
  )

(def routes
  (route/expand-routes
   #{["/greet" :get respond-hello :route-name :greet]
     ["/hero" :post add-hero :route-name :add-hero]}))

(defn get-hero [{{:keys [hero]} :path-params
                 {:keys [extended]} :query-params}]
  (if-let [hero (->> []
                     (filter #(= hero (:hero %)))
                     first)]
    {:status 200 :body (if extended hero (dissoc hero :hero))}
    {:status 404}))

(defn t-hero []
  (route/try-routing-for routes/routes :prefix-tree "/hero" :post))





