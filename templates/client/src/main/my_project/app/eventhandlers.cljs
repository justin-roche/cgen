(ns ^:av tt.app.eventhandlers
  (:require [re-frame.core :as rf]))

(rf/reg-event-db              ;; sets up initial application state
 :initialize
 (fn [_ _]
   {:time (js/Date.)
    :time-color "orange"}))
nil

(rf/reg-event-db
 :time-color-change
 (fn [db [_ new-color-value]]
   (assoc db :time-color new-color-value)))
nil

(rf/reg-event-db
 :timer
 (fn [db [_ new-time]]
   (assoc db :time new-time)))
