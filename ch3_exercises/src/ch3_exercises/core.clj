(ns ch3-exercises.core
  (:gen-class))

;; Chapter 3 Excercises
;; 1) Use the str, vector, list, hash-map and hash-set functions
;; str function concatenates string values
(str "Hey" " " "Ho")
;; vector function returns a new vector using the provided args
(vector 1 2 3 4 5)
;; list function returns a new list sing the args
(def mylist (list 1 2 3 4 5))
(identity mylist)
;; hash-map creates a hashmap
(hash-map :bad "juju", :lack "stuff", :bad2 "juju")
;; hash-set like above but hash-set
(hash-set :bad "juju", :bad "juju2")

;; 2) Write a function that takes a number and adds 100 to it.
(def add100 (partial + 100))
(add100 300)
(defn add100n
  [x]
  (+ 100 x))
(add100n 10)

;; 3) write a function dec-maker that works exactly like the function
;; inc-maker except with subtraction
(defn dec-maker
  "Create a custom decrementer"
  [dec-by]
  #(- % dec-by))
(defn dec-maker
  "Create a custom decrementer"
  [dec-by]
  (fn [x] (- x dec-by)))
(def dec9 (dec-maker 9))
(dec9 10)

;; 4) write a mapset function that works like map but returns a set
(map  str ["hey" " " "ho" " " "hey"])
(defn mapset
  "Map a function over a collection but return a set"
  [f col]
  (set (map f col))
  )
(map inc [1 1 2 2])

(set (map inc [1 1 2 2]))

(mapset inc [1 1 2 2])
