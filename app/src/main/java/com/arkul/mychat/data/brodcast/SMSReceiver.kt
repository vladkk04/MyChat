package com.arkul.mychat.data.brodcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class SMSReceiver: BroadcastReceiver() {
    private val codePattern = "(\\d{6})".toRegex()

    override fun onReceive(context: Context?, intent: Intent?) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action) {
            val extras = intent.extras
            val status = extras!!.get(SmsRetriever.EXTRA_STATUS) as Status

            when (status.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    val message = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE) as String
                    val code: MatchResult? = codePattern.find(message)
                    if (code?.value != null) {
                        Log.d("d", code.value)
                    } else {
                        // TODO: handle error
                    }
                }
                CommonStatusCodes.TIMEOUT -> {

                }
            }

        }
    }
}