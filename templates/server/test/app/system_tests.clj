(ns app.system-tests
  (:require
   [clojure.test :refer [deftest is]]
   [app.system :as system]
   [app.test-utils :refer [simplify]]
   [com.stuartsierra.component :as component]
   [com.walmartlabs.lacinia :as lacinia]))

(defn ^:private test-system
  "Creates a new system suitable for testing, and ensures that
  the HTTP port won't conflict with a default running system."
  []
  (-> (system/new-system)
      (assoc-in [:server :port] 8989)))

