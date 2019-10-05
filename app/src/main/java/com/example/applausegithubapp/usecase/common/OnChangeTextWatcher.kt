package com.example.applausegithubapp.usecase.common

import android.text.Editable
import android.text.TextWatcher

abstract class OnChangeTextWatcher : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        /**
         * not used in this watcher
         */
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        /**
         * not used in this watcher
         */
    }
}