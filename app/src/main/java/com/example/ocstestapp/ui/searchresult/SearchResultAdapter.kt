package com.example.ocstestapp.ui.searchresult

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.example.ocstestapp.ui.base.BaseListAdapter
import com.example.ocstestapp.BR
import com.example.ocstestapp.databinding.SearchResultItemBinding

class SearchResultAdapter(viewModel: SearchResultViewModel,
                          onItemClicked: (item: SearchResultViewItem)-> Unit) :
    BaseListAdapter<SearchResultViewModel, SearchResultViewItem>(viewModel, onItemClicked)
{
    override fun setDataBinding(viewDataBinding: ViewDataBinding, item: SearchResultViewItem)
    {
        viewDataBinding.setVariable(BR.search_result, item)
    }

    override fun getViewDataBinding(inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding
    {
        return SearchResultItemBinding.inflate(inflater, parent, false)
    }

}