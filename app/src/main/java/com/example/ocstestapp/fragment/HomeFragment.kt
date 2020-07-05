package com.example.ocstestapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import com.example.ocstestapp.R

class HomeFragment: Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: MainActivity2ViewModel
    private var adapter: GridElementAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_activity2_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainActivity2ViewModel::class.java)
        // TODO: Use the ViewModel

        if(view != null)
        {
            val gridView: GridView = view?.findViewById<GridView>(R.id.id_grid_view) ?:
            throw NullPointerException("No grid view founded")
            adapter = context?.let { GridElementAdapter(it, 0, viewModel.elements) }
            gridView.adapter = adapter
        }

    }

}