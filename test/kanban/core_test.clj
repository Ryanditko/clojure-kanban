(ns kanban.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [kanban.core :as core]))

(deftest port-is-defined-test
  (testing "PORT constant is defined"
    (is (number? core/PORT))
    (is (= 3000 core/PORT))))
