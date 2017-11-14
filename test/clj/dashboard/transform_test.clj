(ns clj.dashboard.transform-test
  (:require  [dashboard.transform :as sut]
             [clojure.edn :as edn]
             [clojure.test :refer [deftest testing is]]))

(testing "transform"
  (deftest transform
   (let [actual (sut/transform (edn/read-string (slurp "resources/05-configured.edn")))
          expected (edn/read-string (slurp "resources/06-transformed.edn"))]
     (is (= expected actual)))))
