package com.example.telematics_project.event

import androidx.lifecycle.MutableLiveData

typealias EventEmitter = MutableLiveData<Event<Unit>>
typealias DataEventEmitter<T> = MutableLiveData<Event<T>>

fun EventEmitter.emit() {
    this.value = Event(Unit)
}

fun <T> DataEventEmitter<T>.emit(data: T) {
    this.value = Event(data)
}
