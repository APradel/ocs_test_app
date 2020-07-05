package com.example.ocstestapp.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

/**
 *  Template of a generic adapter intended to be use for the MVVM pattern
 */
abstract class BaseListAdapter<VM: ViewModel, VIT: Any>
    (var viewModel: VM,
     var onItemClicked: (item: VIT)-> Unit) :
    RecyclerView.Adapter<BaseListAdapter.ViewHolder<VM, VIT>>()
{
    /**
     *  This function is called each times a View holder is setup,
     *  an abstract function is used and not a variable to find the BR field,
     *  because no solution were founded to put a BR field in a parameter
     *  Example: { viewDataBinding.setVariable(BR.search_result, item) }
     */
    abstract fun setDataBinding(viewDataBinding: ViewDataBinding, item: VIT)

    /**
     *  This function return an inflate view data inflate
     *  Parameters:
     *      -   inflater: LayoutInflater
     *      -   parent: ViewGroup
     *  Return:
     *      ViewDataBinding
     *  Example: { return SearchResultItemBinding.inflate(inflater, parent, false) }
     */
    abstract fun getViewDataBinding(inflater: LayoutInflater, parent: ViewGroup) : ViewDataBinding

    private var elementList: List<VIT> = listOf()

    class ViewHolder<VM: ViewModel, VIT: Any>(private val dataBinding: ViewDataBinding)
        : RecyclerView.ViewHolder(dataBinding.root)
    {
        fun setup(item: VIT,
                  onNewDataBinding: (viewDataBinding: ViewDataBinding, item: VIT)-> Unit?,
                  onClicked: (item: VIT)-> Unit)
        {
            onNewDataBinding(dataBinding, item)
            itemView.setOnClickListener { onClicked(item) }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder<VM, VIT>
    {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = getViewDataBinding(inflater, parent)
        return ViewHolder(dataBinding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder<VM, VIT>, position: Int) {
        holder.setup(elementList[position], this::setDataBinding, onItemClicked)
    }

    override fun getItemCount() = elementList.size

    fun updateList(elementList: List<VIT>)
    {
        this.elementList = elementList
        notifyDataSetChanged()
    }

}