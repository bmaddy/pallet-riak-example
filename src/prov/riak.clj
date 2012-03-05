(ns prov.riak
  (:require
    [pallet.action.directory :as directory]
    [pallet.action.exec-script :as exec-script]
    [pallet.stevedore :as script]
    [pallet.action.package :as package]
    [pallet.stevedore :only (script)]
  ; [clojure.contrib.json :as json])
  ))

(defn install
  "Installs riak, leaving all default configuration as-is."
  [session]
  (->
    session
    (exec-script/exec-script
      (mkdir "/tmp/riak-package")
      (cd "/tmp/riak-package")
      (wget "http://downloads.basho.com/riak/riak-1.0.2/riak_1.0.2-1_amd64.deb")
      (dpkg "-i" "riak_1.0.2-1_amd64.deb"))
    ))

(defn riak
  "Ensures riak is installed (along with curl for testing) optionally configuring it as specified."
  [session & {:as option-keyvals}]
  (let [session (install session)]
    (-> session
        (package/package "curl")
      )))

