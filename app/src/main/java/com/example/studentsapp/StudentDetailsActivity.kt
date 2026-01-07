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
  private var position: Int = -1

  private val editStudentLauncher = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
  ) { result ->
    if (result.resultCode == RESULT_OK) {
      val updatedStudent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        result.data?.getParcelableExtra(EditStudentActivity.EXTRA_STUDENT, Student::class.java)
      } else {
        @Suppress("DEPRECATION")
        result.data?.getParcelableExtra(EditStudentActivity.EXTRA_STUDENT)
      }

      if (updatedStudent == null) {
        return@registerForActivityResult
      }

      student = updatedStudent
      displayStudentInfo()

      val resultIntent = Intent()
      resultIntent.putExtra("EXTRA_STUDENT", student)
      resultIntent.putExtra("EXTRA_POSITION", position)
      setResult(Activity.RESULT_OK, resultIntent)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityStudentDetailsBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // Set the custom toolbar as the ActionBar
    setSupportActionBar(binding.toolbar)

    // Add the back arrow to the ActionBar
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    student = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      intent.getParcelableExtra("EXTRA_STUDENT", Student::class.java)
    } else {
      @Suppress("DEPRECATION")
      intent.getParcelableExtra("EXTRA_STUDENT")
    }
    position = intent.getIntExtra("EXTRA_POSITION", -1)

    displayStudentInfo()

    binding.editStudentButton.setOnClickListener {
      val intent = Intent(this, EditStudentActivity::class.java)
      intent.putExtra(EditStudentActivity.EXTRA_STUDENT, student)
      intent.putExtra(EditStudentActivity.EXTRA_POSITION, position)
      editStudentLauncher.launch(intent)
    }
  }

  // Handle the click on the back arrow
  override fun onSupportNavigateUp(): Boolean {
    finish() // Closes the current activity and returns to the previous one
    return true
  }

  private fun displayStudentInfo() {
    student?.let {
      // Set the title of the ActionBar to the student's name
      supportActionBar?.title = it.name

      binding.studentDetailsName.text = it.name
      binding.studentDetailsId.text = it.id
      binding.studentDetailsPhone.text = it.phone
      binding.studentDetailsAddress.text = it.address
      binding.studentDetailsCheckbox.isChecked = it.isChecked
    }
  }
}
