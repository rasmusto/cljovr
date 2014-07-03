(ns clojocular.utils
  (:import [org.saintandreas.math Matrix4f Quaternion Vector3f]
           [com.oculusvr.capi OvrMatrix4f OvrQuaternionf OvrVector3f Posef]))

(defn vector3f [^OvrVector3f v]
  (Vector3f. (.x v) (.y v) (.z v)))

(defn quaternion [^OvrQuaternionf q]
  (Quaternion. (.x q) (.y q) (.z q) (.w q)))

(defmulti matrix4f class)

(defmethod matrix4f Posef [p]
  (.mult (.rotate (Matrix4f.) (quaternion (.Orientation p)))
         (.translate (Matrix4f.) (vector3f (.Position p)))))

(defmethod matrix4f OvrMatrix4f [m]
  (.transpose (Matrix4f. (.M m))))
