package com.example.studentsapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapp.databinding.ActivityStudentDetailsBinding

class StudentDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Updated to receive a single Parcelable Student object
        val student = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("EXTRA_STUDENT", Student::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Student>("EXTRA_STUDENT")
        }

        // Display the data if the student object is not null
        student?.let {
            binding.studentDetailsName.text = it.name
            binding.studentDetailsId.text = it.id
            binding.studentDetailsPhone.text = it.phone
            binding.studentDetailsAddress.text = it.address
            binding.studentDetailsCheckbox.isChecked = it.isChecked
        }

        // Set up the button to navigate to the Edit screen
        binding.editStudentButton.setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java)
            // Pass the same Student object along to the EditStudentActivity
            intent.putExtra("EXTRA_STUDENT", student)
            startActivity(intent)
        }
    }
}