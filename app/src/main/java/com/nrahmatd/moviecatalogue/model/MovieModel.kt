package com.nrahmatd.moviecatalogue.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *
 * @author Nur Rahmat Dwi Riyanto
 */

@Parcelize
data class MovieModel(
    val id: Int,
    val imgMovie: Int,
    val titleMovie: String,
    val dateMovie: String,
    val descMovie: String,
    val langMovie: String,
    val runtimeMovie: String,
    val fbAccountMovie: String,
    val twitterAccountMovie: String,
    val instaAccountMovie: String,
    val genresMovie: String
) : Parcelable
