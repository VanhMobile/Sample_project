package com.example.pnlib_ph35035.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import org.w3c.dom.Text
import java.io.Serializable

@Entity(tableName = "employees")
data class Employee(
    @PrimaryKey
    @NotNull
    @ColumnInfo("idEmployee")
    val idEmployee: String ,

    @ColumnInfo("nameEmployee")
    val name: String,

    @ColumnInfo("officeDuty")
    val officeDuty: String,

    @ColumnInfo("email")
    val email: String,

    @ColumnInfo("password")
    var pass: String,

    @ColumnInfo("imgPathEmp")
    val imgPathEmp: String?,

    @ColumnInfo("status")
    var status: String?,
) :Serializable
