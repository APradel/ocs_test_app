package com.example.ocstestapp.ui.seasonslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.example.ocstestapp.BR
import com.example.ocstestapp.databinding.SeasonsListItemBinding
import com.example.ocstestapp.ui.base.BaseListAdapter


class SeasonsListAdapter(viewModel: SeasonsListViewModel,
                         onItemClicked: (item: SeasonsListViewItem)-> Unit) :
    BaseListAdapter<SeasonsListViewModel, SeasonsListViewItem>(viewModel, onItemClicked)
{
    override fun setDataBinding(viewDataBinding: ViewDataBinding, item: SeasonsListViewItem)
    {
        viewDataBinding.setVariable(BR.season_info, item)
    }

    override fun getViewDataBinding(inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding
    {
        return SeasonsListItemBinding.inflate(inflater, parent, false)
    }
}