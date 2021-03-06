(ns damionjunk.config
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [damionjunk.structures :refer [deep-merge]])
  (:import [java.io PushbackReader]))

(def default-base-path (str (System/getProperty "user.home")
                            "/.config/damionjunk/"))
(def default-pub-config-file  "damionjunk.pub.edn")
(def default-priv-config-file "damionjunk.priv.edn")

(defn get-config
  [filename]
  (try
    (with-open [f (-> filename io/reader PushbackReader.)]
      (edn/read f))
    (catch Exception e (println "Configuration read error:" (.getLocalizedMessage e)))))

(defn get-app-config
  "This function is opinionated. It's looking for files at a specific location
   by default.

   Override with kw arguments:
   basepath - filesystem base path
   pub - public config file name
   priv - private config (passwords, etc) file name

   Usage:

   (get-app-config :someappkey)

   see:

   damionjunk.common/default-base-path
   damionjunk.common/default-pub-config-file
   damionjunk.common/default-priv-config-file"
  [app-kw & {:keys [basepath pub priv] :or {basepath default-base-path
                                            pub default-pub-config-file
                                            priv default-priv-config-file}}]
  (let [pub-cfg  (get-config (str basepath pub))
        priv-cfg (if priv (get-config (str basepath priv)))]
    (app-kw (deep-merge pub-cfg priv-cfg))))