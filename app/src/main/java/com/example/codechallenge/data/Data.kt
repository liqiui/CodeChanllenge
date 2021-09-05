package com.example.codechallenge.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Show(
    val country: String = "",
    val description: String = "",
    val drm: Boolean = true,
    val episodeCount: Int = 0,
    val genre: String = "",
    val image: Image = Image(),
    val language: String = "",
    val nextEpisode: NextEpisode? = NextEpisode(),
    val primaryColour: String = "",
    val seasons: List<Seasons>?,
    val slug: String = "",
    val title: String = "",
    val tvChannel: String = ""
): Parcelable

@Parcelize
data class Image(
    val showImage: String = ""
): Parcelable

@Parcelize
data class Data(
    val payload: List<Show>,
    val skip: Int =  0,
    val take: Int =  0,
    val totalRecords: Int =  0
): Parcelable

@Parcelize
data class Seasons(
    val slug: String = ""
): Parcelable

@Parcelize
data class NextEpisode(
    val channel: String? = "",
    val channelLogo: String = "",
    val date: String? = "",
    val html: String = "",
    val url: String = "",
): Parcelable
