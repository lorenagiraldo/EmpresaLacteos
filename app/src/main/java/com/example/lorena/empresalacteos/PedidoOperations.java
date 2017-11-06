package com.example.lorena.empresalacteos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.IntegerRes;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lorena on 05/11/2017.
 */

public class PedidoOperations {
    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            PedidoDB.COLUMN_ID,
            PedidoDB.COLUMN_CODIGO,
            PedidoDB.COLUMN_NOMBRE,
            PedidoDB.COLUMN_CANTIDAD

    };

    public PedidoOperations(Context context){
        dbhandler = new PedidoDB(context);
    }

    public void open(){
        Log.i("ABIERTA","Base de datos abierta");
        database = dbhandler.getWritableDatabase();


    }
    public void close(){
        Log.i("CERRADA", "Base de datos cerrada");
        dbhandler.close();

    }
    public Pedido addPedido(Pedido pedido){
        ContentValues values  = new ContentValues();
        values.put(PedidoDB.COLUMN_CODIGO,pedido.getCodigo());
        values.put(PedidoDB.COLUMN_NOMBRE,pedido.getNombre());
        values.put(PedidoDB.COLUMN_CANTIDAD, pedido.getCantidad());
        long insertid = database.insert(PedidoDB.TABLE_PEDIDO,null,values);
        pedido.setId((int)insertid);
        return pedido;

    }

    // Getting single Pedido
    public Pedido getPedido(long id) {

        Cursor cursor = database.query(PedidoDB.TABLE_PEDIDO,allColumns,PedidoDB.COLUMN_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Pedido e = new Pedido(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2), Integer.parseInt(cursor.getString(3)));
        // return Pedido
        return e;
    }

    public List<Pedido> getAllPedidos() {

        Cursor cursor = database.query(PedidoDB.TABLE_PEDIDO, allColumns, null, null, null, null, null);

        List<Pedido> pedidos = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Pedido Pedido = new Pedido();
                Pedido.setId(cursor.getInt(cursor.getColumnIndex(PedidoDB.COLUMN_ID)));
                Pedido.setCodigo(cursor.getString(cursor.getColumnIndex(PedidoDB.COLUMN_CODIGO)));
                Pedido.setNombre(cursor.getString(cursor.getColumnIndex(PedidoDB.COLUMN_NOMBRE)));
                Pedido.setCantidad(cursor.getInt(cursor.getColumnIndex(PedidoDB.COLUMN_CANTIDAD)));
                pedidos.add(Pedido);
            }
        }
        return pedidos;
    }

    public int updatePedido(Pedido Pedido) {

        ContentValues values = new ContentValues();
        values.put(PedidoDB.COLUMN_CODIGO, Pedido.getCodigo());
        values.put(PedidoDB.COLUMN_NOMBRE, Pedido.getNombre());
        values.put(PedidoDB.COLUMN_CANTIDAD, Pedido.getCantidad());

        // updating row
        return database.update(PedidoDB.TABLE_PEDIDO, values,
                PedidoDB.COLUMN_CODIGO + "=?",new String[] { String.valueOf(Pedido.getCodigo())});
    }

    // Deleting Pedido
    public void removePedido(Pedido Pedido) {
        database.delete(PedidoDB.TABLE_PEDIDO, PedidoDB.COLUMN_CODIGO + "='" + Pedido.getCodigo()+"'", null);
    }
}
