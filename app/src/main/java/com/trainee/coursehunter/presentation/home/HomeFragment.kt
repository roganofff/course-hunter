package com.trainee.coursehunter.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.trainee.core.constants.Constants
import com.trainee.coursehunter.R
import com.trainee.coursehunter.databinding.FragmentHomeBinding
import com.trainee.coursehunter.presentation.home.adapter.CourseRecyclerViewAdapter
import com.trainee.domain.repository.model.Course
import com.trainee.domain.repository.model.CourseListItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private val viewmodel: HomeViewModel by viewModels()
    private lateinit var adapter: CourseRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerView.adapter = CourseRecyclerViewAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = CourseRecyclerViewAdapter()
        adapter.addPlaceholders(6)

        setUpObserver()

        viewmodel.loadCourses(Constants.TAG_PROGRAMMING)

        return root
    }

    private fun setUpObserver() {
        viewmodel.courses.observe(viewLifecycleOwner) { result ->
            when (result) {
                is HomeViewModel.Result.Success -> onLoadingSuccess(result.value, adapter)
                is HomeViewModel.Result.Failure -> onLoadingFailure(result.throwable)
            }
        }
    }

    private fun onLoadingSuccess(courseList: List<Course>, adapter: CourseRecyclerViewAdapter) {
        val listToAdd = courseList.map { CourseListItem.Data(it) }
        adapter.addToCourseList(listToAdd)
    }

    private fun onLoadingFailure(throwable: Throwable) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}