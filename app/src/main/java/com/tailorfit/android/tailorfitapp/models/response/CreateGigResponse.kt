package com.tailorfit.android.tailorfitapp.models.response


import com.google.gson.annotations.SerializedName

data class CreateGigResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String
)