(defproject clojocular "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :native-path "target/native"
  :dependencies [[org.clojure/clojure "1.6.0"]

                 [org.lwjgl.lwjgl/lwjgl "2.8.4"]
                 [org.lwjgl.lwjgl/lwjgl-platform "2.8.4"
                  :classifier "natives-windows"
                  :native-prefix ""]

                 [org.saintandreas/jocular "2.0.3"]
                 [org.saintandreas/glamour-lwjgl "1.0.6"]
                 [org.saintandreas/math "1.0.4"]
                 [org.saintandreas/oria-resources "1.0.2"]
                 [org.saintandreas/jovr "0.3.2.4"]]
  ; :java-source-paths ["src/main/java"]
  )
