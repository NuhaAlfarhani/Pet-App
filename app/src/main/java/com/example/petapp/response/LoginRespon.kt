package com.example.petapp.response

import com.google.gson.annotations.SerializedName

class LoginRespon {
    @SerializedName("jwt")
    var jwt : String = ""
}