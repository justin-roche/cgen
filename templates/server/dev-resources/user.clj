(ns app.user
  (:require
   [clojure.tools.namespace.repl :as rp]
   [clojure.test :as t]
   [app.app-tests :as ts]
   [app.system :as system]
   [clojure.data.json :as json]
   [clj-http.client :as client]
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

;; (try
;;   (rp/refresh :after 'app.user/run-system)
;;   (catch Exception e (clojure.pprint/pprint (str "caught exception: " (.toString e)))))
