package com.trainee.coursehunter.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.trainee.coursehunter.R
import com.trainee.coursehunter.databinding.FragmentHomeBinding
import com.trainee.coursehunter.presentation.home.adapter.CourseRecyclerViewAdapter
import com.trainee.domain.repository.model.Course
import com.trainee.domain.repository.model.CourseListItem

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private val viewmodel: HomeViewModel by viewModels()
    private lateinit var adapter: CourseRecyclerViewAdapter

    private var loading: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerView.adapter = CourseRecyclerViewAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter.addPlaceholders(6)

        setUpObserver()

        viewmodel.loadCourses(Constants.TAG_PROGRAMMING)

        return root
    }

    private fun setUpObserver() {
        viewmodel.courses.observe(viewLifecycleOwner) { result ->
            when (result) {
                is HomeViewModel.Result.Success -> onLoadingSuccess(result.value, adapter)
                HomeViewModel.Result.Loading -> loading = true
                is HomeViewModel.Result.Failure -> onLoadingFailure()
            }
        }
    }

    private fun onLoadingSuccess(courseList: List<Course>, adapter: CourseRecyclerViewAdapter) {
        val listToAdd = courseList.map { CourseListItem.Data(it) }
        adapter.addToCourseList(listToAdd)
    }

    private fun onLoadingFailure() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}