package com.example.studentsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.saveStudentButton.setOnClickListener {
            val name = binding.newStudentName.text.toString().trim()
            val id = binding.newStudentId.text.toString().trim()
            val phone = binding.newStudentPhone.text.toString().trim()
            val address = binding.newStudentAddress.text.toString().trim()
            val isChecked = binding.newStudentCheckbox.isChecked

            if (name.isEmpty() || id.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newStudent = Student(id, name, phone, address, isChecked)

            val resultIntent = Intent()
            resultIntent.putExtra(EditStudentActivity.EXTRA_STUDENT, newStudent)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        binding.cancelButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}