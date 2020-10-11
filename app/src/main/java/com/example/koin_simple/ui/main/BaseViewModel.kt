package com.example.koin_simple.ui.main

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel(), Observable {

    /* Start of databinding behavior pillaged from BaseObservable */

    @Transient private var databindingCallbacks: PropertyChangeRegistry? = null

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (databindingCallbacks == null) {
                databindingCallbacks = PropertyChangeRegistry()
            }
        }

        databindingCallbacks!!.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (databindingCallbacks == null) {
                return
            }
        }

        databindingCallbacks!!.remove(callback)
    }

    fun notifyChange() {
        synchronized(this) {
            if (databindingCallbacks == null) {
                return
            }
        }

        databindingCallbacks!!.notifyCallbacks(this, 0, null)
    }

    fun notifyPropertyChanged(fieldId: Int) {
        synchronized(this) {
            if (databindingCallbacks == null) {
                return
            }
        }

        databindingCallbacks!!.notifyCallbacks(this, fieldId, null)
    }

    /* End of databinding behavior pillaged from BaseObservable */
}