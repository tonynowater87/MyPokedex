package com.tonynowater.core.dialog

import androidx.fragment.app.FragmentActivity

interface DialogManager {

    fun showDoubleButtonsDialog(
        activity: FragmentActivity,
        title: String, content: String, positive: String, negative: String,
        onPositive: () -> Unit, onNegative: () -> Unit
    )

    fun showDoubleButtonsDialog(
        activity: FragmentActivity,
        title: Int, content: Int, positive: Int, negative: Int, onPositive: () -> Unit,
        onNegative: () -> Unit
    )

    fun showSingleButtonDialog(
        activity: FragmentActivity,
        title: String,
        content: String,
        positive: String,
        onPositive: () -> Unit
    )

    fun showSingleButtonDialog(
        activity: FragmentActivity, title: Int,
        content: Int, positive: Int, onPositive: () -> Unit
    )

    fun showDoNothingDialog(
        activity: FragmentActivity, title: String,
        content: String, positive: String
    )

    fun showDoNothingDialog(
        activity: FragmentActivity, title: Int,
        content: Int, positive: Int
    )
}
