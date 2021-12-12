(ns app.router
  (:require
   [io.pedestal.http.route :as route]
   [app.hero :as hero]
   [com.stuartsierra.component :as component]
   [io.pedestal.interceptor :as i]
   [reitit.http.coercion :as coercion]
   [reitit.http.interceptors.parameters :as parameters]
   [reitit.http.interceptors.exception :as exception]
   [reitit.http.interceptors.multipart :as multipart]
   [muuntaja.core :as m]
   [reitit.coercion.malli]
   [reitit.http.interceptors.muuntaja :as muuntaja]
   [io.pedestal.http.body-params :as bp]))


(def body-parser (bp/body-params (bp/default-parser-map)))

(defn get-db-interceptor [db]
  {:enter
   (fn [context]
     (update context :request assoc :db db))})

(def route-data {:data {:coercion reitit.coercion.malli/coercion
           :muuntaja m/instance
           :interceptors [(muuntaja/format-request-interceptor)
;; query-params & form-params
                          (parameters/parameters-interceptor)
                             ;; content-negotiation
                          (muuntaja/format-negotiate-interceptor)
                             ;; encoding response body
                          (muuntaja/format-response-interceptor)
                             ;; exception handling
                          (exception/exception-interceptor)
                             ;; decoding request body
                          (muuntaja/format-request-interceptor)
                             ;; coercing response bodys
                          (coercion/coerce-response-interceptor)
                             ;; coercing request parameters
                          (coercion/coerce-request-interceptor)
                             ;; multipart
                          (multipart/multipart-interceptor)]}} )

(defn make-routes [db]
  [""
   {:interceptors [(get-db-interceptor db)]}
   ["/hero"

    {:get {:handler hero/get-heroes}
     :post {:handler hero/add-heroes
            ;; :coercion m/coercion
            :parameters {:body [:map
                                [:a string?]]}}}]])

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
