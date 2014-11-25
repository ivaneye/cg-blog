(ns cg-blog.core
  (:use markdown.core)
  (:use selmer.parser)
  (:require [clojure.string :as cstr])
  (:gen-class))


(set-resource-path! "/home/ivan/my/ivanpig.github.com/layout")

(def from-root "/home/ivan/my/mydoc/study/clojure/work/")
(def to-root "/home/ivan/my/ivanpig.github.com/")


(defn read-md-file [path file]
  (slurp (str path file ".md")))

(defn resolve-title [md-str]
  (cstr/replace-first md-str #"%" "# "))

(defn to-html [source]
  (md-to-html-string source))

(defn to-file [path file s]
  (spit (str path "posts/" file ".html") s))

(defn layout [path toc body]
  (let [layout (slurp (str path "layout/_posts.html"))
        _menu (slurp (str path "layout/_menu.html"))]
   (render-file "_posts.html" {:body body :toc toc})
    ))

(defn resolve-toc [md-str]
  (loop [*matcher* (re-matcher #"#+ +[^\n]+" md-str)
         find (re-find *matcher*)
         re []]
    (if find
      (recur *matcher* (re-find *matcher*) (conj re (cstr/replace find #"[# ]" "")))
      re)))

(defn wrap-li [s]
  (str "<li><a name='anchor' href=\"#" s "\">" s "</a></li>"))

(defn resolve-h1-id [html-str]
  (cstr/replace html-str #"<h1>([^</h1>]+)</h1>" "<h1 id=\"$1\">$1</h1>"))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  )


(let [md-str (read-md-file from-root "clojure")
      toc (map wrap-li (resolve-toc md-str))
      f-toc (str "<ul>" (cstr/join toc) "</ul>")]
  (->> md-str
     resolve-title
     to-html
     resolve-h1-id
     (layout to-root f-toc)
     (to-file to-root "clojure")))
