(ns tryouts.notifications
  (:require-macros [cljs.core.async.macros :refer [go-loop]])
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.async :refer [put! chan <!]]))

(enable-console-print!)

(def app-state (atom {}))

(defn notifications [_ owner]
  (reify
    om/IInitState
    (init-state [_]
      {:msg ""})
    om/IWillMount
    (will-mount [_]
      (let [notif (om/get-shared owner :notif)]
        (go-loop []
                 (let [msg (<! notif)]
                   (om/set-state! owner :msg msg))
                 (recur))))
    om/IRenderState
    (render-state [_ {:keys [msg]}]
      (dom/div nil msg))))

(defn application [app owner]
  (reify
    om/IRender
    (render [_]
      (dom/div #js {:className "container"}
               (dom/div #js {:className "page-header"} "Notifications Component")

               (om/build notifications (om/graft {} app))

               (dom/button #js {:type      "button"
                                :className "btn"
                                :onClick   (fn [_] (put! (om/get-shared owner :notif) "Notification 1"))}
                           "1")
               (dom/button #js {:type      "button"
                                :className "btn"
                                :onClick   (fn [_] (put! (om/get-shared owner :notif) "Notification 2"))}
                           "2")
               (dom/button #js {:type      "button"
                                :className "btn"
                                :onClick   (fn [_] (put! (om/get-shared owner :notif) ""))}
                           "Clear")))))

(om/root application
         app-state
         {:target (.getElementById js/document "application")
          :shared {:notif (chan)}})
