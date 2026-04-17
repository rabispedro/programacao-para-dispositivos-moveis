package com.example.parcelas.view

import com.example.parcelas.viewmodel.OnibusViewModel

class OnibusView(val viewModel: OnibusViewModel) : StepView {
    fun listaTodos() {
        viewModel.listaTodos().forEach {
            print(it)
        }
    }

    override fun show() {
    }
}