package com.example.studentsapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Student(
  var id: String,
  var name: String,
  var phone: String,
  var address: String,
  var isChecked: Boolean = false
) : Parcelable
