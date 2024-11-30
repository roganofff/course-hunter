package com.trainee.domain.repository.model

sealed class CourseListItem {
    data class Data(val course: Course): CourseListItem()
    data object Placeholder : CourseListItem()
}