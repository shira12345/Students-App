package com.example.studentsapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapp.databinding.ActivityNewStudentBinding

class NewStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "New Student"

        binding.saveStudentButton.setOnClickListener {
            val name = binding.newStudentName.text.toString()
            val id = binding.newStudentId.text.toString()
            val phone = binding.newStudentPhone.text.toString()
            val address = binding.newStudentAddress.text.toString()
            val isChecked = binding.newStudentCheckbox.isChecked

            val newStudent = Student(id, name, phone, address, isChecked)

            val resultIntent = Intent()
            resultIntent.putExtra("EXTRA_STUDENT", newStudent)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        binding.cancelButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}