package com.manik.gymmembership.AppDatabase

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

import java.util.Date

@Entity(tableName = "memberstable")
class Member {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String? = null
    var joindate: Date? = null
    var feeDate: String? = null
    var branch: String? = null
    var mobile: String? = null
    var amount: Int = 0
    var months: Int = 0
    var gender: String? =null



    @Ignore
    constructor(id: Int, name: String?, joindate: Date?, feeDate: String?, branch: String?, mobile: String?, amount: Int, months: Int, gender: String?) {
        this.id = id
        this.name = name
        this.joindate = joindate
        this.feeDate = feeDate
        this.branch = branch
        this.mobile = mobile
        this.amount = amount
        this.months = months
        this.gender = gender
    }

    constructor(name: String?, joindate: Date?, feeDate: String?, branch: String?, mobile: String?, amount: Int, months: Int, gender: String?) {
        this.name = name
        this.joindate = joindate
        this.feeDate = feeDate
        this.branch = branch
        this.mobile = mobile
        this.amount = amount
        this.months = months
        this.gender = gender
    }
}
