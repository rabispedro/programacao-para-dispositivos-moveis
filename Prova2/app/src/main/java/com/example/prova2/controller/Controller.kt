package com.example.prova2.controller

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

abstract class Controller(val collection: String) {
    val repository: FirebaseFirestore = Firebase.firestore
}
