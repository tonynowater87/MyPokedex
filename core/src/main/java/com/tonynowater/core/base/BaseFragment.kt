package com.tonynowater.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.transition.TransitionInflater
import com.tonynowater.core.dialog.DialogManager
import com.tonynowater.core.repository.network.Resource
import org.koin.android.ext.android.inject

abstract class BaseFragment<T : ViewDataBinding>(
    private val layout: Int,
    private val handleOnBackPressed: Boolean = false
) : Fragment() {

    protected val dialogManager by inject<DialogManager>()

    // the data binding view class
    var bindings: T? = null

    // is the fragment displaying on UI
    var isViewCreated = false

    private val parent by lazy { activity as BaseActivity<*> }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // create the data binding class
        bindings = DataBindingUtil.inflate(inflater, layout, container, false)
        // add this class to the data binding class as the lifecycle owner
        // (i.e. data binding class has the same life cycle as this fragment)
        bindings!!.lifecycleOwner = viewLifecycleOwner
        // the view is a visible UI
        isViewCreated = true

        return bindings!!.root
    }

    override fun onDestroyView() {

        super.onDestroyView()
        // prevent memory leak
        bindings = null
        // the view is hide
        isViewCreated = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, handleOnBackPressed) {
            onBackPressed()
        }
    }

    protected open fun onBackPressed() {

    }

    fun <U> resolve(callback: (data: U) -> Unit): Observer<Resource<U>> = parent.resolve(callback)
}