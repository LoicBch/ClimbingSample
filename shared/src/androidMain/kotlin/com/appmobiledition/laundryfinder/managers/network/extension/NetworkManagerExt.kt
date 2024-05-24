package com.appmobiledition.laundryfinder.managers.network.extension

import android.app.Activity
import android.content.Context
import com.appmobiledition.laundryfinder.managers.network.NetworkManager
import java.lang.ref.WeakReference

internal var NetworkManager.Companion.activity: Activity?
    get() = networkLoyal.activity?.get()
    set(value) {
        networkLoyal.activity = WeakReference(value)
    }

internal fun NetworkManager.Companion.configure(context: Context) = networkLoyal.configure(context)

