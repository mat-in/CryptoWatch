package io.matin.cryptowatch.fragments.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import io.matin.cryptowatch.R
import io.matin.cryptowatch.repo.CoinRepository
import io.matin.cryptowatch.utils.TrendingRecyclerViewAdapter
import io.matin.cryptowatch.viewmodel.CoiniViewModel
import javax.inject.Inject

@AndroidEntryPoint
class TrendingFragment : Fragment() {

    lateinit var trendingRecyclerViewAdapter: TrendingRecyclerViewAdapter
    lateinit var recyclerView: RecyclerView

    private val coinViewModel: CoiniViewModel by viewModels()

    @Inject
    lateinit var coinRepository: CoinRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_trending_list, container, false)

        recyclerView = view.findViewById(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )

        trendingRecyclerViewAdapter = TrendingRecyclerViewAdapter(emptyList())
        recyclerView.adapter = trendingRecyclerViewAdapter

        observeTrending()
        coinViewModel.getTrending()

        return view
    }


    private fun observeTrending() {
        coinViewModel.trendingLiveData.observe(viewLifecycleOwner) { response ->
            val coinList = response?.coins?.map { it.item } ?: emptyList()
            trendingRecyclerViewAdapter.updateData(coinList)
        }
    }

}