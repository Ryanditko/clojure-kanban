(ns kanban.domain.cards-test
  (:require [clojure.test :refer [deftest is testing]]
            [kanban.domain.cards :as cards]))

(deftest valid-status-test
  (testing "Validates correct statuses"
    (is (true? (cards/valid-status? "todo")))
    (is (true? (cards/valid-status? "doing")))
    (is (true? (cards/valid-status? "done")))
    (is (false? (cards/valid-status? "invalid")))
    (is (false? (cards/valid-status? "pending")))
    (is (false? (cards/valid-status? nil)))))
