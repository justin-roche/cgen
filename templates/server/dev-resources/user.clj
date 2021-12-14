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

(def valid-token
  "eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwicm9sZXMiOlsiYWRtaW4iLCJ1c2VyIl0sImV4cCI6MTYzOTUxMzUzOH0.IvcM3ocMrrjbIp_ffnic7axpQSV7W_BM4ngY1oYSGBTgXWFZDMgdaCYBoqRrY7NkRE1cAUFXj26Xy0KnSplDXQ")

(def invalid-token
  "fyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwicm9sZXMiOlsiYWRtaW4iLCJ1c2VyIl0sImV4cCI6MTYzOTUxMzUzOH0.IvcM3ocMrrjbIp_ffnic7axpQSV7W_BM4ngY1oYSGBTgXWFZDMgdaCYBoqRrY7NkRE1cAUFXj26Xy0KnSplDXQ")

(defn valid-token-req []
  (println "valid req")
  (let [o {:headers {"X-Api-Version" "2" "Authorization" valid-token}
           ;; :body j
           ;; :content-type :application/json
           :socket-timeout 1000
           :connection-timeout 1000
           :accept :json}
        r (client/get "http://localhost:8890/authorized" o)]
    (print "........\n\n")
    (print (:status r))
    (print (:body r))
    (print "\n........\n")))

(defn invalid-token-req []
  (println "invalid req")
  (let [o {:headers {"X-Api-Version" "2" "Authorization" invalid-token}
           ;; :body j
           ;; :content-type :application/json
           :socket-timeout 1000
           :connection-timeout 1000
           :accept :json}
        r (client/get "http://localhost:8890/authorized" o)]
    (print "........\n\n")
    (print (:status r))
    (print (:body r))
    (print (:message r))
    (print "\n........\n")))

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
    ;; (print "........\n")
    ;; (print (:status r))
    ;; (print "\n........\n")
    ))

(defn get-req []
  (let [r (client/get "http://localhost:8890/hero")]
    (print (:status r))))

(defn start-system []
  (let [s (system/new-system)
        s1 (component/start-system s)]
    nil
    ;; (def sys s1)
    ))

(defn run-test []
  (let [s (system/new-system)
        s1 (component/start-system s)]
    (try (valid-token-req)
         (catch Exception e (do
                              (clojure.pprint/pprint (str "caught exception: " (.toString e)))
                              (component/stop-system s1))))

    (component/stop-system s1)))

(rp/refresh :after 'app.user/run-test)
;; (clojure.pprint/pprint "ok")
;; (start-system)




