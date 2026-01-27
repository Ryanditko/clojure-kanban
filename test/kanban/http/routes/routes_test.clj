(ns kanban.http.routes.routes-test
  (:require [clojure.test :refer [deftest is testing]]
            [kanban.http.routes :as routes]))

(deftest app-routes-defined-test
  (testing "app-routes function is defined"
    (is (fn? routes/app-routes))))

