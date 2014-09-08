(ns compojure-example.test.site
  (:require [clojure.test :refer :all]
            [compojure-example.site :refer :all]
            [ring.mock.request :as mock]))

(deftest test-site
  (testing "Site entry point returns status code 200"
    (let [response (site (mock/request :get "/"))]
      (is (= (:status response) 200))
      ))

  (testing "Returns status code 404 when route is not found"
    (let [response (site (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
