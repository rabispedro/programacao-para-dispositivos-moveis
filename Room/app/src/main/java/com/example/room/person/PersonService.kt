package com.example.room.person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PersonService(private val repository: PersonRepository): ViewModel() {
    fun insert(person: Person, callback: (Person?) -> Unit) {
        viewModelScope.launch {
            repository.insert(person)
            callback(repository.findByCpf(person.cpf))
        }
    }

    fun findByCpf(cpf: String, callback: (Person?) -> Unit) {
        viewModelScope.launch {
            callback(repository.findByCpf(cpf))
        }
    }

    fun findAll(callback: (List<Person>) -> Unit) {
        viewModelScope.launch {
            callback(repository.findAll())
        }
    }

    fun update(person: Person) {
        viewModelScope.launch {
            repository.update(person)
        }
    }

    fun delete(person: Person) {
        viewModelScope.launch {
            repository.delete(person)
        }
    }
}