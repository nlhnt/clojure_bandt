(ns ch5-exercises.core
  (:gen-class))

(def great-baby-name "Rosanthony")
great-baby-name

(let [great-baby-name "Bloodthunder"]
  great-baby-name)

great-baby-name

(defn sum
  ([vals] (sum vals 0))
  ([vals accumulating-total]
   (if (empty? vals)
     accumulating-total
     (sum (rest vals) (+ (first vals) accumulating-total))))
  )
(sum [1 2 3 4 5 6])

(require '[clojure.string :as s])
(defn clean
  [text]
  (s/replace (s/trim text) #"lol" "LOL"))

(clean "My boad constrictor is so sassy lol!    ")

(def character
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})
(def c-int (comp :intelligence :attributes))
(def c-str (comp :strength :attributes))
(def c-dex (comp :dexterity :attributes))


(c-int character)
(c-str character)
(c-dex character)

(:attributes character)

(defn spell-slots
  [char]
  (int (inc (/ (c-int char) 2))))

(spell-slots character)

;; (def spell-slots-comp (comp int inc #(/ % 2) c-int))
(def spell-slots-comp (comp int inc (fn [x] (/ x 2)) c-int))

(spell-slots-comp character)

;; memoize
(defn sleepy-identity
  "returns the given value after 1 second"
  [x]
  (Thread/sleep 1000)
  x)
(sleepy-identity "Mr. Fantasticf")

(def memo-sleepy-identity (memoize sleepy-identity))
;; first call takes a sec, later calls are called from cache
(memo-sleepy-identity "Mr. Fantasticf")

;; Excercises ch5

;; 1) You used (comp :intelligence :attributes) to create a function that returns a character’s intelligence. Create a new function, attr, that you can call like (attr :intelligence) and that does the same thing.

character

(defn attr2 [x character] ((comp x :attributes) character))

(attr2 :intelligence character)

(defn attr
  [k]
  (fn [obj] ((comp k :attributes) obj)))

(defn attr2
  [k obj]
  ((comp k :attributes) obj))

((attr :intelligence) character)

(attr2 :intelligence character)

(attr2 :strength character)