package io.matin.cryptowatch.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.matin.cryptowatch.data.retrofit.SearchCoin
import io.matin.cryptowatch.databinding.FragmentSearchBinding
import io.matin.cryptowatch.R

/**
 * [androidx.recyclerview.widget.RecyclerView.Adapter] that can display a [PlaceholderItem].
 */
class SearchRecyclerViewAdapter(
    private val values: MutableList<SearchCoin> = mutableListOf()
) : RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }
    var onBookmarkClicked: ((SearchCoin, Boolean) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.tvCoinName.text = item.name
        holder.tvMarketCapRank.text = item.market_cap_rank ?: "-"
        Glide.with(holder.itemView.context)
            .load(item.thumb ?: "")
            .into(holder.imgCoinThumb)

        var isBookmarked = false

        val icon = if (isBookmarked) R.drawable.ic_favourite_filled_foreground
        else R.drawable.ic_favourite_border
        holder.imgBookmark.setImageResource(icon)

        holder.imgBookmark.setOnClickListener {
            isBookmarked = !isBookmarked
            val newIcon = if (isBookmarked)
                R.drawable.ic_favourite_filled_foreground
            else
                R.drawable.ic_favourite_border
            holder.imgBookmark.setImageResource(newIcon)

            onBookmarkClicked?.invoke(item, isBookmarked)
        }
    }


    fun updateList(newList: List<SearchCoin>) {
        values.clear()
        values.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentSearchBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val imgCoinThumb: ImageView = binding.imgCoinThumb
        val tvCoinName: TextView = binding.tvCoinName
        val tvMarketCapRank: TextView = binding.tvMarketCapRank
        val imgBookmark: ImageView = binding.imgBookmark
    }
}