package com.example.shoppingcalculator.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingcalculator.Event

class EventViewModel : ViewModel() {
    var event: MutableLiveData<List<Event>> = MutableLiveData()
    var repository = EventRepository.instance

    fun init() {
        //repository = GroupRepository.instance
        event = MutableLiveData()
        //updateEvent()

    }

    fun getEvents(): LiveData<List<Event>> {
        return event
    }

    fun updateEvents() {
        //println("before updating group")
        repository.getEvents {
            event.postValue(it)
        }
    }

    fun setEvent(data: List<Event>) {
        event.value = data
    }

    //test
    fun listenChange() {
        updateEvents()
    }
}