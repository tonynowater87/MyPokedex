package com.tonynowater.core.base

import android.app.Application
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.tonynowater.core.miscellaneous.Event
import io.reactivex.CompletableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

open class BaseViewModel(app: Application) : AndroidViewModel(app) {

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val loading = MutableLiveData<Boolean>()
    val navigationCommands = MutableLiveData<Event<NavigationCommand>>()

    override fun onCleared() {

        super.onCleared()
        compositeDisposable.clear()
    }

    protected fun <T> applySchedulers(): ObservableTransformer<T, T> {

        return ObservableTransformer { upper ->
            upper
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.from(Looper.getMainLooper()))
        }
    }

    protected fun applyCompletableSchedulers(): CompletableTransformer {

        return CompletableTransformer { upper ->

            upper
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.from(Looper.getMainLooper(), true))
        }
    }

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
