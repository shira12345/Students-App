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
        const val EXTRA_STUDENT = "EXTRA_STUDENT"
        const val EXTRA_POSITION = "EXTRA_POSITION"
        const val EXTRA_IS_DELETED = "EXTRA_IS_DELETED"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        student = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_STUDENT, Student::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_STUDENT)
        }

        position = intent.getIntExtra(EXTRA_POSITION, -1)

        fillInputs()

        binding.saveStudentButton.setOnClickListener {
            val updatedStudent = createUpdatedStudent()
            returnToCaller(updatedStudent, false)
        }

        binding.deleteStudentButton.setOnClickListener {
            returnToCaller(student, true)
        }

        binding.cancelButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    private fun fillInputs() {
        student?.let { s ->
            binding.apply {
                editStudentName.setText(s.name)
                editStudentId.setText(s.id)
                editStudentPhone.setText(s.phone)
                editStudentAddress.setText(s.address)
                editStudentCheckbox.isChecked = s.isChecked
            }
        }
    }

    private fun createUpdatedStudent(): Student? {
        return student?.copy(
            name = binding.editStudentName.text.toString(),
            id = binding.editStudentId.text.toString(),
            phone = binding.editStudentPhone.text.toString(),
            address = binding.editStudentAddress.text.toString(),
            isChecked = binding.editStudentCheckbox.isChecked
        )
    }

    private fun returnToCaller(s: Student?, isDeleted: Boolean) {
        val resultIntent = Intent().apply {
            putExtra(EXTRA_STUDENT, s)
            putExtra(EXTRA_POSITION, position)
            putExtra(EXTRA_IS_DELETED, isDeleted)
        }

        setResult(RESULT_OK, resultIntent)
        finish()
    }
}