(ns kanban.domain.cards
  (:require [kanban.db.card :as db]))

(defonce cards-db (atom {}))

(def valid-statuses #{"todo" "doing" "done"})

(defn valid-status? [status]
  (contains? valid-statuses status))

(defn- current-timestamp []
  (str (java.time.Instant/now)))

(defn list-cards [datasource query-params]
  (let [status (:status query-params)]
    (if status
      (db/find-cards-by-status datasource status)
      (db/find-all-cards datasource))))

(defn create-card! [datasource card]
  (let [card' (update card :description #(or % ""))]
    (db/insert-card datasource card')))

(defn get-card [id]
  (get @cards-db id))

(defn update-card! [id updates]
  (when-not (get-card id)
    (throw (ex-info "Card not found" {:type :not-found :id id})))
  (when-let [new-status (:status updates)]
    (when-not (valid-status? new-status)
      (throw (ex-info "Invalid status" {:type :invalid-status
                                        :status new-status
                                        :valid-statuses valid-statuses}))))
  (let [current-card (get-card id)
        updated-card (-> current-card
                         (merge updates)
                         (assoc :id id)
                         (assoc :updated-at (current-timestamp)))]
    (swap! cards-db assoc id updated-card)
    updated-card))

(defn delete-card! [id]
  (if (get-card id)
    (do (swap! cards-db dissoc id) true)
    false))

(defn move-card! [id new-status]
  (when-not (valid-status? new-status)
    (throw (ex-info "Invalid status" {:type :invalid-status
                                      :status new-status
                                      :valid-statuses valid-statuses})))
  (update-card! id {:status new-status}))

(defn clear-all! []
  (reset! cards-db {}))
