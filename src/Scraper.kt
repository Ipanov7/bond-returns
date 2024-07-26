import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.time.LocalDate
import java.time.format.DateTimeFormatter

const val bondUrl = "https://www.borsaitaliana.it/borsa/obbligazioni/mot/euro-obbligazioni/scheda/"
const val resultsUrl = "https://www.borsaitaliana.it/borsa/searchengine/search.html?lang=en&q="
const val dateFormat = "yy/MM/dd"

class Scraper {

    fun retrieveBonds(type: String): List<String> {
        val codes = ArrayList<String>()
        val doc = Jsoup.connect("$resultsUrl$type").get()
        val elements = doc.select("table.m-table > tbody > tr > td:eq(0) > a")
        for (el in elements) {
            val code = el.attribute("href").value.split("=")[1].split("&")[0]
            codes.add(code)
        }
        return  codes
    }


    fun parseBond(isin: String): Bond {
        val doc = Jsoup.connect("$bondUrl$isin.html?lang=en").get()

        val name = findTableField(doc, "Denomination")
        val dueDate = LocalDate.parse(
            findTableField(doc, "Expiry Date"),
            DateTimeFormatter.ofPattern(dateFormat)
        )
        val price = findTableField(doc, "Reference price").toFloat()

        return Bond(name, isin, dueDate, price)
    }

    fun findTableField(doc: Document, name: String): String = doc.select("td:matches(^$name$) + td").text()

}