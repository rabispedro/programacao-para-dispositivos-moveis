package com.example.automoveis

interface IObservable {
    val observers: MutableList<IObserver>

    fun onChange() {
        observers.forEach { it.update() }
    }
}