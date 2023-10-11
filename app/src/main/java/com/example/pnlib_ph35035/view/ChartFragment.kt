package com.example.pnlib_ph35035.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pnlib_ph35035.DataBase.PNLibDataBase
import com.example.pnlib_ph35035.databinding.FragmentChartBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.NumberFormat

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


class ChartFragment : Fragment() {

    private lateinit var chartBinding: FragmentChartBinding
    private lateinit var xList: MutableList<String>
    private lateinit var entries: MutableList<BarEntry>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        chartBinding = FragmentChartBinding.inflate(inflater,container,false)
        initView()
        return chartBinding.root
    }

    private fun initView() {
        addXaxit()
        chartBinding.barChart.axisRight.setDrawLabels(false)

        entries = mutableListOf<BarEntry>()
        addEntries()

        var yAxis = YAxis()

        yAxis.mAxisMinimum = 0f

        var barDataset = BarDataSet(entries,"Doanh thu")

        var barData = BarData(barDataset)

        chartBinding.barChart.data = barData
        chartBinding.barChart.description.isEnabled = false
        chartBinding.barChart.invalidate()
        chartBinding.barChart.xAxis.valueFormatter = IndexAxisValueFormatter(xList)
        chartBinding.barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chartBinding.barChart.xAxis.isGranularityEnabled = false
        chartBinding.sumBooks.text = PNLibDataBase.getInstance(requireContext()).PNLibDao().sumBooks().toString() + " quyển"
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
        val price30Day = numberFormat.format(PNLibDataBase.getInstance(requireContext()).PNLibDao().sumPrice30Day())
        chartBinding.turnover30day.text = price30Day
        val date = LocalDate.now()
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val now = date.format(dateTimeFormatter)
        val priceToDay = numberFormat.format(PNLibDataBase.getInstance(requireContext()).PNLibDao()
            .sumPriceToDay(now,date.plusDays(1).format(dateTimeFormatter)))
        chartBinding.turnoverToDay.text = priceToDay


        chartBinding.sumStatusDt.text = PNLibDataBase.getInstance(requireContext())
            .PNLibDao().sumStatus("đã trả")
            .toString() + " quyển"

        chartBinding.sumStatusCt.text = PNLibDataBase.getInstance(requireContext())
            .PNLibDao().sumStatus("chưa trả")
            .toString() + " quyển"

        chartBinding.sumBookInventory.text = (PNLibDataBase.getInstance(requireContext())
            .PNLibDao().quantityBook() - PNLibDataBase.getInstance(requireContext()).PNLibDao().sumBooks()).toString() + " quyển"
    }

    private fun addEntries() {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        var startDate = LocalDate.now()
        val price1 = PNLibDataBase.getInstance(requireContext()).PNLibDao()
            .sumPriceToDay(startDate.minusDays(6).format(formatter)
                ,startDate.minusDays(5).format(formatter))

        val price2 = PNLibDataBase.getInstance(requireContext()).PNLibDao()
            .sumPriceToDay(startDate.minusDays(5).format(formatter)
                ,startDate.minusDays(4).format(formatter))

        val price3 = PNLibDataBase.getInstance(requireContext()).PNLibDao()
            .sumPriceToDay(startDate.minusDays(4).format(formatter)
                ,startDate.minusDays(3).format(formatter))

        val price4 = PNLibDataBase.getInstance(requireContext()).PNLibDao()
            .sumPriceToDay(startDate.minusDays(3).format(formatter)
                ,startDate.minusDays(2).format(formatter))

        val price5 = PNLibDataBase.getInstance(requireContext()).PNLibDao()
            .sumPriceToDay(startDate.minusDays(2).format(formatter)
                ,startDate.minusDays(1).format(formatter))

        val price6 = PNLibDataBase.getInstance(requireContext()).PNLibDao()
            .sumPriceToDay(startDate.minusDays(1).format(formatter)
                ,startDate.format(formatter))

        val price7 = PNLibDataBase.getInstance(requireContext()).PNLibDao()
            .sumPriceToDay(startDate.format(formatter)
                ,startDate.plusDays(1).format(formatter))

        entries.add(BarEntry(0f,price1.toFloat()))
        entries.add(BarEntry(1f,price2.toFloat()))
        entries.add(BarEntry(2f,price3.toFloat()))
        entries.add(BarEntry(3f,price4.toFloat()))
        entries.add(BarEntry(4f,price5.toFloat()))
        entries.add(BarEntry(5f,price6.toFloat()))
        entries.add(BarEntry(6f,price7.toFloat()))

    }

    private fun addXaxit() {
        xList = mutableListOf()
        var startDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM")
        for(i in 6 downTo  1){
            var  pastDate = startDate.minusDays(i.toLong())
            xList.add(pastDate.format(formatter))
        }
        xList.add(startDate.format(formatter))
    }
}