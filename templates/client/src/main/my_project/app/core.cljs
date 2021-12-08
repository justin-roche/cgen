(ns tt.app.core
  (:require-macros [secretary.core :refer [defroute]])
  (:require [goog.events :as events]
            [reagent.dom]
            [re-frame.core :as rf :refer [dispatch dispatch-sync]]
            [secretary.core :as secretary]
            [tt.app.events] ;; These two are only required to make the compiler
            [tt.app.subs]
            [tt.app.views])
  (:import [goog History]
           [goog.history EventType]))

(enable-console-print!)
(dispatch-sync [:initialise-db])

(defroute "/" [] (dispatch [:set-showing :all]))
(defroute "/:filter" [filter] (dispatch [:set-showing (keyword filter)]))

(defonce history
  (doto (History.)
    (events/listen EventType.NAVIGATE
                   (fn [^js/goog.History.Event event] (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn render
  []
  (reagent.dom/render [tt.app.views/todo-app]
                      (.getElementById js/document "app")))

(defn ^:dev/after-load clear-cache-and-render!
  []
  (rf/clear-subscription-cache!)
  (render))

(defn ^:export main
  []
  (render))
