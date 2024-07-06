package com.muiltplatform.project

object NativeLoader {
    init {
        System.loadLibrary("nativeApp") // Replace with the actual name of your native library without the "lib" prefix and file extension
    }
    @JvmStatic
    external fun greetUserNative(username: String): String
}