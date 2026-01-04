package com.example.studentsapp

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
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

  private val detailsLauncher = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
  ) { result ->
    if (result.resultCode == Activity.RESULT_OK) {
      val updatedStudent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        result.data?.getParcelableExtra("EXTRA_STUDENT", Student::class.java)
      } else {
        @Suppress("DEPRECATION")
        result.data?.getParcelableExtra<Student>("EXTRA_STUDENT")
      }
      val position = result.data?.getIntExtra("EXTRA_POSITION", -1)

      if (updatedStudent != null && position != null && position != -1) {
        // Update the student in the list and notify the adapter
        students[position] = updatedStudent
        adapter.notifyItemChanged(position)
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityStudentsListBinding.inflate(layoutInflater)
    setContentView(binding.root)

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.studentsListActivity)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }

    adapter = StudentsListAdapter(students) { student, position ->
      val intent = Intent(this, StudentDetailsActivity::class.java).apply {
        putExtra("EXTRA_STUDENT", student)
        putExtra("EXTRA_POSITION", position) // Pass the position
      }
      detailsLauncher.launch(intent)
    }

    binding.studentsList.layoutManager = LinearLayoutManager(this)
    binding.studentsList.adapter = adapter

    //    val divider = DividerItemDecoration(
    //      this,
    //      DividerItemDecoration.VERTICAL,
    //    )
    //
    //    binding.studentsList.addItemDecoration(divider)
    //    binding.studentsList.addItemDecoration(
    //      SpaceItemDecoration(4)
    //    )
  }
}