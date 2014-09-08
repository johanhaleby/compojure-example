(ns compojure-example.test.handler
  (:require [clojure.test :refer :all]
            [compojure-example.site :refer :all]
            [ring.mock.request :as mock]))

(deftest test-app
  (testing "main route"
    (let [response (site (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Hello World"))))
  
  (testing "not-found route"
    (let [response (site (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
