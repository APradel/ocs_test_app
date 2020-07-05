package com.example.ocstestapp.ui.searchresult

import android.app.ActivityOptions
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ocstestapp.databinding.SearchResultFragmentBinding
import com.example.ocstestapp.ui.base.BaseFragment
import com.example.ocstestapp.utils.showDebugMessage
import kotlinx.android.synthetic.main.search_result_fragment.*

const val SEARCH_QUERY_KEY = "searchQuery"
const val NUMBER_OF_COLS = 2

class SearchResultFragment : BaseFragment() {

    companion object {
        fun newInstance(query: String? = null) : SearchResultFragment
        {
            val fragment = SearchResultFragment()
            query?.let {
                //If a query was found, it's passed to the fragment
                val args:Bundle = Bundle()
                args.putString(SEARCH_QUERY_KEY, it)
                fragment.arguments = args
            }
            return fragment
        }
    }

    private lateinit var adapter: SearchResultAdapter
    private lateinit var viewDataBinding: SearchResultFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val query: String? = arguments?.getString(SEARCH_QUERY_KEY, null)
        viewDataBinding = SearchResultFragmentBinding.inflate(inflater, container, false)
            .apply {
                viewModel = ViewModelProvider(this@SearchResultFragment).get(SearchResultViewModel::class.java)
                if(query != null) viewModel?.onNewSearch(query)
                lifecycleOwner = viewLifecycleOwner
            }
        return viewDataBinding.root
    }


    override fun initializeAdapter()
    {
        val viewModel = viewDataBinding.viewModel
        if (viewModel != null) {
            adapter = SearchResultAdapter(viewDataBinding.viewModel!!, onItemClicked)
            val layoutManager = GridLayoutManager(activity, NUMBER_OF_COLS)
            results_recycler_view.layoutManager = layoutManager
            results_recycler_view.adapter = adapter

        }
    }

    override fun initializeObservers()
    {
        viewDataBinding.viewModel?.elements?.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
        })
    }

    private var onItemClicked: (item: SearchResultViewItem)-> Unit =
    {
        showDebugMessage(context, "item clicked")
    }
}