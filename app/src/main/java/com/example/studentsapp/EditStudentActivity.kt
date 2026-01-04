package com.example.studentsapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapp.databinding.ActivityEditStudentBinding

class EditStudentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditStudentBinding
    private var student: Student? = null
    private var position: Int = -1

    companion object {
        // This key now matches the one used in StudentDetailsActivity
        const val EXTRA_STUDENT = "EXTRA_STUDENT"
        const val EXTRA_POSITION = "extra_position"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Simplified and safe way to get the student object
        student = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_STUDENT, Student::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_STUDENT)
        }

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
        student?.let { student ->
            binding.apply {
                editStudentName.setText(student.name)
                editStudentId.setText(student.id)
                editStudentPhone.setText(student.phone)
                editStudentAddress.setText(student.address)
                editStudentCheckbox.isChecked = student.isChecked
            }
        }
    }

    private fun updateStudent() {
        student?.apply {
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