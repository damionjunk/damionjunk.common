(ns damionjunk.structures)

;;
;; Maps

;; Taken from github search / modified .. seems ubiquitous
(defn deep-merge
  "Like merge, but merges maps recursively.

   Right most map values take precedence, nil's are removed."
  [& maps]
  (let [maps (keep identity maps)]
    (if (every? map? maps)
      (apply merge-with deep-merge maps)
      (last maps))))
