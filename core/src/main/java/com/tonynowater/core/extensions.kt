@file:Suppress("UNCHECKED_CAST")

package com.tonynowater.core

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

// change system status bar's color
fun AppCompatActivity.changeStatusBarColor(color: Int = getColor(R.color.colorPrimary)) {
    window.apply {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = color
    }
}

fun FragmentActivity.hideKeyboard() {
    val imm: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

// ==== Transition Extensions Start ====

private val defaultTransitionSet = TransitionSet().apply {
    ordering = TransitionSet.ORDERING_SEQUENTIAL
    addTransition(Fade(Fade.OUT))
    addTransition(Fade(Fade.IN))
}

fun View.beginDelayedTransition(transition: Transition = defaultTransitionSet) {
    (parent as? ViewGroup)?.run {
        TransitionManager.endTransitions(this)
        TransitionManager.beginDelayedTransition(this, transition)
    }
}

fun Fragment.beginDelayedTransition(viewGroup: ViewGroup? = (view as? ViewGroup), transition: Transition = defaultTransitionSet) {
    viewGroup?.run {
        TransitionManager.endTransitions(this)
        TransitionManager.beginDelayedTransition(this, transition)
    }
}

// ==== Transition Extensions End ====

@SuppressLint("MissingPermission")
fun Fragment.logScreenEventInFirebase() {
    if (!BuildConfig.DEBUG) {
        FirebaseAnalytics
            .getInstance(requireContext())
            .setCurrentScreen(requireActivity(), javaClass.simpleName, null)
    }
}

fun <T> Fragment.argument(key: String) =
    lazy { arguments?.get(key) as T ?: throw IllegalArgumentException("$key is required.") }

fun <T> Fragment.argument(key: String, defaultValue: T) = lazy {
    arguments?.get(key) as? T ?: defaultValue
}

fun Fragment.showToast(msg: String) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(@StringRes msgRes: Int) {
    Toast.makeText(requireContext(), getString(msgRes), Toast.LENGTH_SHORT).show()
}

inline fun <T : ViewDataBinding> T.executeAfter(block: T.() -> Unit) {
    block()
    executePendingBindings()
}

// ==== Navigation Component Extensions Start ====

fun NavOptions.defineAnimationInXml() =
    enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1

val defaultNavOptions = NavOptions
    .Builder()
    .setEnterAnim(R.anim.slide_in_right)
    .setExitAnim(R.anim.slide_out_left)
    .setPopEnterAnim(R.anim.slide_in_left)
    .setPopExitAnim(R.anim.slide_out_right)
    .build()

fun NavController.navigateSafely(directions: NavDirections) {
    val action = currentDestination?.getAction(directions.actionId) ?: graph.getAction(directions.actionId)
    if (action != null && currentDestination?.id != action.destinationId) {
        navigate(
            directions, if (action.navOptions != null && action.navOptions!!.defineAnimationInXml()) {
                action.navOptions
            } else {
                defaultNavOptions
            }
        )
    }
}

fun NavController.navigateSafely(@IdRes actionIdRes: Int, arg: Bundle, extra: Navigator.Extras? = null) {
    val action = currentDestination?.getAction(actionIdRes) ?: graph.getAction(actionIdRes)
    if (action != null) {
        navigate(
            actionIdRes,
            arg,
            if (action.navOptions != null && action.navOptions!!.defineAnimationInXml()) {
                action.navOptions
            } else if (extra == null) {
                defaultNavOptions
            } else {
                null
            },
            extra
        )
    }
}

// ref:https://stackoverflow.com/a/60757744
fun <T> Fragment.getNavigationResult(key: String) =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.setNavigationResult(result: T, key: String) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

// 處理完Result要清除，否則會一直重複觸發onChange事件
fun <T> Fragment.clearNavigationResult(key: String) {
    findNavController().currentBackStackEntry?.savedStateHandle?.remove<T>(key)
}

// ==== Navigation Component Extensions End ====

fun RecyclerView.disableItemAnimator() {
    itemAnimator = object : DefaultItemAnimator() {
        override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
            dispatchAddFinished(holder)
            dispatchAddStarting(holder)
            return false
        }
    }
}

fun <T> Flow<T>.onIO() = flowOn(Dispatchers.IO)