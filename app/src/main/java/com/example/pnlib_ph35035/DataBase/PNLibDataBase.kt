package com.example.pnlib_ph35035.DataBase

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pnlib_ph35035.Dao.PNLibDao
import com.example.pnlib_ph35035.model.Bill
import com.example.pnlib_ph35035.model.Book
import com.example.pnlib_ph35035.model.Customer
import com.example.pnlib_ph35035.model.DetailBill
import com.example.pnlib_ph35035.model.Employee
import java.util.Objects


@Database(entities = [Bill::class, Book::class, Customer::class,Employee::class]
    , version = 3
)
abstract class PNLibDataBase : RoomDatabase() {
    abstract fun PNLibDao() : PNLibDao

    companion object{

        val MIGRATION_FROM_1_2 = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE employees ADD COLUMN imgPathEmp TEXT")
            }

        }

        val MIGRATION_FROM_2_3 = object : Migration(2,3){

            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE employees ADD COLUMN status TEXT")
            }

        }


        @Volatile
        private var INSTANCE: PNLibDataBase? = null

        fun getInstance(context: Context): PNLibDataBase{
            val tempInstance = INSTANCE
            tempInstance?.let {
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext
                    ,PNLibDataBase::class.java
                    ,"pnlib.db")
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_FROM_1_2, MIGRATION_FROM_2_3)
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }


}