(ns app.auth
  (:require [buddy.auth :as buddy-auth]
            [buddy.auth.backends :as buddy-auth-backends]
            [buddy.auth.backends.httpbasic :as buddy-auth-backends-httpbasic]
            [buddy.auth.middleware :as buddy-auth-middleware]
            [io.pedestal.interceptor.chain :as interceptor.chain]
            [io.pedestal.interceptor.error :refer [error-dispatch]]
            [io.pedestal.interceptor :as i]
            [malli.provider :as mp]
            [malli.core :as m]
            [app.errors :as e]
            [buddy.hashers :as buddy-hashers]
            [buddy.sign.jwt :as jwt]))

(def private-key
  "Used for signing and verifying JWT-tokens In real world you'd read
  this from an environment variable or some other configuration that's
  not included in the source code."
  "kana15")

(defn create-token
  "Creates a signed jwt-token with user data as payload.
  `valid-seconds` sets the expiration span."
  [user & {:keys [valid-seconds] :or {valid-seconds 7200}}] ;; 2 hours
  (let [payload (-> user
                    (select-keys [:id :roles])
                    (assoc :exp (.plusSeconds
                                 (java.time.Instant/now) valid-seconds)))]
    (jwt/sign payload private-key {:alg :hs512})))

(def s-token-request
  (m/schema [:map
             [:request  [:map [:headers [:map ["authorization" string?]]]]]]))

(defn verify-token [db]
  {:enter
   (fn [r]
     (e/v s-token-request r)
     (try (let [token (get-in r [:request :headers "authorization"])
                data (jwt/unsign token private-key {:alg :hs512})]
            (clojure.pprint/pprint data)
            (update-in r [:user] data))
          (catch Exception err
            (do
              (clojure.pprint/pprint "ERROR***\n")

              (e/throw 401)))))})

(def db
  {"user1"
   {:id       1
    :password (buddy-hashers/encrypt "kissa13")
    :roles    ["admin" "user"]}
   "user2"
   {:id       2
    :password (buddy-hashers/encrypt "koira12")
    :roles    ["user"]}})

(def s-request
  (m/schema [:map
             [:request  [:map [:body-params [:map
                                             [:username string?]
                                             [:password string?]]]]]]))

(defn login [db]
  {:enter
   (fn [r]
     (e/v s-request r)
     (let [username (get-in r [:request :body-params :username])
           password (get-in r [:request :body-params :password])
           user (get db username)]
       (println (str "auth data: " username " " password))
       (if (and user (buddy-hashers/check password (:password user)))
         (let [r2 (assoc-in r [:request :token] (create-token user))]
           r2)
         (throw
          (ex-info "unauthorized" {:status 401})))))})

