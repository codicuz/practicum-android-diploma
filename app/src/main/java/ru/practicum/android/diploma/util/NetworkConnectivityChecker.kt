package ru.practicum.android.diploma.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkConnectivityChecker(private val context: Context) {

    fun isConnected(): Boolean = with(context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) {
        activeNetwork?.let { getNetworkCapabilities(it) }?.run {
            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    }
}
