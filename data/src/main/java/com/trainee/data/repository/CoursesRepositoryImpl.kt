package com.trainee.data.repository

import com.trainee.data.api.StepikApi
import com.trainee.domain.repository.CoursesRepositoryInterface
import com.trainee.domain.repository.model.Course
import com.trainee.domain.repository.model.CourseReviewSummaries

class CoursesRepositoryImpl(private val api: StepikApi) : CoursesRepositoryInterface {
    override suspend fun getCourses(page: Int, isFeatured: Boolean, search: String?): MutableList<Course> {
        val response = api.getCourses(page, isFeatured, search)
        return response.courses.map {
            Course(
                id = it.id,
                title = it.title,
                summary = it.summary,
                cover = it.cover,
                continue_url = it.continue_url,
                create_date = it.create_date,
                display_price = it.display_price ?: "Бесплатно",
                rating = it.rating
            )
        }.toMutableList()
    }

    override suspend fun getRating(courseId: Int): CourseReviewSummaries {
        return api.getRating(courseId)
    }

}