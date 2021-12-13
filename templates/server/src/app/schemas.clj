;; (ns app.schemas
;;   (:require
;;    [malli.provider :as mp]
;;    [malli.core :as m]))

;; (def system-at-server
;;   (m/schema [:vector
;;  [:or
;;   :any
;;   [:map
;;    [:env {:optional true} symbol?]
;;    [:io.pedestal.http/type {:optional true} keyword?]
;;    [:io.pedestal.http/join? {:optional true} boolean?]
;;    [:io.pedestal.http/routes {:optional true} [:vector any?]]
;;    [:db
;;     {:optional true}
;;     [:map
;;      [:db-config
;;       [:map
;;        [:env symbol?]
;;        [:port int?]
;;        [:host string?]
;;        [:db string?]
;;        [:cred-user string?]
;;        [:cred-db string?]
;;        [:cred-password string?]]]
;;      [:db :any]
;;      [:conn :any]]]
;;    [:service-map
;;     {:optional true}
;;     [:map
;;      [:env symbol?]
;;      [:io.pedestal.http/type keyword?]
;;      [:io.pedestal.http/join? boolean?]
;;      [:io.pedestal.http/routes [:vector any?]]]]
;;    [:routes
;;     {:optional true}
;;     [:vector
;;      [:or
;;       string?
;;       [:map
;;        [:interceptors
;;         [:vector
;;          [:map
;;           [:error {:optional true} ifn?]
;;           [:enter {:optional true} ifn?]]]]]
;;       [:vector
;;        [:or
;;         string?
;;         [:map
;;          [:post
;;           [:map
;;            [:handler ifn?]
;;            [:interceptors
;;             {:optional true}
;;             [:vector [:map [:enter ifn?]]]]
;;            [:parameters
;;             {:optional true}
;;             [:map [:body [:vector [:or keyword? [:vector ifn?]]]]]]]]
;;          [:get {:optional true} [:map [:handler ifn?]]]]]]]]]]]]))
