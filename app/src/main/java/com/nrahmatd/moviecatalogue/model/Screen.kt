package com.nrahmatd.moviecatalogue.model

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object About: Screen("about")
    object Detail: Screen("home/{movieId}") {
        fun createRoute(movieId: Int) = "home/$movieId"
    }
}