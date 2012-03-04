(ns prov.riak
  (:require
    [pallet.action.directory :as directory]
    [pallet.action.exec-script :as exec-script]
    [pallet.stevedore :as script]
    [pallet.action.package :as package]
    [pallet.stevedore :only (script)]
  ; [pallet.action.service :as service]
  ; [pallet.argument :as argument]
  ; [pallet.session :as session]
  ; [clojure.contrib.json :as json])
  ))
  ;(:use
  ; pallet.thread-expr))

;(defn download-riak
;  []
;  (script/exec-script
;    (mkdir "/tmp/riak-package")
;    (~download-file
;       "http://downloads.basho.com/riak/riak-1.0.2/riak_1.0.2-1_amd64.deb"
;       "/tmp/riak-package")
;    (cd "/tmp/riak-package")
;    (dpkg "-i" "riak_1.0.2-1_amd64.deb")))
  ;(rf/remote-file "/tmp/download-riak.sh"
  ;                :content
  ;                (script
  ;                 ;(~mkdir "/tmp/books")
  ;                 ;(cd "/tmp/books")
  ;                 (doseq [f ["pg20417.txt" "pg5000.txt" "pg4300.txt"
  ;                            "pg132.txt" "pg1661.txt" "pg972.txt" "pg19699.txt"]]
  ;                   (println @f)
  ;                   (~download-file
  ;                    (str "https://hadoopbooks.s3.amazonaws.com/" @f)
  ;                    (str "/tmp/books/" @f) )))
  ;                :owner hadoop-user
  ;                :group hadoop-user
  ;                :mode "0755"
  ;                :literal true))

(defn install
  "Installs riak, leaving all default configuration as-is."
  [session]
  ;(->
  ;  session
  ;  (download-riak)
  (->
    session
    (exec-script/exec-script
      (mkdir "/tmp/riak-package")
      (cd "/tmp/riak-package")
      (wget "http://downloads.basho.com/riak/riak-1.0.2/riak_1.0.2-1_amd64.deb")
      (dpkg "-i" "riak_1.0.2-1_amd64.deb"))
   ;(directory/directories
   ; (install-dirs (session/packager session))
   ; :mode "0770" :owner "couchdb" :group "couchdb")
   ;(package/package "couchdb")
   ;;; the package seems to start couch in some way that the init script
   ;;; can't terminate it, so we kill it.
   ;(exec-script/exec-checked-script
   ; "Kill and re-start couchdb"
   ; (pkill couchdb)
   ; (pkill beam))))
    ))

(defn riak
  "Ensures riak is installed (along with curl for testing) optionally configuring it as specified."
  [session & {:as option-keyvals}]
  (let [session (install session)]
    (-> session
        (package/package "curl")
        ;(service/service "couchdb" :action :restart)
        ;;; the sleeps are here because couchdb doesn't actually start taking
        ;;; sessions for a little bit -- it appears that the real process that's
        ;;; forked off is beam which ramps up couchdb *after* it's forked.
        ;(exec-script/exec-script (sleep 2))
        ;(configure option-keyvals)
        ;(service/service "couchdb" :action :restart)
        ;(exec-script/exec-script
        ; (sleep 2)
        ; (echo "Checking that couchdb is alive...")
        ; (curl "http://localhost:5984")))))
      )))

