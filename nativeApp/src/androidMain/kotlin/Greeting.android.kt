import com.muiltplatform.project.NativeLoader.greetUserNative


actual fun greet(username: String): String {
    return greetUserNative(username)
}