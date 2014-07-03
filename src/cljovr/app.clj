(ns cljovr.app
  (:require [cljovr.utils :as utils])
  (:import [java.awt Rectangle]
           [org.lwjgl.opengl ContextAttribs PixelFormat]
           [org.saintandreas.gl FrameBuffer MatrixStack]
           [org.saintandreas.gl.app LwjglApp]
           [org.saintandreas.math Matrix4f]

           [com.oculusvr.capi EyeRenderDesc FovPort HmdDesc OvrLibrary
            OvrVector2i Posef RenderAPIConfig Texture TextureHeader]
           com.oculusvr.capi.OvrLibrary$ovrRenderAPIType
           com.oculusvr.capi.Hmd
           ))

(comment
 (let [projections [(Matrix4f.) (Matrix4f.)]]
   projections))

(def ipd (. OvrLibrary OVR_DEFAULT_IPD))

(def get-hmd
  (memoize (fn []
             (. OvrLibrary/INSTANCE ovr_Initialize)
             (. Hmd createDebug com.oculusvr.capi.OvrLibrary$ovrHmdType/ovrHmd_DK1))))

(comment
(. OvrLibrary/INSTANCE ovr_Initialize)
(get-hmd)

 (.getDesc (get-hmd))

 (. (get-hmd) destroy))

(comment
 (.getFloat (get-hmd) OvrLibrary/OVR_KEY_IPD ipd))

; (.startSensor hmd 0 0)
; (.WindowsPos hmd-desc)
; (get (.EyeRenderOrder hmd-desc) 0)
; (get (.EyeRenderOrder hmd-desc) 1)
; (.beginEyeRender (get-hmd) 0)
; (.beginEyeRender (get-hmd) 1)

; (GL11/glViewport 0 0 10 10)

(defn target-rect [hmd-desc]
  (let [pixel-format (.withDepthBits (.withSamples (PixelFormat.) 4) 16)
        context-attributes (-> (ContextAttribs. 4 4)
                               (.withForwardCompatible true)
                               (.withProfileCore true)
                               (.withDebug true))
        target-rect (Rectangle. (.x (.WindowsPos hmd-desc))
                                (.y (.WindowsPos hmd-desc))
                                (.w (.Resolution hmd-desc))
                                (.h (.Resolution hmd-desc)))]
    target-rect))

(comment
 (target-rect hmd-desc)

 (. (OvrLibrary/INSTANCE) ovrMatrix4f_Projection)
 (. OvrLibrary INSTANCE ovrMatrix4f_Projection))

; TODO Debug
(comment
 (System/setProperty "org.lwjgl.util.Debug" "true"))
; TODO Debug

; (useContext nil)
(proxy [LwjglApp] []
  (setupDisplay
    ([]
     (System/setProperty "org.lwjgl.opengl.Window.undecorated", "true")
     (proxy-super
      setupDisplay
      (target-rect (.getDesc (get-hmd))))))
  (initGl
    ([]
     (let [hmd (get-hmd)
           hmd-desc (.getDesc hmd)
           fov-ports
           (for [eye [0 1]
                 :let [default-eye-fov (get (. hmd-desc DefaultEyeFov) eye)
                       fov-port
                       (doto (FovPort.)
                         (-> .DownTan (set! (.DownTan default-eye-fov)))
                         (-> .UpTan (set! (.UpTan default-eye-fov)))
                         (-> .LeftTan (set! (.LeftTan default-eye-fov)))
                         (-> .RightTan (set! (.RightTan default-eye-fov))))
                       projection (. (OvrLibrary/INSTANCE)
                                     ovrMatrix4f_Projection
                                     fov-port 0.1 10000 (byte 1))
                       eth (let [texture-size (.getFovTextureSize hmd eye fov-port 1.0)]
                             (doto (.Header (Texture.))
                               (-> .TextureSize (set! texture-size))
                               (-> .RenderViewport .Size (set! texture-size))
                               (-> .RenderViewport .Pos (set! (OvrVector2i. 0 0)))
                               (-> .API (set! OvrLibrary$ovrRenderAPIType/ovrRenderAPI_OpenGL))))
                       frame-buffer (FrameBuffer. (.w (.TextureSize eth))
                                                  (.h (.TextureSize eth)))
                       eye-texture-id (.id (.getTexture frame-buffer))
                       ]]
             ; eth
             ; frame-buffer
             eye-texture-id
             ; [projection fov-port]
             )]
       fov-ports))))
(.setupDisplay app)
