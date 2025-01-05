import com.muiltplatform.project.greetUser
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString

@OptIn(ExperimentalForeignApi::class)
actual fun greet(username: String): String {
    memScoped {
        val greetingPtr = greetUser(username)
        val greeting = greetingPtr?.toKString() ?: "Hello"
        return greeting
    }
}
