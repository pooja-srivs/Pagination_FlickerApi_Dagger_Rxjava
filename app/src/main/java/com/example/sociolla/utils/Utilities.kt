package com.example.sociolla.utils

import android.content.Context
import android.net.ConnectivityManager

object Utilities {
    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return info != null && info.isConnected
    }
}