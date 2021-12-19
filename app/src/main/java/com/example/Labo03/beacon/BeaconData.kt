package com.example.Labo03.beacon

import org.altbeacon.beacon.Identifier


/**
 * data model des beacons captés
 */
data class BeaconData(
    val uuid: Identifier,
    val major: Identifier,
    val minor: Identifier,
    val rssi: Int
)