(ns app.user
  (:require
   [clojure.tools.namespace.repl :as rp]
   [app.system :as system]
   [clojure.data.json :as json]
   [clj-http.client :as client]
   [com.stuartsierra.component :as component]))

(def valid-token
  "eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwicm9sZXMiOlsiYWRtaW4iLCJ1c2VyIl0sImV4cCI6MTY0MDM2MjQ4MH0.oxVIVYOqmsSLXSrg9Xbv5WMCGi86QtiPvZfOoYC6CWAzs-WOCrwd-7XbpMSU6URN4KFHBHjvVDAKx_T4ehnlwA")

(def invalid-token
  "fyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwicm9sZXMiOlsiYWRtaW4iLCJ1c2VyIl0sImV4cCI6MTYzOTUxMzUzOH0.IvcM3ocMrrjbIp_ffnic7axpQSV7W_BM4ngY1oYSGBTgXWFZDMgdaCYBoqRrY7NkRE1cAUFXj26Xy0KnSplDXQ")

(def request-options  {:headers {"X-Api-Version" "2" "Authorization" valid-token}
                       :socket-timeout 1000
                       :connection-timeout 1000
                       :accept :json})

(def base "http://localhost:8890/")

(defn admin-req []
  (println "sending admin get req")
  (let [o (merge request-options {:headers {"Authorization" valid-token}})
        r (client/get "http://localhost:8890/admin" o)]
    (println r)))

(defn valid-token-req []
  (println "sending valid req")
  (let [o (merge request-options {:headers {"Authorization" valid-token}})
        r (client/get "http://localhost:8890/authorized" o)]
    (println (:status r))))

(defn invalid-token-req []
  (println "sending invalid req")
  (let [o  (merge request-options {:headers {"X-Api-Version" "2" "Authorization" invalid-token}})
        r (client/get "http://localhost:8890/authorized" o)]
    (println "wow")
    (println (:status r))))

(defn login-req []
  (let [j (json/write-str {:username "user1" :password "kissa13"})
        o (-> request-options
              (merge {:content-type :application/json :body j}))
        r (client/post "http://localhost:8890/login" o)]
    (println (:body r))))

(defn post-req []
  (let [j (json/write-str {:a "b"})
        o
        (-> request-options
            (merge {:content-type :application/json
                    :body j
                    :accept :json}))
        r (client/post "http://localhost:8890/hero" o)]
    (println (:status r))))

(defn get-req []
  (let [r (client/get "http://localhost:8890/hero")]
    (print (:status r))))

(defn start-system []
  (let [s (system/new-system)
        s1 (component/start-system s)]
    nil))

(defn run-test []
  (let [s (system/new-system)
        s1 (component/start-system s)]
    (try
      ;; (post-req)
      ;; (valid-token-req)
      (admin-req)
      ;; (invalid-token-req)
      ;; (login-req)
      (catch Exception e (component/stop-system s1)))

    (component/stop-system s1)))

(rp/refresh :after 'app.user/run-test)

;; (every? #(contains? ["admin"] %) ["admin"])
