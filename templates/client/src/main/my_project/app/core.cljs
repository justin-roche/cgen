(ns ^:av tt.app.core
  (:require [re-frame.core :as rf]
            [tt.app.db]
            [tt.app.events :refer [dispatch-timer-event]]
            [tt.app.eventhandlers]
            [tt.app.subscriptions]
            [reagent.dom :as rdom]))

(defonce do-timer (js/setInterval dispatch-timer-event 1000))

(defn clock
  []
  (let [colour @(rf/subscribe [:time-color])
        time   (-> @(rf/subscribe [:time])
                   .toTimeString
                   (clojure.string/split " ")
                   first)]
    [:div.example-clock {:style {:color colour}} time]))

(defn color-input
  []
  (let [gettext (fn [e] (-> e .-target .-value))
        emit    (fn [e] (rf/dispatch [:time-color-change (gettext e)]))]
    [:div.color-input
     "Display color: "
     [:input {:type "text"
              :style {:border "1px solid #CCC"}
              :value @(rf/subscribe [:time-color])
              :on-change emit}]]))

(defn simple-component []
  [:div
   [clock]
   [color-input]
   [:p "I am a component!"]
   [:p.someclass
    "I have " [:strong "bold"]
    [:span {:style {:color "red"}} " and red "] "text."]])

(defn ^:av app-component []
  [:h1 "Create Reagent App"]
  [simple-component])

(defn ^:export main []
  (rf/dispatch-sync [:initialize])
  (rdom/render [app-component] (js/document.getElementById "root")))
