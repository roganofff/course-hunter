package com.trainee.data.repository

import com.trainee.data.api.StepikApi
import com.trainee.domain.repository.CoursesRepositoryInterface
import com.trainee.domain.repository.model.Course
import com.trainee.domain.repository.model.CourseReviewSummaries

class CoursesRepositoryImpl(private val api: StepikApi) : CoursesRepositoryInterface {
    override suspend fun getCourses(page: Int, isFeatured: Boolean, search: String?): MutableList<Course> {
        val response = api.getCourses(page, isFeatured, search)
        return response.courses.toMutableList()
    }

    override suspend fun getRating(courseId: Int): CourseReviewSummaries {
        return api.getRating(courseId)
    }

}