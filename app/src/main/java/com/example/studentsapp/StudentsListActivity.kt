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
            val isDeleted = result.data?.getBooleanExtra(EditStudentActivity.EXTRA_IS_DELETED, false) ?: false
            val position = result.data?.getIntExtra(EditStudentActivity.EXTRA_POSITION, -1) ?: -1

            if (isDeleted && position != -1) {
                students.removeAt(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, students.size)
                return@registerForActivityResult
            }

            val updatedStudent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getParcelableExtra("EXTRA_STUDENT", Student::class.java)
            } else {
                @Suppress("DEPRECATION")
                result.data?.getParcelableExtra<Student>("EXTRA_STUDENT")
            }

            if (updatedStudent != null && position != -1) {
                students[position] = updatedStudent
                adapter.notifyItemChanged(position)
            }
        }
    }

    private val newStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val newStudent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getParcelableExtra("EXTRA_STUDENT", Student::class.java)
            } else {
                @Suppress("DEPRECATION")
                result.data?.getParcelableExtra<Student>("EXTRA_STUDENT")
            }

            if (newStudent != null) {
                students.add(newStudent)
                adapter.notifyItemInserted(students.size - 1)
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
                putExtra("EXTRA_POSITION", position)
            }
            detailsLauncher.launch(intent)
        }

        binding.studentsList.layoutManager = LinearLayoutManager(this)
        binding.studentsList.adapter = adapter

        binding.addStudentFab.setOnClickListener {
            val intent = Intent(this, NewStudentActivity::class.java)
            newStudentLauncher.launch(intent)
        }
    }
}
