(ns hobbit-smack.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(foo "test")

(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])

(defn matching-part
  "Match left-<body-part> with a right-<bod-part> where possible.
   Return the bodypart unchanged if there's no match."
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asymb-body-parts]
  (loop [remaining-asym-parts asymb-body-parts final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
               (into final-body-parts
                     (set [part (matching-part part)])))))))

(symmetrize-body-parts asym-hobbit-body-parts)


;; examples to understand let
(let [x 3] (+ x 3))

(def dalmatian-list
  ["Pongo" "Pango" "Tango" "Bango"])
(let [dalmatians (take 2 dalmatian-list)] dalmatians)
(take 2 dalmatian-list)

(def x 0)
(let [x 1] x)
(identity x)

(def x 0)
(let [x (inc x)] x)
(identity x)

(let [[pongo & dalmatians] dalmatian-list] [pongo dalmatians])

;; another example of let form in the symmetrizing function
;; 1) Create a new scope using let
;; 2) Assosciate part with the first element of remaining-asym-parts
;; 3) associate remaining with the rest of the elems
;; 4) 
;; (let [[part & remaining] remaining-asym-parts]
;;   (recur remaining
;;          (into final-body-parts)
;;          (set [part (matching-part part)])))

(into [] (set [:a :a]))

(loop [iteration 0]
  (println (str "Iteration " iteration))
  (if (> iteration 3)
    (println "Goodbye!")
    (recur (inc iteration))))

(re-find #"^left-" "left-eye")
(re-find #"^left-" "cleft-chin")
(re-find #"^left-" "wongleblart")

;; better symetrizer with reduce
;; reduce example
;; sum with reduce
(reduce + [1 2 3 4 5 5])

(reduce + 15 [1 2 3 4])

(reduce - 15 [1 2 3 4])

(- 1 5)

(defn my-reduce
  ([f initial coll]
  (loop [result initial
         remaining coll]
    (if (empty? remaining)
      result
      (recur (f result (first remaining)) (rest remaining))))) 
  ([f [head & tail]]
   (my-reduce f head tail)))
  
(my-reduce + 15 [1 2 3 4])

(defn better-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part)])))
          []
          asym-body-parts))

(better-symmetrize-body-parts asym-hobbit-body-parts)

(defn hit
  [asym-body-parts]
  
  (let [sym-parts (better-symmetrize-body-parts asym-body-parts)
        body-parts-size-sum (reduce + (map :size sym-parts))
        target (rand body-parts-size-sum)]
    (loop [[part & remaining] sym-parts
           accumulated-size (:size part)]
      (if (> accumulated-size target)
        part
        (recur remaining (+ accumulated-size (:size (first remaining))))))))

(hit asym-hobbit-body-parts)

(seq '(1 2 3))

(type (seq [1 2 3]))


(def human-cons [ 8 1 5 4])
(def critter-cons [0 0.2 0.33 1.11])
(defn unify-diet-data [human critter] {:human human :critter critter})
;; unify 1st el of human and critter consumption
(unify-diet-data (human-cons 1) (critter-cons 1))
;; unify all the consumption (all days)
(map unify-diet-data human-cons critter-cons)

(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))
(stats [3 4 10])

(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "spider-man" :real "peter parker"}
   {:alias "santa" :real "your mom"}
   {:alias "easter bunny" :real "your dad"}])

(map :real identities)

;; reducto
(reduce (fn [new-map [key val]]
          (assoc new-map key (inc val)))
        {:ewww 130}
        {:max 30 :min 10})

(assoc {:my "god" :your "nice"} :my "not" :key3 "ew")

;; using reducto to filter maps based on values of key:val

(defn more-than 
  "Filter out pairs when value of a pair is not greater than `than`"
  [_map than]
  (reduce (fn [new-map [key val]]
          (if (> val than)
            (assoc new-map key val)
            new-map))
        {}
        _map
        ))

(more-than {:human 4.1 :critter 3.9 :wupwup 30 :mupwup 3} 4)


