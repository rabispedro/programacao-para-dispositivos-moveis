package com.example.prova.computer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ComputerService(private val computerRepository: ComputerRepository) : ViewModel() {
    fun insert(computer: Computer) {
        viewModelScope.launch {
            if (computer.isValid()) {
                if (computerRepository.findById(computer.id) == null) {
                    computerRepository.insert(computer)
                } else {
                    Log.e("error", "Computer already exists")
                }
            } else {
                Log.e("error", "Invalid Computer")
            }
        }
    }

    fun findAllByClient(callback: (Set<ComputerByClient>) -> Unit) {
        viewModelScope.launch {
            callback(computerRepository.findAllByClient().toSet())
        }
    }

    fun findAll(callback: (Set<Computer>) -> Unit) {
        viewModelScope.launch {
            callback(computerRepository.findAll().toSet())
        }
    }

    fun findAllByCpf(cpf: String, callback: (Set<ComputerByClient>) -> Unit) {
        viewModelScope.launch {
            callback(computerRepository.findAllByCpf(cpf).toSet())
        }
    }

    fun findById(id: String, callback: (Computer?) -> Unit) {
        viewModelScope.launch {
            callback(computerRepository.findById(id))
        }
    }

    fun update(computer: Computer) {
        viewModelScope.launch {
            if (computer.isValid()) {
                computerRepository.update(computer)
            } else {
                Log.e("error", "Invalid Computer")
            }
        }
    }

    fun delete(computer: Computer) {
        viewModelScope.launch {
            computerRepository.delete(computer)
        }
    }
}