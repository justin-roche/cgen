
(ns cgen.core
  (:require
   [babashka.fs :refer [copy-tree delete-tree exists?]]
   [cgen.templating :refer [render-files]]
   [me.raynes.conch :refer [programs]]
   [clojure.java.shell :refer [sh]]
   [cgen.fileutils :refer [rename-directories]]
   [malli.core :as m]
   [clojure.edn]))

(def resources-dir (System/getProperty "user.dir"))
(def working-dir "/Users/justinroche/projects/cgen-generated")

(def default-settings {:output-dir working-dir
                       :project-name "tt"
                       :template-exts ["cljs", "clj", "html", "json", "edn"]
                       :db-template-dir (str resources-dir "/templates/db/")
                       :server-template-dir (str resources-dir "/templates/server/")
                       :client-template-dir (str resources-dir "/templates/client/")
                       :db-dest-dir (str working-dir "/db")
                       :server-dest-dir (str working-dir "/server")
                       :client-dest-dir (str working-dir "/client")})

(defn create-db! [settings]
  (copy-tree (:db-template-dir settings) (:db-dest-dir settings)))

(defn create-server! [settings]
  (copy-tree (:server-template-dir settings) (:server-dest-dir settings)))

(defn create-client! [settings]
  (copy-tree (:client-template-dir settings) (:client-dest-dir settings)))

(defn delete-directories [settings]
  (if (exists? (:client-dest-dir settings))
    (delete-tree (:client-dest-dir settings))
    (println "client dir does not exist"))
  (if (exists? (:db-dest-dir settings))
    (delete-tree (:db-dest-dir settings)))
  (if (exists? (:server-dest-dir settings))
    (delete-tree (:server-dest-dir settings))))

(defn run-shells []
  (let [p (str  (:client-dest-dir default-settings))]
    (println "npm installing...")
    (println (:out (sh "npm" "install" :dir p)))
    (println "lein deps...")
    (println (:out (sh "lein" "deps"
                       ;; :env (dissoc current-env "CLASSPATH")
                       :dir (:server-dest-dir default-settings))))
    ;; (println "lein run...")
    ;; (println (:out (sh "lein" "run" :dir (:server-dest-dir default-settings))))
    ))

(def Settings
  (m/schema
   [:map
    [:client-template-dir :string]
    [:client-dest-dir :string]
    [:db-template-dir :string]
    [:db-dest-dir :string]
    [:server-template-dir :string]
    [:project-name :string]
    [:server-dest-dir :string]
    [:output-dir :string]]))

(defn generate-project [settings]
  (if (m/validate Settings settings)
    (do
      (delete-directories settings)
      (create-client! settings)
      (create-server! settings)
      (create-db! settings)
      (render-files (merge settings {:exts ["cljs", "clj", "json", "edn",
                                            "html"]}))
      (rename-directories [{:old (str  (:client-dest-dir settings) "/src/test/my_project")
                            :new (:project-name settings)}
                           {:old (str  (:client-dest-dir settings) "/src/main/my_project")
                            :new (:project-name settings)}]))))

(defn main []
  (generate-project default-settings)
  (run-shells))

(comment
  (main))

;; (main)

;; (println "complete")
