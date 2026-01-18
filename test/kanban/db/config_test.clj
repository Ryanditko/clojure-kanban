(ns kanban.db.config-test
  (:require [clojure.test :refer [deftest is testing]]
            [kanban.db.core :as db]))

(deftest read-config-test
  (testing "Reads configuration from db.edn"
    (let [config (db/read-config "db.edn")]
      (is (map? config))
      (is (contains? config :db)))))

(deftest config-has-required-keys-test
  (testing "Configuration contains required database keys"
    (let [config (db/read-config "db.edn")
          db-config (:db config)]
      (is (contains? db-config :jdbc-url))
      (is (contains? db-config :username))
      (is (contains? db-config :password))
      (is (contains? db-config :maximum-pool-size)))))

(deftest jdbc-url-key-exists-test
  (testing "JDBC URL key exists in configuration"
    (let [config (db/read-config "db.edn")
          db-config (:db config)]
      (is (contains? db-config :jdbc-url)))))

(deftest pool-size-is-positive-test
  (testing "Pool size is a positive number"
    (let [config (db/read-config "db.edn")
          pool-size (get-in config [:db :maximum-pool-size])]
      (is (number? pool-size))
      (is (pos? pool-size)))))

