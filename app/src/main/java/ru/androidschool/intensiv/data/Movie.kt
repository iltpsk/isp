package ru.androidschool.intensiv.data

data class Movie(
    val title: String = "",
    val voteAverage: Double = 0.0,
    val description: String = "",
    val studio: String = "",
    val genre: String = "",
    val year: Int,
    val actors: List<Actor>
) {
    val rating: Float
        get() = voteAverage.div(2).toFloat()

}
