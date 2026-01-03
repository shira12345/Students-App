package com.example.studentsapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studentsapp.databinding.ActivityEditStudentBinding

class EditStudentActivity : AppCompatActivity() {
  private lateinit var binding: ActivityEditStudentBinding
  private lateinit var student: Student
  private var position: Int = -1

  companion object {
    const val EXTRA_STUDENT = "extra_student"
    const val EXTRA_POSITION = "extra_position"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    binding = ActivityEditStudentBinding.inflate(layoutInflater)
    setContentView(binding.root)

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editStudentActivity)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

    @Suppress("DEPRECATION")
    student = intent.getParcelableExtra(EXTRA_STUDENT)!!
    position = intent.getIntExtra(EXTRA_POSITION, -1)

    fillInputs()

    binding.saveStudentButton.setOnClickListener {
      updateStudent()

      returnToStudentsListWithUpdatedStudent()
    }

    binding.cancelButton.setOnClickListener {
      setResult(RESULT_CANCELED)

      finish()
    }
  }

  private fun fillInputs() {
    binding.apply {
      editStudentName.setText(student.name)
      editStudentId.setText(student.id)
      editStudentPhone.setText(student.phone)
      editStudentAddress.setText(student.address)
      editStudentCheckbox.isChecked = student.isChecked
    }
  }

  private fun updateStudent() {
    student.apply {
      name = binding.editStudentName.text.toString()
      id = binding.editStudentId.text.toString()
      phone = binding.editStudentPhone.text.toString()
      address = binding.editStudentAddress.text.toString()
      isChecked = binding.editStudentCheckbox.isChecked
    }
  }

  private fun returnToStudentsListWithUpdatedStudent() {
    val resultIntent = Intent().apply {
      putExtra(EXTRA_STUDENT, student)
      putExtra(EXTRA_POSITION, position)
    }

    setResult(RESULT_OK, resultIntent)
    finish()
  }
}