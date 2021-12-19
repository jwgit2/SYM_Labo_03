package com.example.Labo03.beacon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.Labo03.R


/**
 * Project      : SYM_Labo3
 * Author       : Tailhades laurent, Werkle Johann, Zeller Corentin
 * Date         : 19.12.21
 * Description  :  Adapter pour la reclycler view des beacons
 *
 * https://developer.android.com/guide/topics/ui/layout/recyclerview
 * https://openclassrooms.com/fr/courses/4568576-recuperez-et-affichez-des-donnees-distantes/4893781-implementez-votre-premiere-recyclerview
 */

class BeaconAdapter(private var beaconsToDisplay: List<BeaconData>): RecyclerView.Adapter<BeaconAdapter.BeaconRecyclerViewHolder>() {

    /**
     * view holder pour le beacon _> bind de l'item card view et des datas
     */
    inner class BeaconRecyclerViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var uuidTextView: TextView = view.findViewById(R.id.beacon_card_info_uuid)
        var majorTextView: TextView = view.findViewById(R.id.beacon_card_info_major)
        var minorTextView: TextView = view.findViewById(R.id.beacon_card_info_minor)
        var rssiTextView: TextView = view.findViewById(R.id.beacon_card_info_rssi)
        var ProgressBar : ProgressBar = view.findViewById(R.id.beacon_card_info_bar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeaconRecyclerViewHolder {
        return BeaconRecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.beacon_cardview, parent, false))
    }

    override fun onBindViewHolder(holder: BeaconRecyclerViewHolder, position: Int) {
        val beacon = beaconsToDisplay[position]
        holder.uuidTextView.text = beacon.uuid.toString()
        holder.majorTextView.text = beacon.major.toString()
        holder.minorTextView.text = beacon.minor.toString()
        holder.rssiTextView.text = beacon.rssi.toString()
        holder.ProgressBar.progress = (-beacon.rssi) + 25
    }

    override fun getItemCount() = beaconsToDisplay.size
}
