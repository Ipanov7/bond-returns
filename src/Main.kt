//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

fun main() {
    val scraper = Scraper()
    var bestBond: Bond? = null

    val bondTypes = listOf("bonos tf 0", "bot", "bobl tf 0")
    for (type in bondTypes) {
        val codes = scraper.retrieveBonds(type)

        println("${codes.size} bonds found for type $type")

        for (code in codes) {
            try {
                val bond = scraper.parseBond(code)
                if (bestBond == null) {
                    bestBond = bond
                } else if (bond.annualizedRoi() > bestBond.annualizedRoi()) {
                    bestBond = bond
                }
            } catch (e: Exception) {
                println(e.message)
                println(code)
            }


        }
    }

    println("Best bond is:")
    bestBond!!.print()
}