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

(def riak-node
  (pallet.core/node-spec
    ; Amazon Linux AMI
    ;:image {:image-id "us-east-1/ami-31814f58"}))
    :image {:os-family :ubuntu
            :os-version-matches "10.10"
            :os-64-bit true}))

(def base-server
  (pallet.core/server-spec
    :phases {:bootstrap (phase/phase-fn (automated-admin-user/automated-admin-user))
             :configure (phase/phase-fn (riak/riak)
                                        (package/package "htop"))
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
