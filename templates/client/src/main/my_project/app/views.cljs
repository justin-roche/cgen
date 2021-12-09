(ns tt.app.views
  (:require [reagent.core  :as reagent]
            [clojure.string :as str]))

(defn todo-app
  []
  [:<>
   [:p "Double-click to edit a todo"]])
