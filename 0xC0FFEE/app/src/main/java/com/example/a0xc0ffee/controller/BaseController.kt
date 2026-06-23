package com.example.a0xc0ffee.controller

import com.example.a0xc0ffee.model.mapper.Mapper
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

abstract class BaseController<T>(val collection: String) {
    abstract val mapper: Mapper<T>
    val repository: FirebaseFirestore = Firebase.firestore
}