;; (ns app.routes
;;   (:require [com.stuartsierra.component :as component]
;;             [app.db :as db]
;;             [io.pedestal.interceptor :as i]
;;             [io.pedestal.http.body-params :as bp]
;;             [io.pedestal.http.route :as route]))

;; (def body-parser (bp/body-params (bp/default-parser-map)))

;; (defn get-db-interceptor [db]
;;   (i/interceptor {:name :database-interceptor
;;                   :leave nil
;;                   :enter
;;                   (fn [context]
;;                     (update context :request assoc :database db))}))

;; (defn get-hero [request]
;;   {:status 200 :body "Hello, world!"})

;; (defn add-hero [{:keys [json-params db]}]
;;   (clojure.pprint/pprint json-params)
;;   (clojure.pprint/pprint db)
;;   {:status 200})

;; (def routes
;;   (route/expand-routes [[:app :http
;;                          ["/"
;;                           ^:interceptors [body-parser (get-db-interceptor {:a 1})]
;;                           ["/hero"
;;                            {:get `get-hero
;;                             :post `add-hero}]]]]))

;; (defn get-hero [{{:keys [hero]} :path-params
;;                  {:keys [extended]} :query-params}]
;;   (if-let [hero (->> []
;;                      (filter #(= hero (:hero %)))
;;                      first)]
;;     {:status 200 :body (if extended hero (dissoc hero :hero))}
;;     {:status 404}))

;; (defn t-hero []
;;   (route/try-routing-for routes :prefix-tree "/hero" :post))

;; (defn t-bp []
;;   (bp/default-parser-map))

;; (defn tv []
;;   (clojure.pprint/pprint routes))

;; (tv)
