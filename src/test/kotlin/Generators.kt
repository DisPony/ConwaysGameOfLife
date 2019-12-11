import io.kotlintest.properties.Gen

class UniverseGen(private val center: Cell, private val aliveNeighbors: Int) : Gen<Universe> {

    override fun constants(): Iterable<Universe> = emptyList()

    override fun random(): Sequence<Universe> = generateSequence {
        val alive = (1..aliveNeighbors).map { Cell.Alive() }.toList()
        val dead = (1..(8 - aliveNeighbors)).map { Cell.Dead() }.toList()
        val all = alive + dead

        all.shuffled().toGrid(center)
    }

    private fun List<Cell>.toGrid(center: Cell): List<List<Cell>> =
        listOf(
            listOf(get(0), get(1), get(2)),
            listOf(get(3), center, get(4)),
            listOf(get(5), get(6), get(7))
        )
}

fun universeGenWith2or3AliveNeighbours(cell: Cell) = Gen.choose(2, 3).flatMap { UniverseGen(cell, it) }
fun universeGenFewerThan2AliveNeighbours(cell: Cell) = Gen.choose(0, 1).flatMap { UniverseGen(cell, it) }
fun universeGenMoreThan3AliveNeighbours(cell: Cell) = Gen.choose(4, 8).flatMap { UniverseGen(cell, it) }