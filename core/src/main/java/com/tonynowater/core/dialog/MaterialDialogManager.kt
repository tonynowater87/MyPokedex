package com.tonynowater.core.dialog

import androidx.fragment.app.FragmentActivity
import com.afollestad.materialdialogs.MaterialDialog

class MaterialDialogManager : DialogManager {

    override fun showDoubleButtonsDialog(
        activity: FragmentActivity, title: String,
        content: String, positive: String, negative: String,
        onPositive: () -> Unit, onNegative: () -> Unit
    ) {

        MaterialDialog(activity).show {

            title(text = title)
            message(text = content)
            positiveButton(text = positive) {

                onPositive.invoke()
                it.dismiss()
            }
            negativeButton(text = negative) {

                onNegative.invoke()
                it.dismiss()
            }

            cancelable(false)
        }
    }

    override fun showDoubleButtonsDialog(
        activity: FragmentActivity, title: Int, content: Int,
        positive: Int, negative: Int, onPositive: () -> Unit,
        onNegative: () -> Unit
    ) {
        showDoubleButtonsDialog(
            activity,
            activity.getString(title),
            activity.getString(content),
            activity.getString(positive),
            activity.getString(negative),
            onPositive,
            onNegative
        )
    }

    override fun showSingleButtonDialog(
        activity: FragmentActivity, title: String,
        content: String, positive: String,
        onPositive: () -> Unit
    ) {
        MaterialDialog(activity).show {

            title(text = title)
            message(text = content)
            positiveButton(text = positive) {

                onPositive.invoke()
                it.dismiss()
            }
            cancelable(false)
        }
    }

    override fun showSingleButtonDialog(
        activity: FragmentActivity, title: Int,
        content: Int, positive: Int,
        onPositive: () -> Unit
    ) {

        showSingleButtonDialog(
            activity,
            activity.getString(title),
            activity.getString(content),
            activity.getString(positive),
            onPositive
        )
    }

    override fun showDoNothingDialog(
        activity: FragmentActivity, title: String,
        content: String, positive: String
    ) {
        MaterialDialog(activity).show {

            title(text = title)
            message(text = content)
            positiveButton(text = positive) {

                it.dismiss()
            }

            cancelable(false)
        }
    }

    override fun showDoNothingDialog(
        activity: FragmentActivity, title: Int,
        content: Int, positive: Int
    ) {

        showDoNothingDialog(
            activity,
            activity.getString(title),
            activity.getString(content),
            activity.getString(positive)
        )
    }
}