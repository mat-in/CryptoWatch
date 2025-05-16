package io.matin.cryptowatch.fragments.saved

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import io.matin.cryptowatch.R
import io.matin.cryptowatch.repo.CoinRepository
import io.matin.cryptowatch.repo.SearchRepository
import io.matin.cryptowatch.utils.SavedCoinsAdapter
import io.matin.cryptowatch.viewmodel.CoiniViewModel
import javax.inject.Inject

/**
 * A simple [androidx.fragment.app.Fragment] subclass.
 * Use the [SavedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class SavedFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var savedCoinsAdapter: SavedCoinsAdapter

    private val coiniViewModel: CoiniViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_saved, container, false)

        recyclerView = view.findViewById(R.id.rvSavedCoins)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        savedCoinsAdapter = SavedCoinsAdapter(savedCoins = emptyList())
        recyclerView.adapter = savedCoinsAdapter

        observeSavedCoins()
        return view
    }

    private fun observeSavedCoins() {
        coiniViewModel.getAll.observe(viewLifecycleOwner) { savedCoins ->
            Log.d("SavedFragment", "Observed saved coins: ${savedCoins.size}")
            savedCoinsAdapter.updateList(savedCoins)
        }
    }
}
