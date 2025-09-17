package groffse

import java.util.ArrayList
import java.util.Random

class Grid {
    private val panelGrid: ArrayList<ArrayList<Panel>> = ArrayList()
    private var width: Int = 0
    private var height: Int = 0
    private var numberOfBombs: Int = 0

    fun reset() {
        for (panelRow in panelGrid) {
            for (panelIndex in panelRow) {
                panelIndex.reset()
            }
        }
    }

    fun setHeight(height: Int) {
        this.height = height
    }

    fun setWidth(width: Int) {
        this.width = width
    }

    fun setNumberOfBombs(numberOfBombs: Int) {
        if (numberOfBombs > width * height) {
            throw IllegalArgumentException("Number of bombs has to be less than height * width")
        }
        this.numberOfBombs = numberOfBombs
    }

    fun getHeight(): Int = height

    fun getWidth(): Int = width

    fun getNumberOfBombs(): Int {
        return numberOfBombs
    }

    fun getFlaggedPanels(): Int {
        var flaggedPanels = 0
        for (panelRow in panelGrid) {
            for (panelIndex in panelRow) {
                if (panelIndex.isRevealed()) {
                    flaggedPanels++
                }
            }
        }
        return flaggedPanels
    }

    fun getRevealedPanels(): Int {
        var revealedPanels = 0
        for (panelRow in panelGrid) {
            for (panelIndex in panelRow) {
                if (panelIndex.isRevealed()) {
                    revealedPanels++
                }
            }
        }
        return revealedPanels
    }

    fun getPanelGrid(): ArrayList<ArrayList<Panel>> {
        return ArrayList(panelGrid)
    }

    fun floodFill(i: Int, j: Int, lastV: Int) {
        if ((j < width && j >= 0) && (i < height && i >= 0)) {
            if (panelGrid[i][j].getAdjacentBombs() == 0 && !panelGrid[i][j].isRevealed() && !panelGrid[i][j].isFlagged() && !panelGrid[i][j].isBomb()) {
                panelGrid[i][j].reveal()
                val v = panelGrid[i][j].getAdjacentBombs()
                floodFill(i, j + 1, v)
                floodFill(i, j - 1, v)

                floodFill(i - 1, j + 1, v)
                floodFill(i - 1, j - 1, v)
                floodFill(i - 1, j, v)

                floodFill(i + 1, j, v)
                floodFill(i + 1, j + 1, v)
                floodFill(i + 1, j - 1, v)
            } else if (panelGrid[i][j].getAdjacentBombs() != 0 && lastV == 0 && !panelGrid[i][j].isRevealed() && !panelGrid[i][j].isFlagged() && !panelGrid[i][j].isBomb()) {
                panelGrid[i][j].reveal()
                val v = panelGrid[i][j].getAdjacentBombs()
                floodFill(i, j + 1, v)
                floodFill(i, j - 1, v)

                floodFill(i - 1, j + 1, v)
                floodFill(i - 1, j - 1, v)
                floodFill(i - 1, j, v)

                floodFill(i + 1, j, v)
                floodFill(i + 1, j + 1, v)
                floodFill(i + 1, j - 1, v)
            }
        }
    }

    fun generateBoard(skipBomb: Int) {
        if (height != width || height < 1 || width < 1) {
            throw IllegalArgumentException("Width and height has to be equal and greater than zero!")
        }
        if (height != panelGrid.size || panelGrid.isEmpty() || width != panelGrid[0].size) {
            panelGrid.clear()

            var panelID = 0

            for (i in 0 until height) {
                panelGrid.add(ArrayList())
                for (j in 0 until width) {
                    panelGrid[i].add(Panel(panelID))
                    panelID++
                }
            }
        }
        generate_bomb_positions(skipBomb)
        setAdjacentBombsForPanels()
    }

