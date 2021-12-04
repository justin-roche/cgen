(ns cgen.core
  (:require
   [clojure.java.io :as io]
   [clojure.edn]
   [clojure.string :as str]
   [clojure.reflect :as r]
   [vivid.art :as art]
   [clojure.pprint :refer [print-table]]
   [babashka.fs :refer [path copy-tree delete-tree create-dir walk-file-tree]]))

(def exts ["cljs", "clj", "html", "json", "edn"])
(def working_dir (System/getProperty "user.dir"))
(def server_template_dir (str working_dir "/templates/server/"))
(def client_template_dir (str working_dir "/templates/client/"))
(def project-name "tt")

(def dest_dir "/Users/justinroche/projects/cgen-generated/")
(def server_dest_dir "/Users/justinroche/projects/cgen-generated/server")
(def client_dest_dir "/Users/justinroche/projects/cgen-generated/client")

(defn create-server! [template]
  (let [template_dir (case template
                       "server"         server_dest_dir)]
    (copy-tree server_template_dir server_dest_dir)))

(defn create-client! [template]
  (let [template_dir (case template
                       "client"         client_dest_dir)]
    (copy-tree client_template_dir client_dest_dir)))

(defn render-file [upath uattr]
  (let [p (.toString upath)
        ext (last (str/split p #"\."))
        valid (some #(= ext %) exts)]
    (if valid
      (let [input (slurp p)
            output (art/render input {:bindings {'project-name "whoa"}})]
        (println (str "templating: " (.toString upath)))
        (spit p output)
        (keyword "continue"))
      (keyword "continue"))))

(defn render-files []
  (walk-file-tree client_dest_dir {:visit-file render-file
                                   :max-depth 100}))

(defn rename-directories []
  (let [old-name (str client_dest_dir "/src/test/my_project")
        new-name (str client_dest_dir "/src/test/" project-name)]
    (copy-tree old-name new-name)
    (delete-tree old-name)))

(defn delete-directories []
  (delete-tree client_dest_dir))

(defn  main []
  (delete-directories)
  (create-client! "client")
  ;; (create-server! "server")
  (rename-directories)
  (render-files))

(main)
