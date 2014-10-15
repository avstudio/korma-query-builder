# Korma query builder

A Clojure library designed to build [Korma](http://sqlkorma.com/) queries dynamicly.


## Usage

Example: 
```clojure
(ns exmple 
  (:use [korma.query-builder]))

(select 
  (build-query table {:where {:user_id 5} :with [korma_entity_1 korma_entity_2] :limit 5 :offset 1 :fields [:id :title]}) )
```
### Scopes

Dynamic scopes are predefined  queries.

```clojure
(ns exmple 
  (:use [korma.query-builder]))


(defmethod scopes :active [r]
  {:active true})

(select 
  (build-query table {:scopes [:active] }) )
```  
is same as:

```clojure
(select 
  (build-query table {:where {:active true} }) )
``` 

Dynamic scopes can be combine with plain queries:
```clojure
(select 
  (build-query table {:scopes [:active] :where {:user_id 5} :with [korma_entity] }) ) 
```

### Lazy
```clojure
(def lazy-query
  (build-query table {:where {:active true} }))
```     
Invoke later:
```clojure   
(select lazy-query)
```  
