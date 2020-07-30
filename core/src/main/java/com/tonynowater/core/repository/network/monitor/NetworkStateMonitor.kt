package com.tonynowater.core.repository.network.monitor

interface NetworkStateMonitor {

    fun setup()

    fun hasNetwork(): Boolean
}