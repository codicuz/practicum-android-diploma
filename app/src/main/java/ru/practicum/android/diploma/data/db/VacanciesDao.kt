package ru.practicum.android.diploma.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow

@Dao
interface VacanciesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vacancy: VacancyEntity)

    @Query("DELETE FROM vacancies WHERE id = :idVacancy")
    suspend fun delete(idVacancy: String)

    @Query("SELECT * FROM vacancies")
    suspend fun getAll(): List<VacancyEntity>

    @Query("SELECT * FROM vacancies WHERE id = :id")
    suspend fun getVacancyById(id: Int): VacancyEntity

}
