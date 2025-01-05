

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return greet("$platform native yes yes yes hurrayyy")
    }
}

expect fun greet(username: String): String