package com.example.ocstestapp.ui.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.ocstestapp.R

abstract class BaseActivity : AppCompatActivity()
{
    protected abstract val activityLayoutID: Int
    protected open val displayActionBar = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityLayoutID)
        initActionBar()
        handleIntent(intent)
        if (null == savedInstanceState) {
            launchMainFragment()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    protected open fun handleIntent(intent: Intent) {}

    fun initActionBar()
    {
        if(displayActionBar)
        {
            supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            supportActionBar?.setCustomView(R.layout.custom_toolbar)
        }
        else
        {
            supportActionBar?.hide()
        }
    }

    protected abstract fun launchMainFragment()
}