    private fun generate_bomb_positions(skipBomb: Int) {
        val bombPositions = ArrayList<Int>()
        val rand = Random()
        for (k in 0 until numberOfBombs) {
            bombPositions.add(-50)
        }

        var i = 0
        while (i < numberOfBombs) {
            val n = rand.nextInt(height * width)

            if (n == skipBomb) {
                continue
            }
            if (bombPositions.indexOf(n) < 0) {
                bombPositions[i] = n
                i++
            }
        }

        for (row in 0 until height) {
            for (column in 0 until width) {
                for (bombIndex in 0 until numberOfBombs) {
                    if (panelGrid[row][column].getID() == bombPositions[bombIndex]) {
                        panelGrid[row][column].setBomb(true)
                        break
                    }
                }
            }
        }
    }

    private fun setAdjacentBombsForPanels() {
        for (i in 0 until height) {
            var bombsAround = 0
            for (j in 0 until width) {
                if (!panelGrid[i][j].isBomb()) {
                    if (i - 1 >= 0 && j - 1 >= 0) {
                        if (panelGrid[i - 1][j - 1].isBomb()) {
                            bombsAround++
                        }
                    }
                    if (i - 1 >= 0) {
                        if (panelGrid[i - 1][j].isBomb()) {
                            bombsAround++
                        }
                    }
                    if (i - 1 >= 0 && j + 1 < width) {
                        if (panelGrid[i - 1][j + 1].isBomb()) {
                            bombsAround++
                        }
                    }
                    if (j + 1 < width) {
                        if (panelGrid[i][j + 1].isBomb()) {
                            bombsAround++
                        }
                    }
                    if (i + 1 < height && j + 1 < width) {
                        if (panelGrid[i + 1][j + 1].isBomb()) {
                            bombsAround++
                        }
                    }
                    if (i + 1 < height) {
                        if (panelGrid[i + 1][j].isBomb()) {
                            bombsAround++
                        }
                    }
                    if (i + 1 < height && j - 1 >= 0) {
                        if (panelGrid[i + 1][j - 1].isBomb()) {
                            bombsAround++
                        }
                    }
                    if (j - 1 >= 0) {
                        if (panelGrid[i][j - 1].isBomb()) {
                            bombsAround++
                        }
                    }
                    panelGrid[i][j].setAdjacentBombs(bombsAround)
                    bombsAround = 0
                }
            }
        }
    }

    fun getFlagsAdjacentToPanel(i: Int, j: Int): Int {
        var flagsAround = 0

        if (i + 1 < height) {
            if (panelGrid[i + 1][j].isFlagged()) {
                flagsAround++
            }
        }
        if (i + 1 < height && j + 1 < width) {
            if (panelGrid[i + 1][j + 1].isFlagged()) {
                flagsAround++
            }
        }
        if (i + 1 < height && j - 1 >= 0) {
            if (panelGrid[i + 1][j - 1].isFlagged()) {
                flagsAround++
            }
        }
        if (j + 1 < width) {
            if (panelGrid[i][j + 1].isFlagged()) {
                flagsAround++
            }
        }
        if (j - 1 >= 0) {
            if (panelGrid[i][j - 1].isFlagged()) {
                flagsAround++
            }
        }
        if (j + 1 < width && i - 1 >= 0) {
            if (panelGrid[i - 1][j + 1].isFlagged()) {
                flagsAround++
            }
        }
        if (i - 1 >= 0) {
            if (panelGrid[i - 1][j].isFlagged()) {
                flagsAround++
            }
        }
        if (i - 1 >= 0 && j - 1 >= 0) {
            if (panelGrid[i - 1][j - 1].isFlagged()) {
                flagsAround++
            }
        }
        return flagsAround
    }

    fun printPanelGrid() {
        for (row in 0 until height) {
            print("|")
            for (column in 0 until width) {
                if (panelGrid[row][column].isBomb()) {
                    print("*|")
                } else {
                    print(panelGrid[row][column].getAdjacentBombs().toString() + "|")
                }
            }
            println("")
        }
    }
}
