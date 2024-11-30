package com.trainee.data.api

import com.trainee.domain.repository.model.CourseReviewSummaries
import com.trainee.domain.repository.model.CoursesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface StepikApi {
    @GET("courses")
    suspend fun getCourses(
        @Query("page") page: Int = 1,
        @Query("is_featured") isFeatured: Boolean = true,
        @Query("search") search: String?
    ): CoursesResponse

    @GET("courses-review-summaries")
    suspend fun getRating(
        @Query("course") courseId: Int
    ): CourseReviewSummaries
}
