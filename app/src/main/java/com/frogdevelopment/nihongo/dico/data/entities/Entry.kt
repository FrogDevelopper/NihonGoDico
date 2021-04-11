package com.frogdevelopment.nihongo.dico.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

@JsonIgnoreProperties("rowid")
@Entity(tableName = "entries")
class Entry(
        @PrimaryKey(autoGenerate = true) val rowid: Int,
        val entry_seq: String,
        val kanji: String?,
        val kana: String,
        val reading: String) : Serializable
