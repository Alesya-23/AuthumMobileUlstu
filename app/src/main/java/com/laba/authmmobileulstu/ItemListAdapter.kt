package com.laba.authmmobileulstu

import android.annotation.SuppressLint
import android.content.Context
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.BaseAdapter
import android.widget.TextView

class ItemListAdapter(val context: Context, val dataSource: ArrayList<ItemList>) : BaseAdapter() {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.list_item, parent, false)
        val textViewId = rowView.findViewById(R.id.text_view_id) as TextView
        val textViewName = rowView.findViewById(R.id.text_view_name) as TextView
        val textViewDate = rowView.findViewById(R.id.text_view_date) as TextView
        val item = getItem(position) as ItemList
        textViewId.text = item.id.toString()
        textViewName.text = item.nameDance
        textViewDate.text = item.isModernDance.toString()
        return rowView
    }

}