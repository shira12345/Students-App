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
    if (result.resultCode == Activity.RESULT_OK) {
      val updatedStudent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        result.data?.getParcelableExtra("EXTRA_STUDENT", Student::class.java)
      } else {
        @Suppress("DEPRECATION")
        result.data?.getParcelableExtra<Student>("EXTRA_STUDENT")
      }

      if (updatedStudent != null) {
        student = updatedStudent
        displayStudentInfo()

        // Set the result to be sent back to StudentsListActivity
        val resultIntent = Intent()
        resultIntent.putExtra("EXTRA_STUDENT", student)
        resultIntent.putExtra("EXTRA_POSITION", position)
        setResult(Activity.RESULT_OK, resultIntent)
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityStudentDetailsBinding.inflate(layoutInflater)
    setContentView(binding.root)

    student = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      intent.getParcelableExtra("EXTRA_STUDENT", Student::class.java)
    } else {
      @Suppress("DEPRECATION")
      intent.getParcelableExtra<Student>("EXTRA_STUDENT")
    }
    // Get the position from the intent
    position = intent.getIntExtra("EXTRA_POSITION", -1)

    displayStudentInfo()

    binding.editStudentButton.setOnClickListener {
      val intent = Intent(this, EditStudentActivity::class.java)
      intent.putExtra("EXTRA_STUDENT", student)
      // Pass the position along to the Edit activity
      intent.putExtra("EXTRA_POSITION", position)
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
