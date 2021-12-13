(ns app.user
  (:require
   [clojure.tools.namespace.repl :as rp]
   [app.core :as core]
   [app.auth :as auth]
   [app.router :as rt]
   [app.system :as system]
   [clojure.data.json :as json]
   [io.pedestal.interceptor :as interceptor]
   [clj-http.client :as client]
   [io.pedestal.http.route :as route]
   [com.stuartsierra.component :as component]))

;; (defn start
;;   [s]
;;   (component/start-system s)
;;   :started)

;; (defn stop
;;   []
;;   (component/stop-system s)
;;   :stopped)

(defn login-req []
  (let [j (json/write-str {:username "user1" :password "kissa13"})
        o {:headers {"X-Api-Version" "2"}
           :body j
           :content-type :application/json
           :socket-timeout 1000
           :connection-timeout 1000
           :accept :json}
        r (client/post "http://localhost:8890/login" o)]
    (print "........\n\n")
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
    (print (:status r))))

(defn run-test []
  (let [s (system/new-system)
        s1 (component/start-system s)]
    (login-req)
    (component/stop-system s1)
    ))

(rp/refresh :after 'app.user/run-test)


