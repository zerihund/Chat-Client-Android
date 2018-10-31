package com.example.zerse.chatclient

import java.io.*
import java.net.Socket
import java.util.*
import kotlin.concurrent.thread

object ChatClient:ChatClientObservable{

    lateinit var input: InputStream
    lateinit var output: PrintStream
    lateinit var socket: Socket
    var currentTime=""

  private val observerActivity = mutableSetOf<ChatClientObserver>()
    override fun notify(msg: Message) {
        observerActivity.forEach { it.updateMessage(msg) }
    }

    override fun deregisterObserver(chatClientObserver: ChatClientObserver) {
        observerActivity.remove(chatClientObserver)
    }

    override fun registerObserver(chatClientObserver: ChatClientObserver) {
        observerActivity.add(chatClientObserver)
    }

     fun run(){

        try {

            socket = Socket("10.0.2.2",30003)
            println("port number: ${socket.localPort}")
            println("New connection established ${socket.inetAddress.hostAddress} ${socket.port}")

            input =socket.getInputStream()
            output = PrintStream(socket.getOutputStream(),true)
            //Message from the server is received below
            val scanner = Scanner(socket.getInputStream())
            while (true) {
                val line = scanner.nextLine()
                notify(Message(line,AppUser.user, currentTime))
            }

        }catch (e: IOException){
            println("Got exception: ${e.message}")
        }
    }
    //Message sending function to the server
    fun sendMessage(message:Message){
        try {
            thread {
                output.println(message.chatText) }
        }
        catch (e:IOException){

        }
    }
}