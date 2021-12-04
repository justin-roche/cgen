(ns cgen.structure-edit
  (:require
   [com.rpl.specter :as specter]
   [clojure.edn]
   [tupelo.core :as t]
   [babashka.fs :refer [copy-tree delete-tree]]))

(defn change-ns-name []
;;   (let [input (slurp filepath)
;;         data (clojure.edn/read-string (str "[" input "]"))
;;         output  (map (fn [form]
;;                        (println (type form))
;;                        (if (= 'ns (first form))
;;                          (apply list (t/replace-at form 1 (:project-name vars)))
;;                          form)) data)
;;         out-str (prn-str output)]
  (spit filepath out-str) (println "finished"))

(i)





