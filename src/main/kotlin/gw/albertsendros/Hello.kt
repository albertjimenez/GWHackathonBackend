package gw.albertsendros

import gw.albertsendros.algorithms.DistanceCalc
import gw.albertsendros.datasource.DataSource
import spark.kotlin.get
import spark.kotlin.halt

fun main() {
    val queryParamLatitude = "lat"
    val queryParamLongitude = "lon"
    val dataSource = DataSource()
    val distanceCalc = DistanceCalc()

    get("/floodAreaByPosition") {
        if (request.queryParams().contains(queryParamLatitude) && request.queryParams().contains(queryParamLongitude)) {
            val lat = request.queryParams(queryParamLatitude).toDouble()
            val lon = request.queryParams(queryParamLongitude).toDouble()
            val floodList = dataSource.getFloods()
            return@get distanceCalc.getClosestFloodAreas(floodList, lat, lon)
        }
        halt()
    }
}