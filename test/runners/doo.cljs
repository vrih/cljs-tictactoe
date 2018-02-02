(ns runners.doo
  (:require [doo.runner :refer-macros [doo-all-tests]]
                                      [cljs-tic-tac-toe.test-core]))

(doo-all-tests #"(cljs-tic-tac-toe)\..*-test")










