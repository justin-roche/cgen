(ns tt.app.core
  (:require-macros [secretary.core :refer [defroute]])
  (:require
   [reagent.dom]
   [tt.app.views]
   [secretary.core :as secretary])
  (:import [goog History]
           [goog.history EventType]))

(enable-console-print!)

(defn render
  []
  (reagent.dom/render [tt.app.views/todo-app]
                      (.getElementById js/document "root")))

(defn ^:dev/after-load clear-cache-and-render!
  []
  (render))

(defn ^:export main
  []
  (render))
