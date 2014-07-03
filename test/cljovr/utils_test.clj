(ns cljovr.utils-test
  (:require [clojure.test :refer :all]
            [cljovr.utils :refer :all])
  (:import [org.saintandreas.math Matrix4f Quaternion Vector3f]
           [com.oculusvr.capi OvrMatrix4f OvrQuaternionf OvrVector3f Posef]))

(deftest matrix4f-test
  (Matrix4f.)
  (matrix4f (Posef.))
  (matrix4f (OvrMatrix4f.)))
