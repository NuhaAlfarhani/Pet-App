package com.example.petapp.response

import com.example.petapp.data.UserRole
import com.google.gson.annotations.SerializedName

class LoginRespon {
    @SerializedName("jwt")
    var jwt : String = ""
    @SerializedName("user")
    var user : UserRespon? = null
}