package ru.androidschool.intensiv.data

object MockRepository {

    private val movieList = generateMovies()

    fun getMovies(): List<Movie> = movieList

    fun getMovieByTitle(title: String): Movie = movieList.first {
        it.title == title
    }

    private fun generateMovies(): List<Movie> {
        val actors = List((5..15).random()) {
            Actor(
                firstName = "Thomas",
                lastName = "Holland ${it.inc()}",
                photoUrl = "https://m.media-amazon.com/images/M/MV5BYTk3MDljOWQtNGI2My00OTEzLTlhYjQtOTQ4ODM2MzUwY2IwXkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_.jpg"
            )
        }

        val moviesList = mutableListOf<Movie>()
        for (x in 0..10) {
            val movie = Movie(
                title = "Spider-Man $x",
                voteAverage = 10.0 - x,
                description = "Spider-Man $x full description",
                studio = "WB",
                genre = "action",
                year = 2000.plus(x),
                actors = actors,
            )
            moviesList.add(movie)
        }

        return moviesList
    }
}
