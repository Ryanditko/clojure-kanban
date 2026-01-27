(ns kanban.http.handlers.cards-test
  (:require [clojure.test :refer [deftest is testing]]
            [kanban.http.handlers.cards :as handlers]))

(def mock-db nil)

(deftest create-card-handler-invalid-data-test
  (testing "create-card rejects invalid card data - missing title"
    (let [response (handlers/create-card {:body-params {:status "todo"}
                                          :db mock-db})]
      (is (= 400 (:status response)))
      (is (= "Validation failed" (get-in response [:body :error]))))))

(deftest get-card-handler-not-found-test
  (testing "get-card returns 404 for nonexistent card"
    (let [response (handlers/get-card {:path-params {:id "fake-id"}})]
      (is (= 404 (:status response)))
      (is (= "Card not found" (get-in response [:body :error]))))))

(deftest update-card-handler-not-found-test
  (testing "update-card returns 404 for nonexistent card"
    (let [response (handlers/update-card {:path-params {:id "fake-id"}
                                          :body-params {:title "Test"}})]
      (is (= 404 (:status response))))))

(deftest delete-card-handler-not-found-test
  (testing "delete-card returns 404 for nonexistent card"
    (let [response (handlers/delete-card {:path-params {:id "fake-id"}})]
      (is (= 404 (:status response))))))

