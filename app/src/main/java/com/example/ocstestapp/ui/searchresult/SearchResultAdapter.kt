package com.example.ocstestapp.ui.searchresult

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.ocstestapp.BR
import com.example.ocstestapp.databinding.SearchResultItemBinding


class SearchResultAdapter(var viewModel: SearchResultViewModel,
                          var onItemClicked: (item: SearchResultViewItem)-> Unit) :
    RecyclerView.Adapter<SearchResultAdapter.ViewHolder>()
{
    private var elementList: List<SearchResultViewItem> = listOf()

    class ViewHolder(private val dataBinding: ViewDataBinding)
        : RecyclerView.ViewHolder(dataBinding.root)
    {
        fun setup(item: SearchResultViewItem, onClicked: (item: SearchResultViewItem)-> Unit)
        {
            dataBinding.setVariable(BR.search_result, item)
            itemView.setOnClickListener { onClicked(item) }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = SearchResultItemBinding.inflate(inflater, parent, false)
        return ViewHolder(dataBinding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setup(elementList[position], onItemClicked)
    }

    override fun getItemCount() = elementList.size

    fun updateList(elementList: List<SearchResultViewItem>)
    {
        this.elementList = elementList
        notifyDataSetChanged()
    }

}