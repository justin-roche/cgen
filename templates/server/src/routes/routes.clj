(ns app.server
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http.route :as route]))

(defn respond-hello [request]
  {:status 200 :body "Hello, world!"}) 

(def routes
  (route/expand-routes
   #{["/greet" :get respond-hello :route-name :greet]})) 







