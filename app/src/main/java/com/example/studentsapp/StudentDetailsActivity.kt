package com.example.studentsapp

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapp.databinding.ActivityStudentDetailsBinding

class StudentDetailsActivity : AppCompatActivity() {

  private lateinit var binding: ActivityStudentDetailsBinding
  private var student: Student? = null

  private val editStudentLauncher = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
  ) { result ->
    if (result.resultCode == Activity.RESULT_OK) {
      // Get the updated student from the result
      val updatedStudent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        result.data?.getParcelableExtra("EXTRA_STUDENT", Student::class.java)
      } else {
        @Suppress("DEPRECATION")
        result.data?.getParcelableExtra<Student>("EXTRA_STUDENT")
      }

      // Update the local student object and refresh the UI
      if (updatedStudent != null) {
        student = updatedStudent
        displayStudentInfo()
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityStudentDetailsBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // Receive the student object from the list screen
    student = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      intent.getParcelableExtra("EXTRA_STUDENT", Student::class.java)
    } else {
      @Suppress("DEPRECATION")
      intent.getParcelableExtra<Student>("EXTRA_STUDENT")
    }

    // Display the initial student information
    displayStudentInfo()

    // Set up the button to launch the Edit screen for a result
    binding.editStudentButton.setOnClickListener {
      val intent = Intent(this, EditStudentActivity::class.java)
      // Pass the current student object to the EditStudentActivity
      intent.putExtra("EXTRA_STUDENT", student)
      editStudentLauncher.launch(intent)
    }
  }

  private fun displayStudentInfo() {
    student?.let {
      binding.studentDetailsName.text = it.name
      binding.studentDetailsId.text = it.id
      binding.studentDetailsPhone.text = it.phone
      binding.studentDetailsAddress.text = it.address
      binding.studentDetailsCheckbox.isChecked = it.isChecked
    }
  }
}