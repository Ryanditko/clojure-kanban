(ns kanban.http.schemas.cards-test
  (:require [clojure.test :refer [deftest is testing]]
            [malli.core :as m]
            [kanban.http.schemas.cards :as schemas]))

(deftest create-card-schema-valid-test
  (testing "Validates correct CreateCardRequest"
    (is (m/validate schemas/CreateCardRequest
                    {:title "Test Card" :status "todo"}))
    (is (m/validate schemas/CreateCardRequest
                    {:title "Test" :description "Details" :status "doing"}))))

(deftest create-card-schema-missing-title-test
  (testing "Rejects missing title"
    (is (not (m/validate schemas/CreateCardRequest {:status "todo"})))))

(deftest create-card-schema-empty-title-test
  (testing "Rejects empty title"
    (is (not (m/validate schemas/CreateCardRequest {:title "" :status "todo"})))))

(deftest create-card-schema-invalid-status-test
  (testing "Rejects invalid status"
    (is (not (m/validate schemas/CreateCardRequest {:title "Test" :status "invalid"})))))

(deftest update-card-schema-valid-test
  (testing "Validates correct UpdateCardRequest"
    (is (m/validate schemas/UpdateCardRequest {:title "Updated"}))
    (is (m/validate schemas/UpdateCardRequest {:status "doing"}))
    (is (m/validate schemas/UpdateCardRequest {}))))

(deftest update-card-schema-invalid-status-test
  (testing "Rejects invalid status"
    (is (not (m/validate schemas/UpdateCardRequest {:status "invalid"})))))

(deftest move-card-schema-valid-test
  (testing "Validates correct MoveCardRequest"
    (is (m/validate schemas/MoveCardRequest {:status "todo"}))
    (is (m/validate schemas/MoveCardRequest {:status "doing"}))
    (is (m/validate schemas/MoveCardRequest {:status "done"}))))

(deftest move-card-schema-missing-status-test
  (testing "Rejects missing status"
    (is (not (m/validate schemas/MoveCardRequest {})))))

(deftest move-card-schema-invalid-status-test
  (testing "Rejects invalid status"
    (is (not (m/validate schemas/MoveCardRequest {:status "invalid"})))))

(deftest validate-function-throws-on-error-test
  (testing "validate! throws exception on invalid data"
    (is (thrown-with-msg? clojure.lang.ExceptionInfo #"Validation failed"
                          (schemas/validate! schemas/CreateCardRequest {:status "todo"})))))