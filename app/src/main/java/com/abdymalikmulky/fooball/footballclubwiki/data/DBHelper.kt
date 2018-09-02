package com.abdymalikmulky.fooball.footballclubwiki.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DBHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "footballclub.db", null, 1) {
    companion object {
        private var instance: DBHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DBHelper {
            if (instance == null) {
                instance = DBHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable("TEAM", true,
                "ID_" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "TEAM_ID" to TEXT + UNIQUE,
                "LEAGUE_ID" to TEXT,
                "TEAM_NAME" to TEXT,
                "TEAM_BADGE" to TEXT,
                "IS_FAVORITE" to INTEGER)

        db.createTable("EVENT_FAV", true,
                "ID_" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "EVENT_ID" to TEXT + UNIQUE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable("TEAM", true)
        db.dropTable("EVENT_FAV", true)
    }
}

val Context.database: DBHelper
    get() = DBHelper.getInstance(applicationContext)
