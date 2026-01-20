package com.example.studentsapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapp.databinding.StudentRowBinding

class StudentsListAdapter(
  private val students: MutableList<Student>,
  private val onStudentClick: (Student, Int) -> Unit
) :
  RecyclerView.Adapter<StudentsListAdapter.StudentViewHolder>() {
  inner class StudentViewHolder(
    val binding: StudentRowBinding
  ) : RecyclerView.ViewHolder(binding.root) {
    fun bind(student: Student, position: Int) {
      binding.apply {
        name.text = student.name
        id.text = student.id
        checkBox.isChecked = student.isChecked

        checkBox.setOnCheckedChangeListener { _, isChecked ->
          student.isChecked = isChecked

          updateStudentItemUI(isChecked)
        }

        card.setOnClickListener { onStudentClick(student, position) }

        updateStudentItemUI(student.isChecked)
      }
    }

    private fun updateStudentItemUI(isChecked: Boolean) {
      binding.card.setCardBackgroundColor(
        if (isChecked) "#4b9f44".toColorInt() else Color.LTGRAY
      )
    }
  }

  override fun getItemCount() = students.size

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
    val binding = StudentRowBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )

    return StudentViewHolder(binding)
  }

  override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
    val student = students[position]

    holder.bind(student, position)
  }

  fun updateStudent(student: Student, position: Int) {
    students[position] = student
    
    notifyItemChanged(position)
  }
}