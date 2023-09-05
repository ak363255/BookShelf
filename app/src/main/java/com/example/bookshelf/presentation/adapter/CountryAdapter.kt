package com.example.bookshelf.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.bookscoremodule.domain.Country


class CountryAdapter(private val context: Context,private val countries:List<String>):ArrayAdapter<String>(context, 0,countries) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItem = convertView
        if (listItem == null) listItem =
            LayoutInflater.from(context).inflate(com.example.bookshelf.R.layout.country_item, parent, false)
        val country: String = countries.get(position)
        val countryTv = listItem!!.findViewById<TextView>(com.example.bookshelf.R.id.country)
        countryTv.text = country
        return listItem
    }
}