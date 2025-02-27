package com.example.tecmobileproject.main.movie

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.tecmobileproject.R
import com.example.tecmobileproject.databinding.MovieItemBinding
import com.example.tecmobileproject.dtos.DtoInputMovie

class MovieRecyclerViewAdapter(
    private val values: List<DtoInputMovie>
) : RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(movie: DtoInputMovie)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(movie: DtoInputMovie)
    }

    private var listener: OnItemClickListener? = null
    private var longClickListener: OnItemLongClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
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

        val defaultBitmap: Bitmap = BitmapFactory.decodeResource(holder.itemView.context.resources, R.drawable.logo)
        val resizedBitmap: Bitmap = Bitmap.createScaledBitmap(defaultBitmap, 200, 200, false)

        val decodedBitmap: Bitmap? = if(movieItem.imageMovie.isNullOrEmpty()) {
            null
        } else {
            val decodedBytes: ByteArray = Base64.decode(movieItem.imageMovie, Base64.DEFAULT)
            if(decodedBytes.isNotEmpty()) {
                BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            } else {
                null
            }
        }

        holder.imageView.setImageBitmap(decodedBitmap ?: resizedBitmap)



        //Regular click
        holder.itemView.setOnClickListener {
            listener?.onItemClick(movieItem)
        }

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


            //Regular click
            binding.root.setOnClickListener {
                listener?.onItemClick(values[adapterPosition])
            }
        }
    }
}
