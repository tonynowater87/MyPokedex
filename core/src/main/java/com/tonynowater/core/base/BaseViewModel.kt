package com.tonynowater.core.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.tonynowater.core.miscellaneous.Event

open class BaseViewModel(app: Application) : AndroidViewModel(app) {

    val loading = MutableLiveData<Boolean>()
    val navigationCommands = MutableLiveData<Event<NavigationCommand>>()

    open fun navigate(directions: NavDirections) {
        navigationCommands.postValue(Event(NavigationCommand.To(directions = directions)))
    }

    open fun back() {
        navigationCommands.postValue(Event(NavigationCommand.Back))
    }

    open fun backTo(destinationId: Int, inclusive: Boolean = false) {
        navigationCommands.postValue(
            Event(
                NavigationCommand.BackTo(
                    destinationId = destinationId,
                    inclusive = inclusive
                )
            )
        )
    }
}

sealed class NavigationCommand {
    data class To(val directions: NavDirections) : NavigationCommand()
    object Back : NavigationCommand()
    data class BackTo(val destinationId: Int, val inclusive: Boolean) : NavigationCommand()
}
