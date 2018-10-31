package com.example.zerse.chatclient

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_chat.*


class ChatActivity : AppCompatActivity(),ChatClientObserver {
    var message=""
    var time=""
    private var user=""
    private var messageList= ArrayList<Message>()

    override fun updateMessage(message: Message) {
        user = message.chatText.substringAfter("from").substringBefore("at").trim()
        runOnUiThread {
            var chatMessage = message.chatText.substringBefore("from")
            time = message.chatText.substringAfterLast("at").trim(' ').substringAfter("T").substringBeforeLast(":")
            messageList.add(Message(chatMessage, user, time))

            chat_list.smoothScrollToPosition(adapter.count - 1)
            adapter.notifyDataSetChanged()
        }
    }
    lateinit var adapter:ChatListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setSupportActionBar(chatToolbar)
        ChatClient.registerObserver(this)

        adapter = ChatListAdapter(this, messageList)
        chat_list.adapter = adapter

        btnSend.setOnClickListener {

            if (!editText_send.text.isEmpty()){
                var message= editText_send.text.toString()
                ChatClient.sendMessage(Message(message, AppUser.user,time))
                editText_send.text.clear()
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_chat,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.user_action ->{
                val intent = Intent(this,UsersActivity::class.java)
                startActivity(intent)
            }
            R.id.logout_action->{
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setMessage("Are sure you want to leave the chat room?")

                dialogBuilder.setPositiveButton("Leave"){dialog, which ->
                    ChatClient.sendMessage(Message(":quit $",AppUser.user,time))
                    ChatClient.deregisterObserver(this)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                dialogBuilder.setNegativeButton("Stay"){dialog,which ->

                }
                val dialog: AlertDialog = dialogBuilder.create()
                dialog.show()

            }
            R.id.top_action->{
               val intent =Intent(this,TopActivity::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)

    }

}
