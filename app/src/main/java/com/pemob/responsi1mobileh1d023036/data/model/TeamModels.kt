package com.pemob.responsi1mobileh1d023036.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TeamResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("shortName")
    val shortName: String,
    @SerializedName("crest")
    val crest: String,
    @SerializedName("coach")
    val coach: Coach,
    @SerializedName("squad")
    val squad: List<SquadMember>
)

// Data class untuk Pelatih
data class Coach(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("nationality")
    val nationality: String?,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String?
) : Serializable

// Data class untuk setiap Anggota Skuad (Pemain)
data class SquadMember(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("position")
    val position: String,
    @SerializedName("nationality")
    val nationality: String,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String?
) : Serializable