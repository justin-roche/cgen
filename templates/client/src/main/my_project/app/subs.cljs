(ns tt.app.subs
  (:require [re-frame.core :refer [reg-sub subscribe]]))

;; implied db input signal (inefficient) followed by computation
(reg-sub
 :showing
 (fn [db _]
   (:showing db)))

(defn sorted-todos
  [db _]
  (:todos db))
(reg-sub :sorted-todos sorted-todos)

;; explicit db input signal (state slice) with explicit subscribe followed by computation
(reg-sub
 :todos
 (fn [query-v _]
   (subscribe [:sorted-todos]))
 (fn [sorted-todos query-v _]
   (vals sorted-todos)))

;; (reg-sub
;;  :visible-todos
;;  (fn [query-v _]
;;    [(subscribe [:todos])
;;     (subscribe [:showing])])

;;  (fn [[todos showing] _]
;;    (let [filter-fn (case showing
;;                      :active (complement :done)
;;                      :done   :done
;;                      :all    identity)]
;;      (filter filter-fn todos))))

;; explicit db input signal (state slice) with sugarfied subscribes followed by computation
(reg-sub
 :visible-todos
 :<- [:todos]
 :<- [:showing]
 (fn [[todos showing] _]
   (let [filter-fn (case showing
                     :active (complement :done)
                     :done   :done
                     :all    identity)]
     (filter filter-fn todos))))

(reg-sub
 :all-complete?
 :<- [:todos]
 (fn [todos _]
   (every? :done todos)))

(reg-sub
 :completed-count
 :<- [:todos]
 (fn [todos _]
   (count (filter :done todos))))

(reg-sub
 :footer-counts
 :<- [:todos]
 :<- [:completed-count]
 (fn [[todos completed] _]
   [(- (count todos) completed) completed]))
