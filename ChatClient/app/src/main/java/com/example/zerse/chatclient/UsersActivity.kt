package com.example.zerse.chatclient

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import kotlinx.android.synthetic.main.activity_users.*

class UsersActivity : AppCompatActivity(),ChatClientObserver {
    private var usersList = ArrayList<String>()
    var time =""
    var users =""

    override fun updateMessage(message: Message) {
        users = message.chatText.substringAfter("from").substringBefore("at").trim()
        usersList.add(users)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        setSupportActionBar(chatToolbar)
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,usersList)
        users_list.adapter =adapter
        ChatClient.registerObserver(this)
        ChatClient.sendMessage(Message( ":Users",AppUser.user,time))


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_user,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_back ->{
                val intent = Intent(this,ChatActivity::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)

    }
}
