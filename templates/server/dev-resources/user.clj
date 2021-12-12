(ns user
  (:require
   [app.system :as system]
   [app.core :as core]
   [app.router :as rt]
   [io.pedestal.http.route :as route]
   [com.stuartsierra.component :as component]))

(defonce system (system/new-system))

(defn start
  []
  (alter-var-root #'system component/start-system)
  :started)

(defn stop
  []
  (alter-var-root #'system component/stop-system)
  :stopped)

(defn tt []
  ;; (route/try-routing-for routes/routes :prefix-tree "/greet" :get)
)


;; (rt/new-router)
 ;; (let [router  (component/using (rt/new-router) [:db {} :service-map {}])]
 ;;  (component/start router)
 ;;  (component/stop router)
 ;;    ;; (clojure.pprint/pprint router)
 ;;  )


