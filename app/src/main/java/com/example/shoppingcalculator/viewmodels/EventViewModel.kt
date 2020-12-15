package com.example.shoppingcalculator.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingcalculator.Event

class EventViewModel : ViewModel() {

    var repository = EventRepository.instance

    fun init() {
        //repository = GroupRepository.instance
        repository.event = MutableLiveData()
        //updateEvent()

    }

    fun getEvents(): LiveData<List<Event>> {
        return repository.event
    }

    fun updateEvents() {
        //println("before updating group")
        repository.getEvents {
            repository.event.postValue(it)
        }
    }

    fun setEvent(data: List<Event>) {
        repository.event.value = data
    }

    //test
    fun listenChange() {
        updateEvents()
    }
}