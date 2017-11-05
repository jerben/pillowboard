(ns clj.dashboard.event-test
  (:require [dashboard.event :as sut]
            [clojure.edn :as edn]
            [clojure.test :refer [deftest testing is]]))

(testing "event"
  (deftest process-event-empty-state  
    (let [fun #'dashboard.event/process-event
          event {:time 1 :label :foo :value 42}
          expected {:content {:foo {:data [[1] [42]] :meta {:labels [:time :foo]}}}
                    :config {:foo {:type :line}}}]
      (is (= expected (fun event {})))))

  (deftest process-event-on-state 
    (let [fun #'dashboard.event/process-event
          event {:time 1 :label :foo :value 42}
          expected {:content {:what {:data [[1] [2]] :meta {:labels [:time :ever]}}
                              :foo {:data [[1] [42]] :meta {:labels [:time :foo]}}}
                    :config {:what {:type :line}
                             :foo {:type :line}}}]
      (is (= expected
             (fun event
                  {:content {:what {:data [[1] [2]]
                                    :meta {:labels [:time :ever]}}}
                   :config {:what {:type :line}}})))))

  (deftest process-events
    (let [fun #'dashboard.event/process-events
          events [{:time 1 :label :foo :value 42}
                  {:time 2 :label :bar :value 7}
                  {:time 2 :label :foo :value 24}]
          expected {:content {:bar {:data [[2] [7]] :meta {:labels [:time :bar]}}
                              :foo {:data [[1 2] [42 24]] :meta {:labels [:time :foo]}}}
                    :config {:bar {:type :line}
                             :foo {:type :line}}}]
      (is (= expected (fun events))))))
