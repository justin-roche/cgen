;; (ns app.hero
;;   (:require
;;    [io.pedestal.http.route :as route]
;;    [com.stuartsierra.component :as component]
;;    ))

;; (defn get-heroes [request]
;;   {:status 200 :body "Hello, world!"})

;; (defn add-heroes [{:keys [json-params db]}]
;;   (clojure.pprint/pprint json-params)
;;   {:status 200})

;; (defn get-hero [{{:keys [hero]} :path-params
;;                  {:keys [extended]} :query-params}]
;;   (if-let [hero (->> []
;;                      (filter #(= hero (:hero %)))
;;                      first)]
;;     {:status 200 :body (if extended hero (dissoc hero :hero))}
;;     {:status 404}))



