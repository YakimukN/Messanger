package com.bignerdranch.android.messenger.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.bignerdranch.android.messenger.R
import com.bignerdranch.android.messenger.databinding.ActivityRegistrBinding
import com.bignerdranch.android.messenger.ui.fracments.EnterPhoneNumberFragment
import com.bignerdranch.android.messenger.utilits.initFirebase
import com.bignerdranch.android.messenger.utilits.replaceFragment

class RegisterActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityRegistrBinding
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityRegistrBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initFirebase()
    }

    override fun onStart() {
        super.onStart()
        mToolbar = mBinding.registerToolbar
        setSupportActionBar(mToolbar)
        title = getString(R.string.register_title_your_phone)
        replaceFragment(EnterPhoneNumberFragment(), true)
    }
}