(ns compojure-example.test.api
  (:require [clojure.test :refer :all]
            [compojure-example.api :refer :all]
            [ring.mock.request :as mock]
            [cheshire.core :as json]))

(deftest test-api
  (testing "API entry point returns status code 200"
    (let [response (rest-api (mock/request :get "/api"))]
      (is (= (:status response) 200))))

  (testing "API entry point returns content-type application/hal+json"
    (let [response (rest-api (mock/request :get "/api"))]
      (is (.contains (get (:headers response) "Content-Type") "application/hal+json"))))

  (testing "API entry point returns links to self and greet"
    (let [response (rest-api (mock/request :get "/api"))
          body (json/decode (:body response) true)]
      (is (= (-> body :_links :self :href) "http://localhost/api"))
      (is (= (-> body :_links :greet :href) "http://localhost/api/greet{?name}"))
      (is (= (-> body :_links :greet :templated) true))))

  (testing "Greet resource returns links to self"
    (let [response (rest-api (mock/request :get "/api/greet?name=John%20Doe"))
          body (json/decode (:body response) true)]
      (is (= (-> body :_links :self :href) "http://localhost/api/greet?name=John Doe"))))

  (testing "Greet resource returns greeting to greetee"
    (let [response (rest-api (mock/request :get "/api/greet?name=John%20Doe"))
          body (json/decode (:body response) true)]
      (is (= (:greeting body) "Greetings John Doe"))))

  (testing "Returns status code 404 when route is not found"
    (let [response (rest-api (mock/request :get "/api/invalid"))]
      (is (= (:status response) 404)))))
