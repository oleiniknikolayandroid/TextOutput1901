package com.example.textoutput_190120.data

import androidx.databinding.BaseObservable

abstract class BaseModel: BaseObservable() {
    abstract fun convertItemForDataSource(): BaseModel
}
