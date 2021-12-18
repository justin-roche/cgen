(ns app.auth
  (:require
   [app.errors :as e]
   [buddy.hashers :as buddy-hashers]
   [buddy.sign.jwt :as jwt]
   [clojure.set :refer [subset?]]
   [io.pedestal.log :as pl]
   [malli.instrument :as mi]
   [malli.core :as m]))

(def db
  {"user1"
   {:id       1
    :password (buddy-hashers/encrypt "kissa13")
    :roles    ["admin" "user"]}
   "user2"
   {:id       2
    :password (buddy-hashers/encrypt "koira12")
    :roles    ["user"]}})

(def private-key
  "Used for signing and verifying JWT-tokens In real world you'd read
  this from an environment variable or some other configuration that's
  not included in the source code."
  "kana15")

(defn create-token
  "Creates a signed jwt-token with user data as payload.
  `valid-seconds` sets the expiration span."
  [user & {:keys [valid-seconds] :or {valid-seconds 777200}}] ;; 2 hours
  (let [payload (-> user
                    (select-keys [:id :roles])
                    (assoc :exp (.plusSeconds
                                 (java.time.Instant/now) valid-seconds)))]
    (jwt/sign payload private-key {:alg :hs512})))

(def s-token-request
  (m/schema [:map
             [:request  [:map [:headers [:map ["authorization" string?]]]]]]))

(defn unsign-token [token]
  (pl/info "unsigning token:" token)
  (try (jwt/unsign token private-key {:alg :hs512})
       (catch Exception e (do (pl/error "jwt error" (ex-data e)) (throw e)))))

(defn update-res [ctx v]
  (update-in ctx [:response] #(merge % v)))

(defn update-req [ctx v]
  (update-in ctx [:request] #(merge % v)))

(defn verify-token []
  {:name ::verify-token
   :enter
   (fn [ctx]
     (let [token (get-in ctx [:request :headers "authorization"])
           user-data (unsign-token token)]
       (pl/info "token user data" user-data)
       (update-req ctx {:user user-data})))})

(def s-request
  (m/schema [:map
             [:request  [:map [:body-params [:map
                                             [:username string?]
                                             [:password string?]]]]]]))

(defn login [db]
  {:enter
   (fn [r]
     ;; (e/v s-request r)
     (let [username (get-in r [:request :body-params :username])
           password (get-in r [:request :body-params :password])
           user (get db username)]
       (pl/info "auth data" (str  username " " password))
       (if (and user (buddy-hashers/check password (:password user)))
         (let [r2 (assoc-in r [:request :token] (create-token user))]
           r2)
         (throw
          (ex-info "unauthorized" {:status 401})))))})

(defn get-user-roles [ctx]
  (get-in ctx [:request :user :roles]))

(defn has-roles? [user-roles roles]
  (subset? (set roles) (set user-roles)))

(defn role [roles]
  ;; (pl/info "token user roles" user-roles)
  ;; (pl/info "required roles" roles)
  {:name ::verify-role
   :enter
   (fn [ctx]
     (let [user-roles (get-user-roles ctx)]
       (if (has-roles? user-roles roles)
         ctx
         (throw (new Exception)))))})
(m/=> role [:=> [:cat :int] [:boolean]])

(defn plus1 [x] (identity x))
(m/=> plus1 [:=> [:cat :int] [:int {:max 6}]])

;; (mi/instrument!)
;; (m/function-schemas)
;; (plus1 "a")
