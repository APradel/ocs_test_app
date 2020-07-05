package com.example.ocstestapp.utils

import android.content.Context
import android.widget.Toast

fun showDebugMessage(context: Context?, msg: String, longDuration: Boolean = false)
{
    if(DEBUGGING && null != context)
    {
        val duration = if(longDuration) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        Toast.makeText(context, msg, duration).show()
    }
}