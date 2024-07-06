

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return greet("native yes yes yes hurrayyy")
    }
}

expect fun greet(username: String): String