package com.example.zerse.chatclient

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(),ChatClientObserver {
    var userName = ""
    var time=""
    override fun updateMessage(message: Message) {
        if (message.chatText == "The user set to $userName.") {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
        else{
            //runOnUiThread{ Toast.makeText(this, message.chatText, Toast.LENGTH_LONG).show() }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ChatClient.registerObserver(this)

        thread { ChatClient.run() }

        btnEnter.setOnClickListener {

            if (!editText_user.text.isEmpty()) {
                userName = editText_user.text.toString()
                AppUser.user = userName
                ChatClient.sendMessage(Message(":user $userName",AppUser.user,time))


            } else {
                Toast.makeText(this, "Set a user name first", Toast.LENGTH_LONG).show()
            }

        }
    }
}
