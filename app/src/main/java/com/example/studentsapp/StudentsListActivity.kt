package com.example.studentsapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentsapp.databinding.ActivityStudentsListBinding

val students = listOf(
  Student("963261499219", "Natan Sinai", "052-4436294", "Zores 6, Haifa", true),
  Student("296076149965", "Shira Magrafta", "052-4436294", "Zores 6, Haifa"),
  Student("823686916722", "Yael Abbo", "052-4436294", "Zores 6, Haifa", true)
)

class StudentsListActivity : AppCompatActivity() {
  private lateinit var binding: ActivityStudentsListBinding

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

    binding.studentsList.layoutManager = LinearLayoutManager(this)
    binding.studentsList.adapter = StudentsListAdapter(students)

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