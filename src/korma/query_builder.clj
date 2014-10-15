(ns korma.query-builder
  (:use [korma.core]))

(defmulti scopes (fn[n]
                   (if (keyword? n)
                     n
                     (first (keys n )))
                   ))

(defmethod scopes :default [r]
  {})

;; idea from http://stackoverflow.com/questions/12045825/clojure-dynamically-compose-query-with-korma
(defn- comp-query [q [func arg]]
  (let [sql-fn (ns-resolve 'korma.core (-> func name symbol))]
    (apply sql-fn q arg)))


(defn- parse-scopes 
  [table query]
  (if-let[s (:scopes query)]
    (reduce #(where %1 (scopes %2)) table s )
    table))


(defn- build-with-clause
  [table query]
  (if-let[s (:with query)]
    (reduce #(with %1 %2) table s )
    table))

(defn- build-where-clause 
  [table query]
  (if-let[s (:where query)]
    (reduce (fn[tbl [k v]] (where tbl {k v})) table s )
    table))


(defn build-query
  [t  params]
  (-> (reduce comp-query (select* t) (select-keys params [:limit :offset :fields]))
      (build-with-clause   params)
      (parse-scopes        params)
      (build-where-clause  params)))
