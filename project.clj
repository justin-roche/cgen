(defproject cgen "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :main cgen.core/main
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [com.rpl/specter "1.1.3"]
                 [rewrite-clj "1.0.699-alpha"]
                 [de.ubercode.clostache/clostache "1.4.0"]
                 [metosin/malli "0.6.2"]
                 [clj-commons/conch "0.9.2"]
                 [vivid/art "0.5.0"]
                 [tupelo "21.11.06a"]
                 [babashka/fs "0.1.2"]]
  :repl-options {:init-ns cgen.core})
