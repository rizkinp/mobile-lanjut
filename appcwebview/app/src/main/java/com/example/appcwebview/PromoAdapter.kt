package com.example.appcwebview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class PromoAdapter(context: Context, resource: Int, objects: List<PromoModel>) :
    ArrayAdapter<PromoModel>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.list_item_promo, parent, false)

        val promoItem = getItem(position)

        // Assuming these are the IDs in your list_item_promo.xml
        val promoTextView: TextView = itemView.findViewById(R.id.promoTextView)
        val promoUntilTextView: TextView = itemView.findViewById(R.id.promoUntilTextView)

        // Check if promoItem is not null before accessing its properties
        promoItem?.let {
            promoTextView.text = it.promo
            promoUntilTextView.text = it.promoUntil
        }

        return itemView
    }
}
