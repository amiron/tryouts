(ns tryouts.service.core
  (:require [ring.util.response :refer [file-response]]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.edn :refer [wrap-edn-params]]
            [compojure.core :refer [defroutes GET PUT]]
            [compojure.route :as route]
            [compojure.handler :as handler]))

(defn response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/edn"}
   :body (pr-str data)})

(defn index []
  (file-response "index.html" {:root "resources/public"}))

(defroutes routes
           (GET "/" [] (index))
           (route/files "/" {:root "resources/public"}))

(def app
  (-> routes
      wrap-edn-params))