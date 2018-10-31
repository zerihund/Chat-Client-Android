package com.example.zerse.chatclient

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

private const val VIEW_TYPE_MY_MESSAGE = 1
private const val VIEW_TYPE_OTHER_MESSAGE = 2
class ChatListAdapter(private val context:Context, private val messages: ArrayList<Message>):BaseAdapter() {
    @TargetApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater:LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val currentMessage = messages[position]
        if (AppUser.user== currentMessage.user){

            val rowView = inflater.inflate(R.layout.my_message, parent, false)

            val messageText= rowView.findViewById<TextView>(R.id.txtMyMessage)
            messageText.text = currentMessage.chatText

            val messageTime = rowView.findViewById<TextView>(R.id.txtMyMessageTime)
            messageTime.text=currentMessage.time
            return rowView

        } else {
            val rowView = inflater.inflate(R.layout.other_message, parent, false)

            val messageText= rowView.findViewById<TextView>(R.id.txtOtherMessage)
            messageText.text = currentMessage.chatText

            val messageTime = rowView.findViewById<TextView>(R.id.txtOtherMessageTime)
            messageTime.text=currentMessage.time

            val userText =rowView.findViewById<TextView>(R.id.txtUser)
            userText.text=currentMessage.user

            return rowView
        }

    }

    override fun getItem(position: Int): Any {
        return messages[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return messages.size
    }


}
