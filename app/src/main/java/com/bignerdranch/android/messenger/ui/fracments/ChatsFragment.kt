package com.bignerdranch.android.messenger.ui.fracments

import androidx.fragment.app.Fragment
import com.bignerdranch.android.messenger.R
import com.bignerdranch.android.messenger.utilits.APP_ACTIVITY

class ChatsFragment : Fragment(R.layout.fragment_chats) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Чаты"
    }
}