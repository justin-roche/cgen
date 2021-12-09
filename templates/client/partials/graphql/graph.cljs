
(ns tt.app.graph
  (:require [re-graph.core :as re-graph]
            [clojure.string :as str]
            [re-frame.core :as re-frame]))

(re-frame/dispatch
 [::re-graph/init
  {:ws {:url                     "ws://localhost:8888/graphql-ws"
        ;; :sub-protocol            "graphql-ws"
        :reconnect-timeout       5000
        :resume-subscriptions?   true
        :connection-init-payload {}
        :impl                    {}
        :supported-operations    #{:subscribe
                                   :query
                                   :mutate}}
   :http {:url    "http://localhost:8888/graphql"
          :impl   {}
          :supported-operations #{:query
                                  :mutate}}}])

(re-frame/reg-event-db
 :on-game
 (fn [db [_ {:keys [data errors] :as payload}]]
   (println payload)
   db))

(re-frame/dispatch [::re-graph/query
                    "{ game_by_id(id: 1234) { game_id name summary min_players max_players }}"
                    nil
                    [:on-game]])
