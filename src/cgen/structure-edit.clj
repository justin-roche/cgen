(ns cgen.structure-edit
  (:require [rewrite-clj.node :as n] [rewrite-clj.zip :as z]))

(def inp (slurp "/Users/justinroche/projects/cgen/templates/client/src/main/my_project/app/core.cljs"))

(defn av-metadata? [n]
  (if (= (z/tag n) :meta)
    true
    false))

(defn walk-metadata [loc]
  (loop [zloc loc]
    (if (z/end? zloc)
      (z/root-string zloc)
      (if (av-metadata? zloc)
        (recur (z/next (z/remove zloc)))
        (recur (z/next zloc))))))

(println (walk-metadata (z/of-string inp {:track-position true})))

