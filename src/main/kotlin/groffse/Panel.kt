package groffse

class Panel(private val id: Int) {
    private var bomb = false
    private var adjacentBombs = 0
    private var revealed = false
    private var flagged = false

    fun setBomb(value: Boolean) {
        bomb = value
    }

    fun isBomb(): Boolean = bomb

    fun reveal() {
        if (flagged) {
            throw IllegalStateException("Panel is flagged and cannot be revealed!")
        }
        revealed = true
    }

    fun setFlag() {
        if (revealed) {
            throw IllegalStateException("Panel is already revealed. A flag cannot be set!")
        }
        flagged = true
    }

    fun getAdjacentBombs(): Int = adjacentBombs

    fun setAdjacentBombs(adjacentBombs: Int) {
        this.adjacentBombs = adjacentBombs
    }

    fun isRevealed(): Boolean = revealed

    fun isFlagged(): Boolean = flagged

    fun getID(): Int = id

    fun reset() {
        bomb = false
        adjacentBombs = 0
        revealed = false
        flagged = false
    }
}
