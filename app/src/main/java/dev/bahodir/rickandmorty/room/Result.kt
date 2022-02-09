package dev.bahodir.rickandmorty.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.bahodir.rickandmorty.model.Location
import dev.bahodir.rickandmorty.model.Origin

@Entity
data class Result (
    val created: String = "",
    val gender: String = "",
    @PrimaryKey
    val id: Int = 0,
    val image: String = "",
    val name: String = "",
    val species: String = "",
    val status: String = "",
    val type: String = "",
    val url: String = ""
)