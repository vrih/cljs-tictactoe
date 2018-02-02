(ns cljs-tic-tac-toe.core
  (:require
   [reagent.core :as reagent]))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Vars

(defonce app-state
  (reagent/atom {:board [[0 0 0][0 0 0][0 0 0]] :player false :victory false}))


(def player-piece {0 "-" 1 "X" 2 "O"})
(def token {true 1 false 2})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Page

(defn three-in-a-row? [rows]
  (some true?
        (map #(apply = %)
             (filter #(not-any? (fn [x] (= 0 x)) %) rows))))

(defn victory-checker [b]
  (let [tl [[(get-in b [0 0]) (get-in b [1 1]) (get-in b [2 2])]
            [(get-in b [0 2]) (get-in b [1 1]) (get-in b [2 0])]]]
    (some true? (map three-in-a-row? [b (apply map list b) tl]))))

(defn move [x y]
  (let [current (get-in @app-state [:board y x])]
    (when (and (not (victory-checker (:board @app-state)))(= current 0))
      (swap! app-state assoc-in [:board y x] (token (:player @app-state)))
      (swap! app-state assoc :player (not (:player @app-state))))))

(defn render-row [y row]
  (map-indexed (fn [x piece] [:div {:class "piece" :on-click #(move x y) :key (str "piece" y x)} (player-piece piece)]) row))

(defn render-board [board]
  (map-indexed (fn [i row] [:div {:class "row" :key (str "row" i)} (render-row i row)]) board))

(defn page [ratom]
  [:div {:class "board"}
   (render-board (:board @ratom))
   (if (victory-checker)
     (str "Winner: " (player-piece (token (not (:player @app-state)))))
     (str "Player: " (player-piece (token (:player @app-state)))))])


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Initialize App

(defn dev-setup []
  (when ^boolean js/goog.DEBUG
    (enable-console-print!)
    (println "dev mode")
    ))

(defn reload []
  (reagent/render [page app-state]
                  (.getElementById js/document "app")))

(defn ^:export main []
  (dev-setup)
  (reload))
