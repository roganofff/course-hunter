package com.trainee.domain.repository.model

data class Course(
    val id: Int,
    val title: String,
    val summary: String,
    val cover: String,
    val continue_url: String,
    val display_price: String?,
    val create_date: String,
    val rating: CourseReviewSummaries
)
