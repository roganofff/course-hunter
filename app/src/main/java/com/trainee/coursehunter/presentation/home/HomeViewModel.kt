package com.trainee.coursehunter.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trainee.domain.repository.CoursesRepositoryInterface
import com.trainee.domain.repository.model.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CoursesRepositoryInterface
) : ViewModel() {

    private val _courses = MutableLiveData<Result<MutableList<Course>>>()
    val courses: LiveData<Result<MutableList<Course>>> get() = _courses

    private var job: Job? = null

    private var page: Int = 1

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _courses.value = Result.Failure(throwable)
    }

    fun loadCourses(searchQuery: String) {
        cancelJobIfRunning()
        job = viewModelScope.launch(coroutineExceptionHandler) {
            _courses.value = Result.Success(
                repository.getCourses(
                    page = page,
                    search = searchQuery,
                )
            )
            page++
        }
    }

    private fun cancelJobIfRunning() {
        job?.takeIf { it.isActive  }?.cancel()
    }

    sealed class Result<out T> {
        data class Success<out T : Any>(val value: T) : Result<T>()
        data class Failure(val throwable: Throwable) : Result<Nothing>()
    }
}