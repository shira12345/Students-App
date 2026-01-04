package com.example.studentsapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapp.databinding.StudentRowBinding

class StudentsListAdapter(
  private val students: List<Student>,
) :
  RecyclerView.Adapter<StudentsListAdapter.StudentViewHolder>() {
  class StudentViewHolder(
    val binding: StudentRowBinding
  ) : RecyclerView.ViewHolder(binding.root)

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

    holder.binding.apply {
      name.text = student.name
      id.text = student.id
      checkBox.isChecked = student.isChecked

      checkBox.setOnCheckedChangeListener { _, isChecked ->
        student.isChecked = isChecked

        updateRowUI(holder, student)
      }
    }
    
    updateRowUI(holder, student)
  }

  private fun updateRowUI(holder: StudentViewHolder, student: Student) {
    holder.binding.card.setCardBackgroundColor(
      if (student.isChecked) "#4b9f44".toColorInt() else Color.LTGRAY
    )
  }

  override fun getItemCount() = students.size
}