(ns cgen.fileutils
  (:require
   [babashka.fs :refer [copy-tree delete-tree]]
   [clojure.edn]
   [clojure.string :as str]))

(defn remove-last-path-element [path]
  (str/join "/" (reverse (drop 1 (reverse (str/split path #"/"))))))

(defn rename-directory [d]
  (let [old-name (:old d)
        new-name (str (remove-last-path-element (:old d)) "/" (:new d))]
    (println (str "renaming: " old-name ))
    (copy-tree old-name new-name)
    (delete-tree old-name)
  new-name))

(defn rename-directories [dirs]
  (println dirs)
  (doall (map rename-directory dirs)))

(comment
  (remove-last-path-element "/Users/justinroche/projects/cgen-generated/"))

(comment
  (do
    (def client_dest_dir "/Users/justinroche/projects/cgen-generated/client")
    (rename-directories [{:old (str client_dest_dir "/src/test/my_project")
                          :new "tt"}])))
(comment
  (do
    (def client_dest_dir "/Users/justinroche/projects/cgen-generated/client")
    (rename-directories [{:old (str client_dest_dir "/src/main/my_project")
                          :new "tt"}])))
