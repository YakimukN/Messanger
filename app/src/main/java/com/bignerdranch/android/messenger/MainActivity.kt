package com.bignerdranch.android.messenger

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.bignerdranch.android.messenger.activities.RegisterActivity
import com.bignerdranch.android.messenger.databinding.ActivityMainBinding
import com.bignerdranch.android.messenger.ui.fracments.ChatsFragment
import com.bignerdranch.android.messenger.ui.`object`.AppDrawer
import com.bignerdranch.android.messenger.utilits.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mToolbar: Toolbar
    lateinit var mAppDrawer: AppDrawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        APP_ACTIVITY = this
        initFirebase()
        initUser{
            CoroutineScope(IO).launch{
                initContacts()
            }
            initFields()
            initFunc()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ContextCompat.checkSelfPermission(APP_ACTIVITY, READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
            initContacts()
        }
    }

    override fun onStart() {
        super.onStart()
        AppStates.updateStates(AppStates.ONLINE)
    }

    override fun onStop() {
        super.onStop()
        AppStates.updateStates(AppStates.OFFLINE)
    }

    private fun initFunc() {

        if(AUTH.currentUser != null){
            setSupportActionBar(mToolbar)
            mAppDrawer.create()
            replaceFragment(ChatsFragment(), true)
        } else {
            replaceActivity(RegisterActivity())
        }
    }

    private fun initFields() {
        mToolbar = mBinding.mainToolBar
        mAppDrawer = AppDrawer()

    }
}