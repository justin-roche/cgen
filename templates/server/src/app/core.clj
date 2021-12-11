(ns app.core
  (:require
   [clojure.java.browse :refer [browse-url]]
   [app.system :as system]
   [app.db :as db]
   [com.stuartsierra.component :as component]))

(defonce system (system/new-system))

(defn main
  []
  (component/start-system system)
  )
