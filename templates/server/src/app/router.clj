(ns app.router
  (:require
   [io.pedestal.http.route :as route]
   ;; [app.hero :as hero]
   [com.stuartsierra.component :as component]
   [io.pedestal.interceptor :as i]
   [io.pedestal.http.body-params :as bp]))

(defn get-heroes [request]
  {:status 200 :body "Hello, world!"})

(defn add-heroes [{:keys [json-params db]}]
  (clojure.pprint/pprint json-params)
  {:status 200})

(def body-parser (bp/body-params (bp/default-parser-map)))

(defn get-db-interceptor [db]
  (i/interceptor {:name :database-interceptor
                  :leave nil
                  :enter
                  (fn [context]
                    (update context :request assoc :db db))}))

(defn make-routes [db]
  (println  "making routes")
  (route/expand-routes [[:app :http
                         ["/"
                          ^:interceptors [body-parser (get-db-interceptor db)]
                          ["/hero"
                           {:get `get-heroes
                            :post `add-heroes
                            }]]]]))

(defrecord Router [db service-map routes]

  component/Lifecycle
  (start [this]
    (if routes
      this
      (let [routes (make-routes db)]
        (clojure.pprint/pprint routes)
        (assoc this :routes routes))))

  (stop [this]
    (do
      (println  "removing routes")
      (clojure.pprint/pprint (:routes this))
      (assoc this :routes nil))))

(defn new-router
  []
  (map->Router {}))

(defn t-hero []
  ;; (route/try-routing-for routes :prefix-tree "/hero" :post)
  )
