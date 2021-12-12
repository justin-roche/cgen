;; (comment
;;   ;; Start server to try with real HTTP clients.
;;   (start)

;;   ;; ...or just execute following sexps in the REPL. :)

;;   (def headers {"accept" "application/edn"})
;;   (def read-body (comp read-string slurp :body))

;;   (-> {:headers headers :request-method :get :uri "/no-auth"}
;;       app
;;       read-body)
;;   ;; => {:message "No auth succeeded!"}

;;   (-> {:headers headers :request-method :get :uri "/basic-auth"}
;;       app
;;       read-body)
;;   ;; => {:error "Unauthorized"}

;;   (-> {:headers headers :request-method :get :uri "/token-auth"}
;;       app
;;       read-body)
;;   ;; => {:error "Unauthorized"}

;;   (import java.util.Base64)

;;   (defn ->base64
;;     "Encodes a string as base64."
;;     [s]
;;     (.encodeToString (Base64/getEncoder) (.getBytes s)))

;;   (defn basic-auth-headers [user pass]
;;     (merge headers {:authorization (str "Basic " (->base64 (str user ":" pass)))}))

;;   (def bad-creds (basic-auth-headers "juum" "joo"))
;;   (-> {:headers bad-creds :request-method :get :uri "/basic-auth"}
;;       app
;;       read-body)
;;   ;; => {:error "Unauthorized"}

;;   (def admin-creds (basic-auth-headers "user1" "kissa13"))

;;   (-> {:headers admin-creds :request-method :get :uri "/basic-auth"}
;;       app
;;       read-body)
;;   ;; {:message "Basic auth succeeded!",
;;   ;;  :user
;;   ;;  {:id 1,
;;   ;;   :roles [:admin :user],
;;   ;;   :token
;;   ;;   "eyJhbGciOiJIUzUxMiJ9.eyJp....."

;;   (def admin-token
;;     (-> {:headers admin-creds :request-method :get :uri "/basic-auth"}
;;         app
;;         read-body
;;         :user
;;         :token))

;;   (def user-creds (basic-auth-headers "user2" "koira12"))

;;   (def user-token
;;     (-> {:headers user-creds :request-method :get :uri "/basic-auth"}
;;         app
;;         read-body
;;         :user
;;         :token))

;;   (defn token-auth-headers [token]
;;     (merge headers {:authorization (str "Token " token)}))

;;   (def user-token-headers (token-auth-headers user-token))

;;   (-> {:headers user-token-headers :request-method :get :uri "/token-auth"}
;;       app
;;       read-body)
;;   ;; => {:message "Token auth succeeded!"}

;;   (-> {:headers user-token-headers :request-method :get :uri "/token-auth-with-admin-role"}
;;       app
;;       read-body)
;;   ;; => {:error "Admin role required"}

;;   (def admin-token-headers (token-auth-headers admin-token))

;;   (-> {:headers admin-token-headers :request-method :get :uri "/token-auth-with-admin-role"}
;;       app
;;       read-body)
;;   ;; => {:message "Token auth with admin role succeeded!"}
;;   )
