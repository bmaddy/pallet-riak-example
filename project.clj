(defproject prov "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [
                 ;[org.clojure/clojure "1.2.0"]
                 ;[org.clojure/clojure-contrib "1.2.0"]
                 [org.cloudhoist/pallet "0.6.7"]
                 [org.cloudhoist/pallet-crates-all "0.5.0"]
                 [org.jclouds/jclouds-all "1.0.0"]
                 [org.cloudhoist/automated-admin-user "0.5.0"]
                 ; Logging stuff that makes things super slow
                 ;[org.slf4j/slf4j-api "1.6.1"]
                 ;[ch.qos.logback/logback-core "1.0.0"]
                 ;[ch.qos.logback/logback-classic "1.0.0"]
                 ;[org.jclouds.driver/jclouds-jsch "1.0.0"]
                 ;[org.jclouds.driver/jclouds-slf4j "1.0.0"]
                 ]
  :repositories {"sonatype"
                 "http://oss.sonatype.org/content/repositories/releases"
                 "sonatype-snapshots"
                 "http://oss.sonatype.org/content/repositories/snapshots"})

