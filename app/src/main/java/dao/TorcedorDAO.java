package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import model.Clube;
import model.Torcedor;

/**
 * Created by danilopereira on 02/03/17.
 */

public class TorcedorDAO {
    private SQLiteDatabase db;
    private DBOpenHelper banco;

    public TorcedorDAO(Context context){
        banco = new DBOpenHelper(context);
    }

    private static final String TABELA_TORCEDOR = "torcedor";
    private static final String COLUNA_ID = "id";
    private static final String COLUNA_NOME = "nome";
    private static final String COLUNA_CLUBE_ID = "clube_id";

    public String add(Torcedor torcedor){
        long resultado;
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME, torcedor.getNome());
        values.put(COLUNA_CLUBE_ID, torcedor.getClube().getId());

        resultado = db.insert(TABELA_TORCEDOR, null, values);

        db.close();

        if(resultado == -1){
            return "ERRO ao inserir Registro.";
        }
        else{
            return "Registro Inserido";
        }
    }

    public List<Torcedor> getAll(){
        List<Torcedor> torcedores = new LinkedList<>();

        String rawQuery = "SELECT t.*, c.nome FROM " + TABELA_TORCEDOR +
                " t INNER JOIN "+ ClubeDAO.TABELA_CLUBES
                + " c ON t." + TorcedorDAO.COLUNA_CLUBE_ID + " = c." +
                ClubeDAO.COLUNA_ID +
                " ORDER BY " + TorcedorDAO.COLUNA_NOME + " ASC";
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(rawQuery, null);

        Torcedor torcedor = null;
        if (cursor.moveToFirst()) {
            do {
                torcedor = new Torcedor();
                torcedor.setId(cursor.getInt(0));
                torcedor.setNome(cursor.getString(2));
                torcedor.setClube(new Clube(cursor.getInt(1),
                        cursor.getString(3)));
                torcedores.add(torcedor);
            } while (cursor.moveToNext());
        }
        return torcedores;
    }
}
