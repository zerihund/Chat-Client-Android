package com.example.zerse.chatclient

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_top.*
import kotlinx.android.synthetic.main.toolbar_chat.*

class TopActivity : AppCompatActivity(),ChatClientObserver {
    private var topChat =ArrayList<String>()
    var time =""
    override fun updateMessage(message: Message) {
        Log.d("top",message.chatText)
        topChat.add(message.chatText)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)
        setSupportActionBar(toolbar)
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,topChat)
        top_list.adapter =adapter

        ChatClient.registerObserver(this)
        ChatClient.sendMessage(Message( ":top",AppUser.user,time))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_topper,menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_backward->{
                val intent = Intent(this,ChatActivity::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)

    }
}
