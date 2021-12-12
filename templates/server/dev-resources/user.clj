(ns user
  (:require
   [clojure.data.json :as json]
   [clojure.tools.namespace.repl :as replt]
   [app.system :as system]
   [clj-http.client :as client]
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
(defn login-req []
  (let [j (json/write-str {:username "user1" :password "kissa14"})
        o {:headers {"X-Api-Version" "2"}
           :body j
           :content-type :application/json
           :socket-timeout 1000
           :connection-timeout 1000
           :accept :json}
        r (client/post "http://localhost:8890/login" o)]
    (print "........\n")
    (print (:status r))
    (print (:body r))
    (print "\n........\n")))

(defn post-req []
  (let [j (json/write-str {:a "b"})
        o {:headers {"X-Api-Version" "2"}
           :body j
           :content-type :application/json
           :socket-timeout 1000
           :connection-timeout 1000
           :accept :json}
        r (client/post "http://localhost:8890/hero" o)]
    (print "........\n")
    (print (:status r))
    (print "\n........\n")))

(defn get-req []
  (let [r (client/get "http://localhost:8890/hero")]
    (print (:status r)))
  )

(replt/refresh)
(stop)
(start)
(login-req)

;; (get-req)
