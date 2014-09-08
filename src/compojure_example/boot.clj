(ns compojure-example.boot
  (:require [compojure-example.api :refer [rest-api]]
            [compojure-example.site :refer [site]]
            [compojure.core :refer [routes]]))

; Combine the site and rest-api
(def site-and-api (routes rest-api site))
