;; https://github.com/razum2um/aprint
(ns app.app-tests
  (:require
   [clojure.tools.namespace.repl :as rp]
   [clojure.test :as t]
   [mount.core :as mount :refer [defstate start stop]]
   [aprint.core :refer [aprint]]
   [malli.core :as m]
   [app.auth :as auth]
   [clojure.data.json :as json]
   [clj-http.client :as client]))

(def request-options  {:socket-timeout 1000
                       :connection-timeout 1000
                       :accept :json})

(def auth-headers {:headers {"Authorization" "eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwicm9sZXMiOlsiYWRtaW4iLCJ1c2VyIl0sImV4cCI6MTY0MDQwNjQ3MX0.K_FUbrjpPd2IJaOYnqmG8DtPe43omUueAoupCLC_c7OhCZ68jwm6DgaAlJCN1tPViFP9_-FOeZ6ofR8FxJAYMg"}})

(def invalid-auth-headers {:headers {"Authorization" "this is an invalid header"}})

(def non-admin-auth-headers {:headers {"Authorization" "eyJhbGciOiJIUzUxMiJ9.eyJpZCI6Miwicm9sZXMiOlsidXNlciJdLCJleHAiOjE2NDA0MDcxMDF9.hElFSsbzZN_Q0hdvUXFsTsWHEhCTr75SBwiMBKQTTEujXIN91cfTQXk2nA2o553pZtPw0371H3X85pSj5S_jTg"}})
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
    (:status r)))

(defn invalid-token-req []
  (println "sending invalid req")
  (let [o  (merge request-options invalid-auth-headers)
        r (client/get "http://localhost:8890/authorized" (merge o {:throw-exceptions false}))]
    (:status r)))

(defn denial-by-role-req []
  (println "sending non-admin req for admin route")
  (let [o  (merge request-options non-admin-auth-headers)
        r (client/get "http://localhost:8890/admin" (merge o {:throw-exceptions false}))]
    (:status r)))

(defn login-req []
  (let [j (json/write-str {:username "user1" :password "kissa13"})
        o (-> request-options
              (merge {:content-type :application/json :body j}))
        r (client/post "http://localhost:8890/login" o)]
    (:status r)))

(defn login-normal-user-req []
  (println "logging in as non-admin")
  (let [j (json/write-str {:username "user2" :password "koira12"})
        o (-> request-options
              (merge {:content-type :application/json :body j}))
        r (client/post "http://localhost:8890/login" o)]
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
  (start))

(defn run-test [test]
  (try
    (start)
    (let [result (test)]
      (stop)
      (println "\ntest result")
      (aprint result)
      result)
    (catch Exception e (do ;;
                         (println  "exception of type: " (type e))
                         (stop)))))

(t/deftest valid-token ()
  (t/is (= 200 (run-test valid-token-req))))

(t/deftest invalid-token ()
  (t/is (= 401 (run-test invalid-token-req))))

(t/deftest login ()
  (t/is (= 200 (run-test login-req))))

(t/deftest login-normal-user ()
  (t/is (= 200 (run-test login-normal-user-req))))

(t/deftest denial-by-role ()
  (t/is (= 401 (run-test denial-by-role-req))))

(t/deftest admin ()
  (t/is (= 200 (run-test admin-req))))

;; (t/deftest schema ()
;;   (t/is (= 200 (auth/plus1 "a"))))


