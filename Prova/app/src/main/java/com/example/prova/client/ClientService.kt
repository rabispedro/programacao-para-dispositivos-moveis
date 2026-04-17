package com.example.prova.client

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ClientService(private val clientRepository: ClientRepository): ViewModel() {
    fun insert(client: Client) {
        viewModelScope.launch {
            if (client.isValid()) {
                if (clientRepository.findByCpf(client.cpf) == null) {
                    clientRepository.insert(client)
                } else {
                    Log.e("error", "Client already exists")
                }
            } else {
                Log.e("error", "Invalid Client")
            }
        }
    }

    fun findAll(callback: (Set<Client>) -> Unit) {
        viewModelScope.launch {
            callback(clientRepository.findAll().toSet())
        }
    }

    fun findAllByCpf(cpf: String, callback: (Set<Client>) -> Unit) {
        viewModelScope.launch {
            callback(clientRepository.findAllByCpf(cpf).toSet())
        }
    }

    fun findByCpf(cpf: String, callback: (Client?) -> Unit) {
        viewModelScope.launch {
            callback(clientRepository.findByCpf(cpf))
        }
    }

    fun update(client: Client) {
        viewModelScope.launch {
            if (client.isValid()) {
                clientRepository.update(client)
            } else {
                Log.e("error", "Invalid Client")
            }
        }
    }

    fun delete(client: Client) {
        viewModelScope.launch {
            clientRepository.delete(client)
        }
    }
}