package com.example.a0xc0ffee.controller

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

abstract class BaseController(val collection: String) {
    val repository: FirebaseFirestore = Firebase.firestore
}