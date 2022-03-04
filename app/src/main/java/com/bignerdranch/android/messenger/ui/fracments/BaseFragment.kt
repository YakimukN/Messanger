package com.bignerdranch.android.messenger.ui.fracments

import androidx.fragment.app.Fragment
import com.bignerdranch.android.messenger.MainActivity
import com.bignerdranch.android.messenger.utilits.APP_ACTIVITY

open class BaseFragment(val layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()

        APP_ACTIVITY.mAppDrawer.disableDriwer()
    }

    override fun onStop() {
        super.onStop()
        APP_ACTIVITY.mAppDrawer.enableDriwer()
    }
}