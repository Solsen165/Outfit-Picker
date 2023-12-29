package com.example.outfitpicker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.outfitpicker.databasefiles.Outfit
import com.example.outfitpicker.databasefiles.OutfitWithItems
import java.io.File

class OutfitAdapter(private val filesDir: File, private val listener: OnOutfitClickListener ): RecyclerView.Adapter<OutfitAdapter.ViewHolder>() {
    private var outfits: List<OutfitWithItems> = emptyList()
    init {
        setHasStableIds(true)
    }
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textViewName: TextView
        val gridLayout: GridLayout
        init {
            textViewName = view.findViewById(R.id.text_view_outfit_name)
            gridLayout = view.findViewById(R.id.items_grid)

            view.setOnClickListener {
                listener.onOutfitClick(outfits[adapterPosition])
            }
        }
    }

    fun setOutfits(newOutfits: List<OutfitWithItems>) {
        outfits = newOutfits
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_outfits,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return outfits.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewName.text = outfits[position].outfit.name
        var imageViewIndex = 0
        for (item in outfits[position].items) {
            if (imageViewIndex > 3) {
                break
            }
            val imageView = holder.gridLayout.getChildAt(imageViewIndex) as ImageView
            imageViewIndex += 1
            val imageFile = File(filesDir,"Item#${item.itemId}.png")
            imageView.setImageURI(null)
            imageView.setImageURI(imageFile.toUri())
        }
    }

    interface OnOutfitClickListener {
        fun onOutfitClick(outfit: OutfitWithItems)
    }
}