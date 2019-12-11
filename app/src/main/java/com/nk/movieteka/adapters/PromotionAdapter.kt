package com.nk.movieteka.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nk.movieteka.R
import com.nk.movieteka.models.MovieModel
import kotlinx.android.synthetic.main.movie_data_adapter.view.*

class PromotionAdapter(private val items: List<MovieModel>) : RecyclerView.Adapter<PromotionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.movie_data_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: MovieModel) {
            with(itemView) {
                Glide.with(context).load("https://image.tmdb.org/t/p/w500" + item.backdrop_path).placeholder(R.drawable.ic_movie).into(backdropPath)
                tvFullName.text = item.title
                tvEmail.text = item.overivew
                tvEmail.setSelected(true)
            }
        }
    }
}