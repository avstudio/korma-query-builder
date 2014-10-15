# Korma query builder

A Clojure library designed to build Korma queries dynamically.


## Usage

Example: 

    (ns exmple 
      (:use [korma.query-builder]))
             
    (select 
      (build-query {:where {:user_id 5} :with [korma_entity] :limit 5 :offset 1}) )

### Scopes

Dynamic scopes can be combine with plain queries.

    (ns exmple 
      (:use [korma.query-builder]))
             
      
    (defmethod scopes :active [r]
      {:active true})

    (select 
      (build-query {:scopes [:active] }) ) will produce
      
is same as:
    
    (select 
      (build-query {:where {:active true} }) )
      
### Lazy

    (def lazy-query
      (build-query {:where {:active true} }))
      
Invoke later:
      
    (select lazy-query)
