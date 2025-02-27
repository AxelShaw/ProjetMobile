package com.example.tecmobileproject.main.favorie

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.example.tecmobileproject.databinding.MovieItemBinding
import com.example.tecmobileproject.dtos.DtoInputMovie
import com.example.tecmobileproject.main.movie.MovieRecyclerViewAdapter


class FavorieRecyclerViewAdapter(
    private val values: List<DtoInputMovie>

) : RecyclerView.Adapter<FavorieRecyclerViewAdapter.ViewHolder>() {

    private var longClickListener: MovieRecyclerViewAdapter.OnItemLongClickListener? = null


    fun setOnItemLongClickListener(listener: MovieRecyclerViewAdapter.OnItemLongClickListener) {
        this.longClickListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movieItem = values[position]
        holder.titleView.text = movieItem.nameMovie

        val decodedBytes: ByteArray = Base64.decode(movieItem.imageMovie, Base64.DEFAULT)
        val decodedBitmap: Bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        holder.imageView.setImageBitmap(decodedBitmap)

        //Long click
        holder.itemView.setOnLongClickListener {
            longClickListener?.onItemLongClick(movieItem)
            true
        }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val titleView: TextView = binding.tvMovieItemNameMovie
        val imageView: ImageView = binding.ivMovieItem
        init {
            //Long click
            binding.root.setOnLongClickListener {
                longClickListener?.onItemLongClick(values[adapterPosition])
                true
            }

        }
    }
}