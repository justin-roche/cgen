(ns ^:av tt.app.subscriptions
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :time
 (fn [db _]
   (:time db)))

(rf/reg-sub
 :time-color
 (fn [db _]
   (:time-color db)))
