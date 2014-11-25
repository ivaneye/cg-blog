(defproject cg-blog "0.1.0-SNAPSHOT"
  :description "A static blog generator write by Clojure"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [markdown-clj "0.9.58"]
                 [selmer "0.7.4"]]
  :main ^:skip-aot static-blog.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
