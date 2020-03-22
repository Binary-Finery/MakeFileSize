package com.spencerstudios.makefilesize

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.io.File

class FileAdapter(context: Context, private val items: ArrayList<File>) : BaseAdapter() {

    private val mInflator: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        val vh: ListRowHolder
        if (convertView == null) {
            view = this.mInflator.inflate(R.layout.rv_item, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }

        val item = items[position]

        if (item.isDirectory) {
            vh.label.text = item.name.plus(" (${item.listFiles().size})")
            vh.ic.setImageResource(R.drawable.ic_folder)
        } else {
            vh.label.text = item.name
            vh.ic.setImageResource(R.drawable.ic_file)
        }

        return view
    }
}

private class ListRowHolder(row: View?) {
    val label: TextView = row?.findViewById(R.id.item_filename) as TextView
    val ic: ImageView = row?.findViewById(R.id.ic_file_type) as ImageView
}