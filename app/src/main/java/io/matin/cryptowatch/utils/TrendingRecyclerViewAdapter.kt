package io.matin.cryptowatch.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.matin.cryptowatch.data.retrofit.TrendingCoin
import io.matin.cryptowatch.databinding.FragmentTrendingBinding

/**
 * [androidx.recyclerview.widget.RecyclerView.Adapter] that can display a [io.matin.cryptowatch.fragments.placeholder.PlaceholderContent.PlaceholderItem].
 */
class TrendingRecyclerViewAdapter(
    private var values: List<TrendingCoin>
) : RecyclerView.Adapter<TrendingRecyclerViewAdapter.ViewHolder>() {

    fun updateData(newList: List<TrendingCoin>) {
        values = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentTrendingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.tvName.text = item.name
        holder.tvPriceInr.text = item.price_btc.toString()
        Glide.with(holder.itemView.context)
            .load(item.thumb)
            .into(holder.imgView)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentTrendingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imgView: ImageView = binding.imgCoin
        val tvName: TextView = binding.tvName
        val tvPriceInr: TextView = binding.tvPriceInr
    }

}