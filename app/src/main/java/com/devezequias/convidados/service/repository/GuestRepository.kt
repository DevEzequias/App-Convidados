package com.devezequias.convidados.service.repository

import android.annotation.SuppressLint
import android.content.Context
import com.devezequias.convidados.service.model.GuestModel

class GuestRepository (context: Context) {

    //Acesso ao banco de dados
    private val mDataBase = GuestDataBase.getDatabase(context).guestDAO()

    @SuppressLint("Range")
    fun get(id: Int) :GuestModel {
        return mDataBase.get(id)
    }

    fun save(guest: GuestModel): Boolean {
        return mDataBase.save(guest) > 0
    }

    @SuppressLint("Range")
    fun getAll(): List<GuestModel> {
        return mDataBase.getInvited()
    }

    @SuppressLint("Range")
    fun getPresent(): List<GuestModel> {
        return mDataBase.getPresence()
    }

    @SuppressLint("Range")
    fun getAbsent(): List<GuestModel> {
        return mDataBase.getAbsent()
    }


    fun update(guest: GuestModel): Boolean {
        return mDataBase.update(guest) > 0
    }

    fun delete(guest: GuestModel) {
        mDataBase.delete(guest)
    }
}