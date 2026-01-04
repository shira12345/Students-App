package com.example.studentsapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentsapp.databinding.ActivityStudentsListBinding

class StudentsListActivity : AppCompatActivity() {
  private lateinit var binding: ActivityStudentsListBinding
  private lateinit var adapter: StudentsListAdapter

  private val students = mutableListOf(
    Student("213771538", "Natan Sinai", "052-4436294", "Zores 6a, Haifa", true),
    Student("213683501", "Shira Magrafta", "050-8637364", "Somewhere, Modi'in"),
    Student("213454051", "Yael Abbo", "054-2261484", "Somewhere, Kirayt Ono", true)
  )

  // This launcher is now unused, but kept for future use.
  // The logic to get an updated student back will need to be moved to StudentDetailsActivity.
  private val editStudentLauncher = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
  ) { result ->
    if (result.resultCode != RESULT_OK) return@registerForActivityResult

    @Suppress("DEPRECATION")
    val updatedStudent =
      result.data?.getParcelableExtra<Student>(EditStudentActivity.EXTRA_STUDENT)
        ?: return@registerForActivityResult

    val position = result.data?.getIntExtra(EditStudentActivity.EXTRA_POSITION, -1)
      ?: return@registerForActivityResult

    adapter.updateStudent(updatedStudent, position)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    binding = ActivityStudentsListBinding.inflate(layoutInflater)
    setContentView(binding.root)

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.studentsListActivity)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

    adapter = StudentsListAdapter(students) { student, position ->
      // Changed this to launch StudentDetailsActivity
      val intent = Intent(this, StudentDetailsActivity::class.java).apply {
        // Assuming StudentDetailsActivity can handle a Parcelable Student object
        // with the key "EXTRA_STUDENT".
        putExtra("EXTRA_STUDENT", student)
      }

      startActivity(intent)
    }

    binding.studentsList.layoutManager = LinearLayoutManager(this)
    binding.studentsList.adapter = adapter
  }
}