package com.swayy.core_database.local

import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.swayy.core_database.models.MpesaMessageEntity

@Dao
interface MessagesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mpesaMessageEntity: MpesaMessageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<MpesaMessageEntity>)

    @Query("SELECT * from mpesa_messages ORDER BY transaction_date DESC LIMIT 1")
    suspend fun getLatestMessage(): MpesaMessageEntity?

    @Query("SELECT * from mpesa_messages ORDER BY transaction_date DESC")
    suspend fun getMessages(): List<MpesaMessageEntity>

    @RawQuery(observedEntities = [MpesaMessageEntity::class])
    suspend fun filter(query:SupportSQLiteQuery): List<MpesaMessageEntity>

    @Query("SELECT * FROM mpesa_messages ORDER BY transaction_date DESC")
    fun getMessagesPaged(): DataSource.Factory<Int, MpesaMessageEntity>

    @RawQuery(observedEntities = [MpesaMessageEntity::class])
    fun filterPaged(query:SupportSQLiteQuery): DataSource.Factory<Int, MpesaMessageEntity>

    @Query("SELECT * from mpesa_messages WHERE body LIKE :term ORDER BY transaction_date DESC")
    fun searchPaged(term: String): DataSource.Factory<Int, MpesaMessageEntity>
}
