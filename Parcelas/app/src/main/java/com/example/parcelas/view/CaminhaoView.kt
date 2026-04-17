package com.example.parcelas.view

import com.example.parcelas.model.Caminhao
import com.example.parcelas.viewmodel.CaminhaoViewModel

class CaminhaoView(val viewModel: CaminhaoViewModel) : StepView {
    fun listaTodos() {
        viewModel.listaTodos().forEach {
            print(it)
        }
    }

    override fun show() {
    }
}