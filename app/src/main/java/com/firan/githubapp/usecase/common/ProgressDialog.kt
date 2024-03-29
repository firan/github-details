package com.firan.githubapp.usecase.common

import android.app.Dialog
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.firan.githubapp.R

/**
 * author: Artur Godlewski
 * thing for show progress when loading data from webservice
 */
private val dialogs = HashMap<LifecycleOwner, Dialog>()

fun LifecycleOwner.showProgressDialog(context: Context) {
    val dialog = Dialog(context).apply {
        setContentView(R.layout.dialog_progressbar)
        setCancelable(false)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        show()
    }
    dialogs[this] = dialog
    this.lifecycle.addObserver(object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onStop() {
            dialog.dismiss()
            dialogs.remove(this@showProgressDialog)
        }
    })
}

fun LifecycleOwner.hideProgressDialog() {
    dialogs[this]?.dismiss()
    dialogs.remove(this)
}