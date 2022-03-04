package com.bignerdranch.android.messenger.ui.fracments.single_chat

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.messenger.R
import com.bignerdranch.android.messenger.models.CommonModel
import com.bignerdranch.android.messenger.models.UserModel
import com.bignerdranch.android.messenger.ui.fracments.BaseFragment
import com.bignerdranch.android.messenger.utilits.*
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_single_chat.*
import kotlinx.android.synthetic.main.toolbar_info.view.*

class SingleChatFragment(private val contacts: CommonModel) : BaseFragment(R.layout.fragment_single_chat) {

    private lateinit var mListenerInfoToolbar: AppValueEventListener
    private lateinit var mReceivingUser: UserModel
    private lateinit var mToolbarInfo: View
    private lateinit var mRefUser: DatabaseReference
    private lateinit var mRefMessages: DatabaseReference
    private lateinit var mAdapter: SingleChatAdapter
    private lateinit var mRecycleView: RecyclerView
    private lateinit var mMessagesListener: AppValueEventListener
    private var mListMessages = emptyList<CommonModel>()

    override fun onResume() {
        super.onResume()
        initToolbar()
        initRecycleView()
    }

    private fun initRecycleView() {
        mRecycleView = chat_recycle_view
        mAdapter = SingleChatAdapter()
        mRefMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES)
            .child(CURRENT_UID)
            .child(contacts.id)
        mRecycleView.adapter = mAdapter
        mMessagesListener = AppValueEventListener { dataSnapeshot ->
            mListMessages = dataSnapeshot.children.map { it.getCommonModel() }
            mAdapter.setList(mListMessages)
            mRecycleView.smoothScrollToPosition(mAdapter.itemCount)
        }

        mRefMessages.addValueEventListener(mMessagesListener)
    }

    private fun initToolbar() {
        mToolbarInfo = APP_ACTIVITY.mToolbar.toolbar_info
        mToolbarInfo.visibility = View.VISIBLE
        mListenerInfoToolbar = AppValueEventListener {
            mReceivingUser = it.getUserModel()
            initInfoToolbar()
        }

        mRefUser = REF_DATABASE_ROOT.child(NODE_USERS).child(contacts.id)
        mRefUser.addValueEventListener(mListenerInfoToolbar)
        chat_btn_send_me.setOnClickListener {
            val message = chat_input_message.text.toString()
            if (message.isEmpty()) {
                showToast("Необхожимо ввести сообщение")
            } else sendMessage(message, contacts.id, TYPE_TEXT) {
                chat_input_message.setText("")
            }
        }
    }

    private fun initInfoToolbar() {
        if (mReceivingUser.fullname.isEmpty()){
            mToolbarInfo.toolbar_chat_fullname.text = contacts.fullname
        } else
            mToolbarInfo.toolbar_chat_fullname.text = mReceivingUser.fullname

        mToolbarInfo.toolbar_chat_image.downloadAndSetImage(mReceivingUser.photoUrl)
        mToolbarInfo.toolbar_chat_status.text = mReceivingUser.state
    }

    override fun onPause() {
        super.onPause()
        APP_ACTIVITY.mToolbar.toolbar_info.visibility = View.GONE
        mRefUser.removeEventListener(mListenerInfoToolbar)
        mRefMessages.removeEventListener(mMessagesListener)
    }
}