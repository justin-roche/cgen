(ns cgen.templating
  (:require
   [clojure.edn]
   [clojure.string :as str]
   [vivid.art :as art]
   [babashka.fs :refer [walk-file-tree]]))

(defn render-file [settings upath uattr]
  (let [p (.toString upath)
        ext (last (str/split p #"\."))
        valid (some #(= ext %) (:exts settings))]
    (if valid
      (let [input (slurp p)
            output (art/render input {:bindings {'project-name (:project-name settings)}})]
        (if (not (= input output))
          (do
            (println (str "templating: " (.toString upath)))
            (spit p output)))
        (keyword "continue"))
      (keyword "continue"))))

(defn render-files [settings]
  (let [f  (partial render-file settings)]
    (walk-file-tree (:output-dir settings) {:visit-file f
                                            :max-depth 100})))

(comment
  (render-files settings))
