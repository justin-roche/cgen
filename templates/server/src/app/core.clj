(ns app.core
  (:require
   [clojure.java.browse :refer [browse-url]]
   [app.system :as system]
   [com.stuartsierra.component :as component])
  )

(defonce system (system/new-system))

(defn start
  []
  (component/start-system system)
;;   ;; (browse-url "http://localhost:8888/")
  :started)

(defn main
  []
  (println "generated server running...")
  (start)
  )
