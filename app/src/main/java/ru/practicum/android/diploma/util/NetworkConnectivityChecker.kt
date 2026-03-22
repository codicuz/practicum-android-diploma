package ru.practicum.android.diploma.util

import android.content.Context
import android.net.ConnectivityManager

class NetworkConnectivityChecker(private val context: Context) {

    fun isConnected(): Boolean {
        context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

//        val network = connectivityManager.activeNetwork ?: return false
//        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
//
//        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        return false
    }
}
