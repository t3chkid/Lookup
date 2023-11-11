package com.example.lookup.data.repositories.landmark

import android.graphics.Bitmap
import android.view.Surface

/**
 * A repository for identifying and retrieving information about landmarks.
 */
interface LandmarkRepository {
    /**
     * Retrieves the name and a brief description of the landmark in the given [bitmap].
     *
     * @param bitmap The bitmap containing the landmark.
     * @param rotationDegrees The rotation in degrees which will be a value in {0, 90, 180, 270}.
     * This value is used to denote the rotation of the [bitmap] in degrees.
     * @return A [Result] object containing the name and description of the landmark.
     */
    suspend fun getNameAndDescriptionOfLandmark(
        bitmap: Bitmap,
        rotationDegrees: Int
    ): Result<Pair<String, String>>

    /**
     * Retrieves a list of frequently asked questions about a landmark.
     *
     * @param landmarkName The name of the landmark.
     * @return A [Result] object containing the list of frequently asked questions.
     */
    suspend fun getFAQListAboutLandmark(landmarkName: String): Result<List<String>>
}
