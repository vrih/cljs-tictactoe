(ns cljs-tic-tac-toe.test-core
  (:require [cljs-tic-tac-toe.core :as sut]
            [cljs.test :as t :include-macros true]))

(t/deftest test-three-in-a-row
  (t/testing "Non-matching rows aren't victory"
             (t/is (not (true? (sut/three-in-a-row? [[2 1 0] [0 1 2] [2 1 0]])))))
  (t/testing "Matching rows are victory"
             (t/is (true? (sut/three-in-a-row? [[2 2 2] [0 1 2] [2 1 0]])))))

(t/deftest test-victory-conditions
  (t/testing "Horizontal victory"
    (t/is (true? (sut/victory-checker [[2 1 0] [1 1 1] [0 2 1]]))))
  (t/testing "No victory"
    (t/is (not (true? (sut/victory-checker [[0 0 0][0 0 0][0 0 0]])))))
  (t/testing "Vertical victory"
    (t/is (sut/victory-checker [[0 1 0][0 1 0][0 1 0]])))
  (t/testing "Diagnoal victory"
    (t/is (sut/victory-checker [[1 0 0][0 1 0][0 0 1]]))))
  
(t/run-tests)
