package com.example.parcelas.view

import com.example.parcelas.viewmodel.CarroViewModel

class CarroView(val viewModel: CarroViewModel) : StepView {
    fun listarTodos() {
        viewModel.listaTodos().forEach {
            print(it)
        }
    }

    override fun show() {
    }
}