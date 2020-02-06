package gw.albertsendros.algorithms

import model.Flood
import util.Haversian
import kotlin.math.abs

class DistanceCalc(private val maxRadiusKm: Int = 2) {

    fun getClosestFloodAreas(floodList: List<Flood>, sourceLatitude: Double, sourceLongitude: Double): List<Flood> {
        return floodList.filter { it ->
            abs(Haversian.distance(it.latitude, it.longitude, sourceLatitude, sourceLongitude)) <= maxRadiusKm
        }.sortedBy {
            abs(Haversian.distance(it.latitude, it.longitude, sourceLatitude, sourceLongitude))
        }
    }
}