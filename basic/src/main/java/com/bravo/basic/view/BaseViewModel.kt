package com.bravo.basic.view

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

open class BaseViewModel : ViewModel() {

    var parentJob: Job? = null
        protected set

    protected fun registerJobFinish() {
        parentJob?.invokeOnCompletion {

        }
    }
}