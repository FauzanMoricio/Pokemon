package com.moriss.pokemon.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("abilities") val abilities: List<Ability>,
    @SerializedName("base_experience") val baseExperience: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("order") val order: Int,
    @SerializedName("sprites") val sprites: Sprites,
    @SerializedName("stats") val stats: List<Stat>,
    @SerializedName("types") val types: List<Type>,
    @SerializedName("weight") val weight: Int
)
data class Ability(
    @SerializedName("ability") val ability: AbilityDetail,
)

data class AbilityDetail(
    @SerializedName("name") val name: String,
)
data class Sprites(
    @SerializedName("back_default") val backDefault: String?,
    @SerializedName("front_default") val frontDefault: String?
)
data class Stat(
    @SerializedName("base_stat") val baseStat: Int,
    @SerializedName("stat") val stat: StatDetail
)

data class StatDetail(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)

data class Type(
    @SerializedName("slot") val slot: Int,
    @SerializedName("type") val type: TypeDetail
)

data class TypeDetail(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)
