package com.trainee.domain.repository

import com.trainee.domain.repository.model.Course
import com.trainee.domain.repository.model.CourseReviewSummaries

interface CoursesRepositoryInterface {
    suspend fun getCourses(page: Int = 1, isFeatured: Boolean = true, search: String?): MutableList<Course>

    suspend fun getRating(courseId: Int): CourseReviewSummaries
}