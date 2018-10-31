package com.example.zerse.chatclient

interface ChatClientObservable {
    fun deregisterObserver(chatClientObserver: ChatClientObserver)
    fun registerObserver(chatClientObserver: ChatClientObserver)
    fun notify(msg:Message)
}