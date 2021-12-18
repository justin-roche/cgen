(ns app.user
  (:require
   [app.system :as system]
   [clojure.data.json :as json]
   [app.auth :as auth]
   [aprint.core :as aprint]
   ;; [sl4j/sl4j-nop]
   [app.app-tests :as appt]
   [clojure.edn :as edn]
   [clojure.test :as t]
   [clojure.tools.namespace.repl :as rp]
   [com.stuartsierra.component :as component]))

(defn refresh-and-run-tests []
  (t/run-tests 'app.app-tests)
  (println "refreshed"))

(defn run-system []
  (let [s (system/new-system)
        s1 (component/start-system s)]
    (try
      (defonce sys s1)
      (catch Exception e (component/stop-system s1)))))

(try
  (rp/refresh :after 'app.user/refresh-and-run-tests)
  (catch Exception e (clojure.pprint/pprint (str "caught exception: " (.toString e)))))

(defn to-json [v]

  (-> v
      edn/read

;; json/write-str
      ;; json/read-str
      ))
;; (try
;;   (rp/refresh :after 'app.user/run-system)
;;   (catch Exception e (clojure.pprint/pprint (str "caught exception: " (.toString e)))))
