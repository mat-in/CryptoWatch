package io.matin.cryptowatch.fragments.chart

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import dagger.hilt.android.AndroidEntryPoint
import io.matin.cryptowatch.R
import io.matin.cryptowatch.data.retrofit.TrendingCoin
import io.matin.cryptowatch.databinding.FragmentCandleChartBinding
import io.matin.cryptowatch.viewmodel.CoiniViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CandleChart : Fragment() {

    private var _binding: FragmentCandleChartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CoiniViewModel by viewModels()
    private val candleEntries = mutableListOf<CandleEntry>()

    companion object {
        private const val ARG_COIN_ID = "coin_id"
        private const val ARG_COIN_NAME = "coin_name"

        fun newInstance(coinId: String, coinName: String): CandleChart {
            val fragment = CandleChart()
            val args = Bundle()
            args.putString(ARG_COIN_ID, coinId)
            args.putString(ARG_COIN_NAME, coinName)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var coinId: String
    private lateinit var coinName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            coinId = it.getString(ARG_COIN_ID) ?: "bitcoin"
            coinName = it.getString(ARG_COIN_NAME) ?: "Bitcoin"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCandleChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupChart()

        lifecycleScope.launch {
            viewModel.getOHLCData(coinId, "inr", 365).collect { ohlcData ->
                candleEntries.clear()
                ohlcData.forEachIndexed { index, candle ->
                    val x = index.toFloat()
                    val open = candle[1].toFloat()
                    val high = candle[2].toFloat()
                    val low = candle[3].toFloat()
                    val close = candle[4].toFloat()
                    candleEntries.add(CandleEntry(x, high, low, open, close))
                }
                updateChart()
            }
        }
    }

    private fun setupChart() = with(binding.candleStickChart) {
        description.isEnabled = false
        setMaxVisibleValueCount(60)
        setPinchZoom(true)
        setDrawGridBackground(false)
        axisLeft.setDrawGridLines(false)
        axisRight.isEnabled = false
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
    }

    private fun updateChart() {
        val dataSet = CandleDataSet(candleEntries, coinName)
        dataSet.color = Color.rgb(80, 80, 80)
        dataSet.shadowColor = Color.DKGRAY
        dataSet.shadowWidth = 0.7f
        dataSet.decreasingColor = Color.RED
        dataSet.decreasingPaintStyle = Paint.Style.FILL
        dataSet.increasingColor = Color.GREEN
        dataSet.increasingPaintStyle = Paint.Style.FILL
        dataSet.neutralColor = Color.BLUE

        val data = CandleData(dataSet)
        binding.candleStickChart.data = data
        binding.candleStickChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
