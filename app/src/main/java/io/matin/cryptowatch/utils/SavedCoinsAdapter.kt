package io.matin.cryptowatch.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.matin.cryptowatch.data.retrofit.SearchCoin
import io.matin.cryptowatch.databinding.SavedCoinItemBinding
import io.matin.cryptowatch.R

class SavedCoinsAdapter(private var savedCoins: List<SearchCoin>) :
    RecyclerView.Adapter<SavedCoinsAdapter.SavedCoinViewHolder>() {

    inner class SavedCoinViewHolder(binding: SavedCoinItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val imgCoinThumb: ImageView = binding.imgCoinThumb
        val tvCoinName: TextView = binding.tvCoinName
        val tvMarketCapRank: TextView = binding.tvMarketCapRank
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedCoinViewHolder {

        return SavedCoinViewHolder(
            SavedCoinItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        Log.v("SavedAdapter  ","onCreate")
    }

    override fun onBindViewHolder(holder: SavedCoinViewHolder, position: Int) {
        val coin = savedCoins[position]
        holder.tvCoinName.text = coin.name
        holder.tvMarketCapRank.text = "Rank: ${coin.market_cap_rank ?: "N/A"}"
        Glide.with(holder.itemView.context)
            .load(coin.large)
            .placeholder(R.drawable.ic_favourite_filled_foreground)
            .error(R.drawable.ic_favourite_filled_foreground)
            .into(holder.imgCoinThumb)

        Log.v("OnBind","SetOnBind")
    }

    override fun getItemCount(): Int = savedCoins.size

    fun updateList(newList: List<SearchCoin>) {
        savedCoins = newList
        notifyDataSetChanged()
    }
}
