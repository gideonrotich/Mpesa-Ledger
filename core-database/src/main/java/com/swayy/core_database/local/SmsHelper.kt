package com.swayy.core_database.local

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Telephony
import com.swayy.core_database.models.Sms
import java.lang.Exception

/**
 * Created by MarkNjunge.
 * mark.kamau@outlook.com
 * https://github.com/MarkNjunge
 */

class SmsHelper(private val context: Context) {

    @SuppressLint("Recycle")
    private fun getRawMessages(): List<Sms> {
        val messageList = mutableListOf<Sms>()

        val messagesCursor =
            context.contentResolver.query(
                Telephony.Sms.Inbox.CONTENT_URI,
                null,
                null,
                null,
                Telephony.Sms.DEFAULT_SORT_ORDER
            ) ?: throw RuntimeException("Unable to get messages")

        val bodyIndex = messagesCursor.getColumnIndexOrThrow("body")
        val addressIndex = messagesCursor.getColumnIndexOrThrow("address")
        val dateIndex = messagesCursor.getColumnIndexOrThrow("date")

        messagesCursor.moveToFirst()
        do {
            try {
                val address = messagesCursor.getString(addressIndex)
                val date = messagesCursor.getString(dateIndex)

                if (address == "MPESA") {
                    val body = messagesCursor.getString(bodyIndex)
                    messageList.add(Sms(address, body, date.toLong()))
                }
            } catch (e: Exception) {
//                FirebaseCrashlytics.getInstance().recordException(e)
            }
        } while (messagesCursor.moveToNext())

        messagesCursor.close()

        return messageList
    }

    @SuppressLint("DefaultLocale")
    fun getMpesaMessages(): List<Sms> {
        return getRawMessages()
            .filter { it.body.isNotEmpty() }
            // Remove messages than are not transactions. e.g. failed, insufficient transaction
            .filter { it.body.toLowerCase().contains(Regex("(.{10} )(confirmed.)")) }
            .sortedBy { it.date }
    }
}