(ns prov.core
  (:require [prov.riak :as riak]
            [pallet.core]
            [pallet.utils :as utils]
            [pallet.phase :as phase]
            [pallet.action.package :as package]
            [pallet.action.exec-script :as exec-script]
            [pallet.compute :as compute]
            [pallet.crate.ssh :as ssh]
            [pallet.crate.public-dns-if-no-nameserver :as dns]
            [pallet.crate.automated-admin-user :as automated-admin-user]))

(def service
  (compute/service :aws))

;(def riak-node
;  (pallet.core/node-spec
;    :image {:os-family :ubuntu :os-version-matches "10.10"}
;    ; Amazon Linux AMI
;    ;:image {:image-id "us-east-1/ami-31814f58"}
;    :network {:inbound-ports [22]}
;    :qos {:enable-monitoring true}))
(def riak-node
  (pallet.core/node-spec
    :image {:os-family :ubuntu
            :os-version-matches "10.10"
            :os-64-bit true}))

(def base-server
  (pallet.core/server-spec
    :phases {:bootstrap (phase/phase-fn (automated-admin-user/automated-admin-user))
             :configure (phase/phase-fn (riak/riak))
             ;             (ssh/openssh)
             ;             (exec-script/exec-script
             ;               (ls "'/tmp'"))
             ;             (exec-script/exec-script
             ;               (println "'downloading riak'")
             ;               (mkdir "'/tmp/riak-package'")
             ;               (cd "'/tmp/riak-package'")
             ;               (wget "'http://downloads.basho.com/riak/riak-1.0.2/riak_1.0.2-1_amd64.deb'")
             ;               (println "'...done'")
             ;               (println "'installing riak'")
             ;               (yum install dpkg)
             ;               (dpkg "-i" "riak_1.0.2-1_amd64.deb"))
             ;               ;(println "'...done'")
             ;             (ssh/iptables-accept)
             ;             (package/package "htop")
                          }))

(def riak
  (pallet.core/group-spec
    "basic-group" :extends [base-server] :node-spec riak-node))

(defn list-nodes []
  (compute/nodes service))

(defn scale-cluster [n]
  (pallet.core/converge
    {riak n}
    :compute service))

;(scale-cluster 1)
