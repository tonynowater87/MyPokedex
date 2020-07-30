package com.tonynowater.core.repository.network.monitor

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.provider.Settings
import timber.log.Timber

class SimpleNetworkStateMonitor(
    private val context: Context,
    private val manager: ConnectivityManager
) : NetworkStateMonitor {

    companion object {

        var connected: Boolean = false
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onLost(network: Network) {
            Timber.d("[DEBUG] network disconnected.")
            connected = false
        }

        override fun onAvailable(network: Network) {
            Timber.d("[DEBUG] network connected.")
            connected = true
        }
    }

    override fun setup() {

        Timber.d("[DEBUG] network state monitor init.")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            manager.registerDefaultNetworkCallback(networkCallback)

        } else {

            // prevent crash in android 6.0
            // see https://stackoverflow.com/questions/32185628/connectivitymanager-requestnetwork-in-android-6-0
            if (!Settings.System.canWrite(context)) {
                return
            }

            manager.requestNetwork(

                NetworkRequest
                    .Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .build(),

                networkCallback
            )
        }
    }

    override fun hasNetwork(): Boolean = connected
}