package com.example.pnlib_ph35035.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.pnlib_ph35035.model.Bill
import com.example.pnlib_ph35035.model.Book
import com.example.pnlib_ph35035.model.Customer
import com.example.pnlib_ph35035.model.DetailBill
import com.example.pnlib_ph35035.model.Employee


@Dao
interface PNLibDao {

    //employee
    @Query("SELECT *FROM employees")
    fun getAllEmployees() : List<Employee>

    @Query("SELECT *FROM employees WHERE status = :status")
    fun getEmployees(status: String) : Employee

    @Insert
    fun insertEmployee(employee: Employee)

    @Delete
    fun deleteEmployee(employee: Employee)

    @Update
    fun upDataPassword(employee: Employee)

    @Query("SELECT *FROM employees WHERE (idEmployee = :email OR email = :email) AND password = :password")
    fun checkIdEmployee(email: String, password: String) :Employee

    @Query("UPDATE employees " +
            "SET nameEmployee = :name,idEmployee = :newId,officeDuty = :officeDuty,email = :email  " +
            "WHERE idEmployee = :oidIdEmployee ")
    fun upDataEmployee(oidIdEmployee: String,newId: String,name: String, officeDuty: String,email: String)

    //book
    @Query("SELECT *FROM books")
    fun getAllBook(): List<Book>

    @Query("SELECT *FROM books WHERE category = :category")
    fun getCateBook(category: String) : List<Book>

    @Query("SELECT SUM(quantity) FROM books")
    fun quantityBook() : Int

    @Insert
    fun insertBook(book: Book)

    @Delete
    fun deleteBook(book: Book)

    @Update
    fun upDataBook(book: Book)




    //bill

    @Query("SELECT imgPath AS imgPathBill , nameBook AS nameBookBill" +
            ", nameCustomer AS nameBill, numberPhone AS numberPhoneBill " +
            ",quantityBill AS quantityBill ,price AS priceBill," +
            " idBill AS idBill, status AS status FROM bills " +
            "INNER JOIN books ON idBook = idBookBill " +
            "INNER JOIN customer ON idCustomer = idCustomerBill ")
    fun getAllBill() : List<DetailBill>

    @Query("SELECT SUM(quantityBill) as loansBook FROM bills WHERE idBookBill = :id")
    fun loansBook(id : Int): Int

    @Query("SELECT SUM(quantityBill) as returnBook FROM bills WHERE status = :status AND idBookBill = :idBook")
    fun bookReturn(status : String, idBook: Int): Int

    @Query("SELECT *FROM books INNER JOIN bills ON idBookBill = idBook GROUP BY idBook ORDER BY SUM(quantityBill) DESC")
    fun topTen() :List<Book>

    @Insert
    fun insertBill(bill : Bill)

    @Delete
    fun deleteBill(bill: Bill)

    @Update
    fun upDataStatusBill(bill: Bill)

    @Query("SELECT *FROM bills WHERE idBill = :idBill")
    fun getBill(idBill: String) : Bill

    @Query("SELECT " +
            " SUM(quantityBill * price) AS tong_doanh_so\n" +
            "    FROM bills\n " +
            "INNER JOIN books ON idBook = idBookBill " +
            "WHERE dateBill < date('now', '-30 days')")
    fun sumPrice30Day(): Int

    @Query("SELECT SUM(quantityBill * price) FROM bills INNER JOIN books ON idBookBill = idBook " +
            "WHERE dateBill >= :now AND dateBill < :checkNow")
    fun sumPriceToDay(now: String, checkNow: String): Int
    @Query("SELECT SUM(quantityBill) FROM bills")
    fun sumBooks(): Int
    @Query("SELECT SUM(quantityBill) FROM bills WHERE status = :status")
    fun sumStatus(status: String): Int


    // customer
    @Query("SELECT *FROM customer")
    fun getCustomer() : List<Customer>
    @Insert
    fun insertCustomer(customer: Customer)
    @Delete
    fun deleteCustomer(customer: Customer)
}