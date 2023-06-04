package com.example.uts_russellherdianque


import com.google.firebase.database.Exclude

data class Image(
    val imageSrc:String? = null,
    val imageTitle:String? = null,
    val imageDesc:String? = null,
    var rating: Float = 0f,
    @get:Exclude
    @set:Exclude
    var key:String? = null

)