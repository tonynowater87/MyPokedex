package com.tonynowater.core.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.tonynowater.core.dialog.DialogManager
import com.tonynowater.core.repository.network.Resource
import com.tonynowater.core.repository.network.exception.ErrorState
import org.koin.android.ext.android.inject

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    protected val dialogManager by inject<DialogManager>()

    // create the data binding class
    protected inline fun <reified T : ViewDataBinding> binding(
        @LayoutRes resId: Int
    ): Lazy<T> = lazy {
        DataBindingUtil.setContentView<T>(this, resId).apply {
            lifecycleOwner = this@BaseActivity
        }
    }

    fun <U> resolve(callback: (data: U) -> Unit): Observer<Resource<U>> {

        return Observer {

            it?.apply {

                when (status) {
                    Resource.Status.ERROR -> extractErrorState()?.apply { onError(errorState!!) }
                    Resource.Status.SUCCESS -> extract()?.apply { callback(this) }
                }
            }
        }
    }

    /**
     * view is created inside afterViews(), it is safe to call mBindings.view
     */
    open fun afterViews(savedInstanceState: Bundle? = null) {
        observe()
    }

    /**
     * do all the LiveData observe and listener in this function
     */
    open fun observe() {
        // observe something...
    }

    /**
     * call this function whenever the LiveData resources is in ERROR state
     */
    open fun onError(errorState: ErrorState) {
        // do something when error occur
    }
}