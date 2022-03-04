package com.bignerdranch.android.messenger.ui.fracments

import androidx.fragment.app.Fragment
import com.bignerdranch.android.messenger.MainActivity
import com.bignerdranch.android.messenger.R
import com.bignerdranch.android.messenger.activities.RegisterActivity
import com.bignerdranch.android.messenger.utilits.*
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_enter_code.*

class EnterCodeFragment(val phoneNumber: String, val id: String) : Fragment(R.layout.fragment_enter_code) {

    override fun onStart() {
        super.onStart()

        (activity as RegisterActivity).title = phoneNumber

        register_input_code.addTextChangedListener(AppTextWatcher{

            val string: String = register_input_code.text.toString()
            if (string.length == 6){
                enterCode()
            }
        })
    }

    private fun enterCode(){
//        showToast("Ok")

        val code = register_input_code.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful){

                val uid = AUTH.currentUser?.uid.toString()
                val dateMap = mutableMapOf<String, Any>()
                dateMap[CHILD_ID] = uid
                dateMap[CHILD_PHONE] = phoneNumber
                dateMap[CHILD_USERNAME] = uid

                REF_DATABASE_ROOT.child(NODE_PHONES).child(phoneNumber).setValue(uid)
                    .addOnFailureListener { showToast(it.message.toString()) }
                    .addOnSuccessListener {
                        REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dateMap)
                            .addOnSuccessListener {
                                showToast("Добро пожаловать")
                                (activity as RegisterActivity).replaceActivity(MainActivity())
                            }
                            .addOnFailureListener { showToast(it.message.toString()) }
                    }

            } else showToast(task.exception?.message.toString())
        }
    }
}