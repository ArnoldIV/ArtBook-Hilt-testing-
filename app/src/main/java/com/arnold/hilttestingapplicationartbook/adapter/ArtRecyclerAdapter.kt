package com.arnold.hilttestingapplicationartbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arnold.hilttestingapplicationartbook.R
import com.arnold.hilttestingapplicationartbook.databinding.ArtRowBinding
import com.arnold.hilttestingapplicationartbook.roomdb.ArtEntity
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class ArtRecyclerAdapter @Inject constructor(
    val glide: RequestManager
) : RecyclerView.Adapter<ArtRecyclerAdapter.ArtViewHolder>() {

    class ArtViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffUtil = object : DiffUtil.ItemCallback<ArtEntity>() {
        override fun areItemsTheSame(oldItem: ArtEntity, newItem: ArtEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ArtEntity, newItem: ArtEntity): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var arts: List<ArtEntity>
    get() = recyclerListDiffer.currentList
    set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.art_row,parent,false)
        return ArtViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.artRowImageView)
        val paintingNameText = holder.itemView.findViewById<TextView>(R.id.paintingName)
        val authorNameText = holder.itemView.findViewById<TextView>(R.id.authorName)
        val yearText = holder.itemView.findViewById<TextView>(R.id.year)
        val art = arts[position]
        holder.itemView.apply {
            paintingNameText.text = "Name: ${art.name}"
            authorNameText.text = "Author name: ${art.ArtistName}"
            yearText.text = "Year: ${art.year}"
            glide.load(art.imageUrl).into(imageView)
        }
    }

    override fun getItemCount(): Int {
       return arts.size
    }

}