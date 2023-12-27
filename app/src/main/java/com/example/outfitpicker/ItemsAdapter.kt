package com.example.outfitpicker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.outfitpicker.databasefiles.Item
import java.io.File

class ItemsAdapter(private val filesDir: File, private val listener: OnItemClickListener): RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {
    private var items: List<Item> = emptyList()

    init {
        setHasStableIds(true)
    }
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textViewName: TextView
        val imageViewItem: ImageView

        init {
            textViewName = view.findViewById(R.id.text_view_clothes_item)
            imageViewItem = view.findViewById(R.id.image_view_clothes_item)

            view.setOnClickListener{
                listener.onItemClick(items[adapterPosition])
            }
        }
    }

    fun setItems(newItems : List<Item>) {
        items = newItems
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_clothes,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemsAdapter.ViewHolder, position: Int) {
        holder.textViewName.text = items[position].name
        val id = items[position].id
        val file: File = File(filesDir,"Item#$id.png")
        holder.imageViewItem.setImageURI(file.toUri())

    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface OnItemClickListener {
        fun onItemClick(item: Item)
    }

}