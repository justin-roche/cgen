(ns tt.app.events
  (:require
   [tt.app.db    :refer [default-db todos->local-store]]
   [re-frame.core :refer [reg-event-db reg-event-fx inject-cofx path after]]
   [cljs.spec.alpha :as s]))

(defn check-and-throw
  "Throws an exception if `db` doesn't match the Spec `a-spec`."
  [a-spec db]
  (when-not (s/valid? a-spec db)
    (throw (ex-info (str "spec check failed: " (s/explain-str a-spec db)) {}))))

(def check-spec-interceptor (after (partial check-and-throw :tt.app.db/db)))

(def ->local-store (after todos->local-store))

(def todo-interceptors [check-spec-interceptor
                        (path :todos)
                        ->local-store])

;; -- Helpers -----------------------------------------------------------------

(defn allocate-next-id
  "Returns the next todo id. Assumes todos are sorted. Returns one more than the current largest id."
  [todos]
  ((fnil inc 0) (last (keys todos))))

;; -- (Fx) Event Handlers (return event map) -----------------------------------------------------------------

(reg-event-fx
 :initialise-db
 [(inject-cofx :local-store-todos)
  check-spec-interceptor]

 (fn [{:keys [db local-store-todos]} _]
   {:db (assoc default-db :todos local-store-todos)}))   ;; all hail the new state to be put in app-db

;; -- (Db) Event Handlers (return Db value) -----------------------------------------------------------------

;; (reg-event-db
;;  :set-showing
;;  [check-spec-interceptor]
;;  (fn [db [_ new-filter-kw]]
;;    (assoc db :showing new-filter-kw)))

;; -- Sliced/Pathed Event Handlers -----------------------------------------------------------------

(reg-event-db
 :set-showing
 [check-spec-interceptor (path :showing)]
 (fn [old-showing-value [_ new-showing-value]]
   new-showing-value))

(reg-event-db
 :add-todo
 todo-interceptors
 (fn [todos [_ text]]
   (let [id (allocate-next-id todos)]
     (assoc todos id {:id id :title text :done false}))))

(reg-event-db
 :toggle-done
 todo-interceptors
 (fn [todos [_ id]]
   (update-in todos [id :done] not)))

(reg-event-db
 :save
 todo-interceptors
 (fn [todos [_ id title]]
   (assoc-in todos [id :title] title)))

(reg-event-db
 :delete-todo
 todo-interceptors
 (fn [todos [_ id]]
   (dissoc todos id)))

(reg-event-db
 :clear-completed
 todo-interceptors
 (fn [todos _]
   (let [done-ids (->> (vals todos)         ;; which todos have a :done of true
                       (filter :done)
                       (map :id))]
     (reduce dissoc todos done-ids))))      ;; delete todos which are done

(reg-event-db
 :complete-all-toggle
 todo-interceptors
 (fn [todos _]
   (let [new-done (not-every? :done (vals todos))]   ;; work out: toggle true or false?
     (reduce #(assoc-in %1 [%2 :done] new-done)
             todos
             (keys todos)))))
