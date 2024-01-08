package com.example.petapp.response

import com.example.petapp.data.UserRole
import com.google.gson.annotations.SerializedName

class UserRespon {
    var id: Int = 0
    var username: String = ""
    var email: String = ""
    var provider: String = ""
    var confirmed: String = ""
    var blocked: Boolean = false
    var createdAt: String = ""
    var updatedAt: String = ""
    @SerializedName("nama_lengkap")
    var namaLengkap: String = ""
    @SerializedName("no_hp")
    var noHp: String = ""
    @SerializedName("alamat")
    var alamat: String = ""
    @SerializedName("peran")
    var peran: UserRole = UserRole.User
    @SerializedName("img")
    var img: UserImgResponse? = null
}
class UserImgResponse {
    @SerializedName("id")
    var id: Int = 0
    @SerializedName("attributes")
    var attributes: UserImgAttributes = UserImgAttributes()
}

class UserImgAttributes {
    @SerializedName("name")
    var name: String = ""
    @SerializedName("url")
    var url : String = ""
}