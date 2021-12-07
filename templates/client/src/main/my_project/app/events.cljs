(ns ^:av tt.app.events
  (:require [re-frame.core :as rf]))

(defn dispatch-timer-event        ;; <-- defining a function
  []                              ;; <-- no args
  (let [now (js/Date.)]           ;; <-- obtain the current time
    (rf/dispatch [:timer now])))  ;; <-- dispatch an event
