import java.time.LocalDate
import java.time.temporal.ChronoUnit

const val basePrice = 100f

data class Bond(
    val name: String,
    val isin: String,
    val dueDate: LocalDate,
    val price: Float
)


fun Bond.roi() = ((basePrice / price) - 1) * 100f

fun Bond.annualizedRoi(): Float {
    val today = LocalDate.now()
    val timePeriod = ChronoUnit.DAYS.between(today, dueDate) / 365f
    return roi() / timePeriod
}

fun Bond.annualizedNetRoi()= annualizedRoi() * 0.875f

fun Bond.print() {
    println("Name: $name")
    println("ISIN: $isin")
    println("Price: $price")
    println("Due date: $dueDate")
    println("Annualized ROI: ${"%.2f".format(annualizedRoi())}%")
    println("Annualized net ROI: ${"%.2f".format(annualizedNetRoi())}%")
}