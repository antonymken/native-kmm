import com.muiltplatform.project.freeMemoryNative
import com.muiltplatform.project.greetUser
import kotlinx.cinterop.ByteVarOf
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import platform.posix.strdup

@OptIn(ExperimentalForeignApi::class)
actual fun greet(username: String): String {
    memScoped {
        val greetingPtr = greetUser(username)
        val greeting = greetingPtr?.toKString() ?: "Hello"
        freeMemoryNative(greetingPtr)
        return greeting
    }
}
