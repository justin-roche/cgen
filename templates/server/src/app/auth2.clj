;; (ns app.auth2
;;   "A simple service demonstrating integration with the buddy-auth
;;   authentication/authorization library."
;;   (:require [io.pedestal.http :as http]
;;             [io.pedestal.http.route :as route]
;;             [io.pedestal.http.body-params :as body-params]
;;             [io.pedestal.interceptor :as interceptor]
;;             [io.pedestal.interceptor.chain :as interceptor.chain]
;;             [io.pedestal.interceptor.error :refer [error-dispatch]]
;;             [ring.util.response :as ring-resp]
;;             [buddy.auth :as auth]
;;             [buddy.auth.backends :as auth.backends]
;;             [buddy.auth.middleware :as auth.middleware]))

;; (defn home
;;   "Given a request, returns a user-specific response if authenticated,
;;   otherwise the response is anonymous."
;;   [request]
;;   (ring-resp/response (if (auth/authenticated? request)
;;                         (str "Hello " (:display-name (get users (:identity request))))
;;                         "Hello anonymous")))

;; (defn admin
;;   "Returns a 200 response for authorized users, otherwise throws a buddy-auth
;;   'unauthorized' exception."
;;   [request]
;;   (let [known-user (get users (:identity request))]
;;     (if (= :admin (:role known-user))
;;       (ring-resp/response  "Only admins can see this!")
;;       (buddy.auth/throw-unauthorized))))

;; (def basic-authentication-interceptor
;;   "Port of buddy-auth's wrap-authentication middleware."
;;   (interceptor/interceptor
;;    {:name ::authenticate
;;     :enter (fn [ctx]
;;              (update ctx :request auth.middleware/authentication-request basic-auth-backend))}))



;; (def common-interceptors [(body-params/body-params)
;;                           http/html-body
;;                           basic-authentication-interceptor
;;                           (authorization-interceptor basic-auth-backend)])

;; (def routes #{["/" :get (conj common-interceptors `home)]
;;               ["/admin" :get (conj common-interceptors `admin)]})

;; (def service {:env                     :prod
;;               ::http/routes            routes
;;               ::http/resource-path     "/public"
;;               ::http/type              :jetty
;;               ::http/port              8080
;;               ::http/container-options {:h2c? true
;;                                         :h2?  false
;;                                         :ssl? false}})
