package gw.albertsendros.datasource

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import model.FactoryDataAdapter
import model.Flood
import model.UKAdapter
import model.UKFlood
import java.net.URL

class DataSource(private val API_URL: String = "https://environment.data.gov.uk/flood-monitoring/id/floodAreas?_limit=100") {
    private val gson: Gson = Gson()

    private fun callAPI(): String {
        return URL(API_URL).readText()
    }
    fun getFloods(): List<Flood> {
        val KEY = "items"
        val jsonObject: JsonObject = gson.fromJson(callAPI(), JsonObject::class.java)
        val stringJSONItemKeys = listOf("county", "description","eaAreaName", "floodWatchArea", "fwdCode",
            "lat", "long", "riverOrSea")
        if (jsonObject.has(KEY)) {
            val items = jsonObject.getAsJsonArray(KEY)
           return items.map { it -> it as JsonObject
            }.map {
                val ukFloodJSON = UKAdapter().UKFloodJSON(county = it[stringJSONItemKeys[0]].asString,
                    description = it[stringJSONItemKeys[1]].asString, eaAreaName = it[stringJSONItemKeys[2]].asString,
                    fwdCode = it[stringJSONItemKeys[4]].asString,
                    lat = it[stringJSONItemKeys[5]].asDouble, lon = it[stringJSONItemKeys[6]].asDouble, riverOrSea = it[stringJSONItemKeys[7]].asString)
                return@map FactoryDataAdapter.ukAdapter.mapToFlood(ukFloodJSON)
            }
        }
        return emptyList()
    }
}