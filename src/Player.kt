data class Player(val name: String, val skill: Int) {
    override fun toString(): String {
        return "$name ($skill)"
    }
}