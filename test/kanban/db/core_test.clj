(ns kanban.db.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [kanban.db.core :as core]))

(deftest read-config-function-exists-test
  (testing "read-config function is defined"
    (is (fn? core/read-config))))

(deftest make-datasource-function-exists-test
  (testing "make-datasource function is defined"
    (is (fn? core/make-datasource))))
