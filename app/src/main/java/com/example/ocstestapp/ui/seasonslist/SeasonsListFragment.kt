package com.example.ocstestapp.ui.seasonslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ocstestapp.api.ocs.results.detail.OCSApiSeriesDetail
import com.example.ocstestapp.databinding.SeasonsListFragmentBinding

import com.example.ocstestapp.ui.base.BaseFragment
import kotlinx.android.synthetic.main.seasons_list_fragment.*

const val SERIES_DETAIL_KEY = "seriesDetail"

class SeasonsListFragment : BaseFragment() {

    companion object {
        fun newInstance(ocsApiSeriesDetail: OCSApiSeriesDetail) : SeasonsListFragment
        {
            val res = SeasonsListFragment()
            val args = Bundle()
            args.putSerializable(SERIES_DETAIL_KEY, ocsApiSeriesDetail)
            res.arguments = args
            return res
        }
    }

    private lateinit var adapter: SeasonsListAdapter
    private lateinit var viewDataBinding: SeasonsListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val ocsApiSeriesDetail = arguments?.getSerializable(SERIES_DETAIL_KEY) as OCSApiSeriesDetail
        viewDataBinding = SeasonsListFragmentBinding.inflate(inflater, container, false)
            .apply {
                viewModel = ViewModelProvider(this@SeasonsListFragment).get(SeasonsListViewModel::class.java)
                viewModel?.setSeriesDetail(ocsApiSeriesDetail)
                lifecycleOwner = viewLifecycleOwner
            }
        return viewDataBinding.root
    }


    override fun initializeAdapter()
    {
        val viewModel = viewDataBinding.viewModel
        if (viewModel != null) {
            adapter = SeasonsListAdapter(viewDataBinding.viewModel!!) { onSeasonClicked(it) }
            val layoutManager = LinearLayoutManager(activity)
            seasons_recycler_view.layoutManager = layoutManager
            seasons_recycler_view.adapter = adapter

        }
    }

    override fun initializeObservers()
    {
        viewDataBinding.viewModel?.elements?.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
        })
    }

    private fun onSeasonClicked(item: SeasonsListViewItem)
    {
        //TODO: Display season information
    }

}
