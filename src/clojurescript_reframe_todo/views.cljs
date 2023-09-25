(ns clojurescript-reframe-todo.views
  (:require
   [re-frame.core :as re-frame]
   [clojurescript-reframe-todo.subs :as subs]
   [reagent.core :as reagent]))

(defn text-input [value on-change]
  [:div.field
   [:div.control
    [:input.input {:type "Text"
                   :placeholder "New todo"
                   :value value
                   :on-change #(on-change (-> % .-target .-value))}]]])



(defn todo-form []
  (let [state (reagent/atom {:current-input "" :todos []})]
    (reagent/create-class
     {:reagent-render
      (fn []
        [:form {:on-submit
                (fn [e]
                  (.preventDefault e)
                  (swap! state (fn [state] (update-in state [:todos] conj (:current-input state)))))}

         [:div (:current-input @state)]
         [text-input 
          (:current-input @state) 
          (fn [value] (swap! state assoc :current-input value))]
         [:button 
          {:type "Submit"} 
          "Add todo"]

         [:ul
          (for [item (:todos @state)] [:li {:key item} item])]])})))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1
      "What shall we" @name]
     [todo-form]]))
