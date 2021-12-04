
(ns cgen.core
  (:require
   [babashka.fs :refer [copy-tree delete-tree]]
   [cgen.templating :refer [render-files]]
   [cgen.fileutils :refer [rename-directories]]
   [malli.core :as m]
   [clojure.edn]))

(def resources-dir (System/getProperty "user.dir"))
(def working-dir "/Users/justinroche/projects/cgen-generated")

(def settings {:exts ["cljs", "clj", "html", "json", "edn"]
               :server-template-dir (str resources-dir "/templates/server/")
               :client-template-dir (str resources-dir "/templates/client/")
               :server-dest-dir (str working-dir "/server")
               :client-dest-dir (str working-dir "/server")
               :template-exts ["cljs", "clj", "html", "json", "edn"]
               :output-dir working-dir
               :project-name "tt"})

(defn create-server! []
  (copy-tree (:server-template-dir settings) (:server-dest-dir settings)))

(defn create-client! []
  (copy-tree (:client-template-dir settings) (:client-dest-dir settings)))

(defn delete-directories []
  (delete-tree (:client-dest-dir settings))
  (delete-tree (:server-dest-dir settings)))

(defn  main []
  (delete-directories)
  (create-client!)
  (create-server!)
  (render-files settings)
  (rename-directories [{:old (str  (:client-dest-dir settings) "/src/test/my_project")
                        :new (:project-name settings)}
                       {:old (str  (:client-dest-dir settings) "/src/main/my_project")
                        :new (:project-name settings)}]))

(main)

