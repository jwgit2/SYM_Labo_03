package com.example.Labo03.beacon

import org.altbeacon.beacon.Identifier

/**
 * Project      : SYM_Labo3
 * Author       : Tailhades laurent, Werkle Johann, Zeller Corentin
 * Date         : 19.12.21
 * Description  : data model des beacons captés
 */

data class BeaconData(
    val uuid: Identifier,
    val major: Identifier,
    val minor: Identifier,
    val rssi: Int
)
