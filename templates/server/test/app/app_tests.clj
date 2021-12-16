(ns app.app-tests
  (:require
   [clojure.tools.namespace.repl :as rp]
   [clojure.test :as t]
   [app.system :as system]
   [clojure.data.json :as json]
   [clj-http.client :as client]
   [com.stuartsierra.component :as component]))

(def request-options  {:socket-timeout 1000
                       :connection-timeout 1000
                       :accept :json})

(def auth-headers {:headers {"Authorization" "eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwicm9sZXMiOlsiYWRtaW4iLCJ1c2VyIl0sImV4cCI6MTY0MDQwNjQ3MX0.K_FUbrjpPd2IJaOYnqmG8DtPe43omUueAoupCLC_c7OhCZ68jwm6DgaAlJCN1tPViFP9_-FOeZ6ofR8FxJAYMg"}})
(def invalid-auth-headers {:headers {"Authorization" "xxx"}})

(def base "http://localhost:8890/")

(defn admin-req []
  (println "sending admin get req")
  (let [o (merge request-options auth-headers)
        r (client/get "http://localhost:8890/admin" o)]
    (println "admin r")
    (:status r)))

(defn valid-token-req []
  (println "sending valid req")
  (let [o (merge request-options auth-headers)
        r (client/get "http://localhost:8890/authorized" o)]
    (println (:status r))
    (:status r)))

(defn invalid-token-req []
  (println "sending invalid req")
  (let [o  (merge request-options invalid-auth-headers)
        r (client/get "http://localhost:8890/authorized" (merge o {:throw-exceptions false}))]
    (:status r)))

(defn login-req []
  (let [j (json/write-str {:username "user1" :password "kissa13"})
        o (-> request-options
              (merge {:content-type :application/json :body j}))
        r (client/post "http://localhost:8890/login" o)]
    (println (:headers r))
    (:status r)))

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

(defn run-test [test]
  (let [s (system/new-system)
        s1 (component/start-system s)]
    (try
      (let [result (test)]
        (component/stop-system s1)
        (println "\ntest result" result)
        result)
      (catch Exception e (component/stop-system s1)))))

(t/deftest valid-token ()
  (t/is (= 200 (run-test valid-token-req))))

(t/deftest invalid-token ()
  (t/is (= 401 (run-test invalid-token-req))))

;; (t/deftest login ()
  ;; (t/is (= 200 (run-test login-req))))

(t/deftest admin ()
  (t/is (= 200 (run-test admin-req))))
;; (t/deftest invalid-token ()
;;   (let [s (system/new-system)
;;         s1 (component/start-system s)]
;;     (try
;;       (let [result (invalid-token-req)]
;;         ;; (println "r:" (:status result))
;;         (t/is (= 401 result))
;;         (component/stop-system s1))
;;       (catch Exception e (do (println e) (component/stop-system s1))))))


