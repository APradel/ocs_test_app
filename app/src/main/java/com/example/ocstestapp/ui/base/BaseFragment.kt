package com.example.ocstestapp.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment()
{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeAdapter()
        initializeObservers()
    }

    protected open fun initializeAdapter(){}
    protected open fun initializeObservers(){}
}