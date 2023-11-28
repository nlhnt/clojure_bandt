(ns ch4-exercises.core
  (:gen-class)
  (:require clojure.string))

(def filename "src/data/suspects.csv")

(slurp filename)

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(convert :glitter-index "3")

(defn parse
  "Conver a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(parse (slurp filename))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(first (mapify (parse (slurp filename))))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(glitter-filter 3 (mapify (parse (slurp filename))))

;; Vampire Exercises (chapter 4)

;; 1. Turn the result of your glitter filter into a list of names.
;; (defn glitter-filter-names
;;   [minimum-glitter records]
;;   (let [res (filter #(>= (:glitter-index %) minimum-glitter) records)] (map :name res))
;;   )

(defn glitter-filter-names
  [minimum-glitter records]
  (map :name (glitter-filter minimum-glitter records)))

(glitter-filter-names 3 (mapify (parse (slurp filename))))
;; 2. Write a function, append, which will append a new suspect to your list of suspects.
(def suspect-list (parse (slurp filename)))
(identity suspect-list)

(defn append-suspect
  [suspect-list new-suspect]
  (conj suspect-list new-suspect))

(append-suspect suspect-list ["Amy Winehouse" "99"])
;; 3. Write a function, validate, which will check that :name and :glitter-index are present when you append. The validate function should accept two arguments: a map of keywords to validating functions, similar to conversions, and the record to be validated.
(def amy {:name "Amy Winehouse"})

(def validad-keys [:name :glitter-index])

(defn validate
  [kw-map value-record]
  (map (partial contains? value-record) kw-map))

(validate validad-keys amy)

(map (partial contains? amy) [:name :glitter-index])
;; 4. Write a function that will take your list of maps and convert it back to a CSV string. You’ll need to use the clojure.string/join function.
(def result-fn
  "results.csv")

(defn prep-csv
  [result-list]
  (clojure.string/join "\n" result-list))

(defn write-back-to-csv
  [result-csv-string result-fn]
  (spit result-fn result-csv-string))

(prep-csv (glitter-filter-names 3 (mapify (parse (slurp filename)))))

(write-back-to-csv (prep-csv (glitter-filter-names 3 (mapify (parse (slurp filename))))) result-fn)
;; Good luck, McFishwich!