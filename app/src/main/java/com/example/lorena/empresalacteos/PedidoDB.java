package com.example.lorena.empresalacteos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lorena on 05/11/2017.
 */

public class PedidoDB extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "enterprise.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_PEDIDO = "pedido";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CODIGO = "codigo";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_CANTIDAD = "cantidad";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_PEDIDO + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CODIGO + " TEXT, " +
                    COLUMN_NOMBRE + " TEXT, " +
                    COLUMN_CANTIDAD + " NUMERIC " +
                    ")";

    public PedidoDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PEDIDO);
        db.execSQL(TABLE_CREATE);
    }
}
