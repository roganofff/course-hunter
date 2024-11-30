package com.trainee.coursehunter.presentation.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trainee.coursehunter.databinding.CourseItemBinding
import com.trainee.coursehunter.databinding.PlaceholderCourseItemBinding
import com.trainee.domain.repository.model.Course
import com.trainee.domain.repository.model.CourseListItem
import kotlinx.coroutines.withContext

class CourseRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val courseList = mutableListOf<CourseListItem>()

    companion object {
        const val TYPE_DATA = 1
        const val TYPE_PLACEHOLDER = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_DATA -> {
                val itemBinding = CourseItemBinding.inflate(inflater, parent, false)
                CourseViewHolder(itemBinding)
            }
            TYPE_PLACEHOLDER -> {
                val itemBinding = PlaceholderCourseItemBinding.inflate(inflater, parent, false)
                PlaceholderCourseViewHolder(itemBinding)
            }
            else -> throw IllegalArgumentException("Unknown viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CourseViewHolder -> holder.bind(courseList[position] as CourseListItem.Data)
            is PlaceholderCourseViewHolder -> holder.startShimmer()
        }
    }

    override fun getItemCount(): Int = courseList.size

    override fun getItemViewType(position: Int): Int {
        return when (courseList[position]) {
            is CourseListItem.Data -> TYPE_DATA
            is CourseListItem.Placeholder -> TYPE_PLACEHOLDER
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is PlaceholderCourseViewHolder) {
            holder.stopShimmer()
        }
    }

    fun addPlaceholders(count: Int) {
        repeat(count) { courseList.add(CourseListItem.Placeholder) }
        notifyItemRangeInserted(courseList.size - count, count)
    }

    fun removePlaceholders() {
        val count = courseList.count { it is CourseListItem.Placeholder }
        courseList.removeAll { it is CourseListItem.Placeholder }
        notifyItemRangeRemoved(courseList.size, count)
    }

    fun addToCourseList(newCourseList: List<CourseListItem.Data>) {
        val start = courseList.size
        courseList.addAll(newCourseList)
        notifyItemRangeInserted(start, newCourseList.size)
    }

    inner class CourseViewHolder(
        private val binding: CourseItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CourseListItem.Data) {
            with (binding) {
                val course = data.course
                courseTitle.text = course.title
                courseDescription.text = course.summary
                coursePrice.text = course.display_price
                courseDate.text = course.create_date
                loadAndSetCover(course)
            }
        }

        private fun loadAndSetCover(course: Course) {
            Glide.with(itemView.context.applicationContext)
                .load(course.cover)
                .override(328, 114)
                .centerCrop()
                .into(binding.courseCover)
        }
    }

    inner class PlaceholderCourseViewHolder(
        private val binding: PlaceholderCourseItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun startShimmer() {
            binding.shimmerLayout.startShimmer()
        }

        fun stopShimmer() {
            binding.shimmerLayout.stopShimmer()
        }
    }
}