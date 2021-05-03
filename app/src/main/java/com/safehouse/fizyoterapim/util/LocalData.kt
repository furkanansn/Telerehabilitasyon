package com.safehouse.fizyoterapim

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import java.lang.reflect.Type
import java.util.*

const val SHARED_PREF = "shrdprf1"
const val ACTIVE_USER = "loggedinuser"
const val ACCOUNT_SESSION_UID = "accountId"
const val SAVED_PASSWORD = "pass"
const val DEVICE_ID = "did"
const val FIRST_TIME = "ftov1"
const val LOGIN_ACTION = "lgnactn"
const val LOGGED_IN_BEFORE = "lgdnbf"
const val BKSTTOKEN = "bksttoken"
const val GLN_LIST_EMPTY = "glnlistempty"

open class LocalData(val context: Context) {


    val default: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(context)
    open val sharedPreferences: SharedPreferences
        get() = context.applicationContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    protected val editor: SharedPreferences.Editor
        get() = sharedPreferences.edit()
    private val firstTimeSharedPreferences: SharedPreferences
        get() = context.applicationContext.getSharedPreferences(FIRST_TIME, Context.MODE_PRIVATE)



    fun writeNow(title: String, content: String?) = editor.putString(title, content).commit()

     fun remove(title: String) = editor.remove(title).apply()



    /**
     * Remove all data associated with the SharedPref key
     */
    fun burnEverything() {
        editor.clear().apply()
        default.edit().clear().apply()
        firstTimeSharedPreferences.edit().clear().apply()
    }

    fun exists(title: String): Boolean = sharedPreferences.contains(title)


    fun read(title: String): String? {
        return sharedPreferences.getString(title, null)
    }


    open fun writeObject(title: String, content: Any) =
            editor.putString(title, Gson().toJson(content)).apply()

    fun readObject(title: String, type: Type): Any? {
        val data = read(title)
        return Gson().fromJson(data, type)
    }




}