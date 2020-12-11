package com.example.shoppingcalculator.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingcalculator.Event

class EventActivityViewModel : ViewModel() {
    var event: MutableLiveData<Event> = MutableLiveData()
    var repository = EventRepository.instance

    fun init() {
        //repository = GroupRepository.instance
        event = MutableLiveData()
        /*repository.getGroupFromDB {
            val currGroup = CurrentGroup.instance
            val tmp = Group(currGroup.groupName, it)
            group.postValue(tmp)
        }*/
        updateGroup()

    }

    fun getGroup(): LiveData<Event> {
        return event
    }

    fun updateGroup() {
        println("before updating group")
        /*repository.getGroupFromDB {
            println("updating group")
            val currGroup = CurrentGroup.instance
            val tmp = Group(currGroup.groupName, it)
            group.postValue(tmp)
        }*/
    }

    fun setEvent(data: Event) {
        event.value = data
    }

    //test
    /*fun listenChange(it: User?) {
        updateGroup()
    }*/
}