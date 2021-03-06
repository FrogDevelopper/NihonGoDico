package com.frogdevelopment.nihongo.dico.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.frogdevelopment.nihongo.dico.data.entities.SearchResultEntry

@Dao
interface EntryDao {

    @Transaction
    @Query("SELECT senses.sense_seq, snippet(fts_entries, '<span class=\"keyword\">', '</span>') as kanji, entries.kana, senses.gloss, CASE WHEN favorites.rowid IS NULL THEN 0 ELSE 1 END as favorite" +
            " FROM entries" +
            " INNER JOIN senses ON (entries.entry_seq = senses.entry_seq)" +
            " INNER JOIN fts_entries ON (entries.rowid = fts_entries.docid)" +
            " LEFT OUTER JOIN favorites ON (favorites.sense_seq = senses.sense_seq)" +
            " WHERE fts_entries MATCH :term")
    fun searchByKanji(term: String): LiveData<List<SearchResultEntry>>

    @Transaction
    @Query("SELECT senses.sense_seq, entries.kanji, snippet(fts_entries, '<span class=\"keyword\">', '</span>') as kana, senses.gloss, CASE WHEN favorites.rowid IS NULL THEN 0 ELSE 1 END as favorite" +
            " FROM entries" +
            " INNER JOIN senses ON (entries.entry_seq = senses.entry_seq)" +
            " INNER JOIN fts_entries ON (entries.rowid = fts_entries.docid)" +
            " LEFT OUTER JOIN favorites ON (favorites.sense_seq = senses.sense_seq)" +
            " WHERE fts_entries MATCH :term")
    fun searchByKana(term: String): LiveData<List<SearchResultEntry>>

    @Transaction
    @Query("SELECT senses.sense_seq, entries.kanji, entries.kana, snippet(fts_gloss, '<span class=\"keyword\">', '</span>') as gloss, CASE WHEN favorites.rowid IS NULL THEN 0 ELSE 1 END as favorite" +
            " FROM entries" +
            " INNER JOIN senses ON (entries.entry_seq = senses.entry_seq)" +
            " INNER JOIN fts_gloss ON (senses.rowid = fts_gloss.docid)" +
            " LEFT OUTER JOIN favorites ON (favorites.sense_seq = senses.sense_seq)" +
            " WHERE fts_gloss MATCH :term")
    fun searchByRomaji(term: String): LiveData<List<SearchResultEntry>>

}