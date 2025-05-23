package io.matin.cryptowatch.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import io.matin.cryptowatch.R
import io.matin.cryptowatch.repo.CoinRepository
import io.matin.cryptowatch.utils.SearchRecyclerViewAdapter
import io.matin.cryptowatch.viewmodel.CoiniViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchRecyclerViewAdapter: SearchRecyclerViewAdapter

    private val coiniViewModel: CoiniViewModel by viewModels()

    @Inject
    lateinit var coinRepository: CoinRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_list, container, false)

        recyclerView = view.findViewById(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        searchRecyclerViewAdapter = SearchRecyclerViewAdapter(mutableListOf())
        recyclerView.adapter = searchRecyclerViewAdapter

        val searchView = view.findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    lifecycleScope.async {
                        coiniViewModel.getSearch(query.toString()).collect {searchResponse ->
                            val results = searchResponse.coins.map { it }
                            searchRecyclerViewAdapter.updateList(results)
                        }
                    }
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        searchRecyclerViewAdapter.onBookmarkClicked = { coin, isBookmarked ->
            if (isBookmarked) {
                coiniViewModel.insert(coin)
            } else {
                coiniViewModel.delete(coin)
            }
        }
        return view
    }
}