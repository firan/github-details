package com.firan.githubapp.usecase.common

import android.text.Editable
import android.text.TextWatcher

/**
 * author: Artur Godlewski
 */
abstract class OnChangeTextWatcher : TextWatcher {
    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
}