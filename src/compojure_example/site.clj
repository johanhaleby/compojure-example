(ns compojure-example.site
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [clojure.java.io :as io]))

(defroutes site-routes
           (GET "/" [] (slurp (io/resource "public/index.html")))
           (route/resources "/")
           (route/not-found (slurp (io/resource "404.html"))))

(def site
  (handler/site site-routes))
