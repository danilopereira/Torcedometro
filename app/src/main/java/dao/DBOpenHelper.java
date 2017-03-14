package dao;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.project.danilopereira.torcedometro.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by danilopereira on 02/03/17.
 */

public class DBOpenHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "torcedometro.db";
    private static final int VERSAO_BANCO = 1;
    private Context ctx;

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSAO_BANCO);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // cria a estrutura do banco
        
        lerEExecutarSQLScript(db, ctx, R.raw.db_criar);
        // Insere os dados iniciais
        lerEExecutarSQLScript(db, ctx, R.raw.insere_dados_iniciais);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    private void lerEExecutarSQLScript(SQLiteDatabase db, Context ctx, int db_criar) {
        Resources res = ctx.getResources();
        try {
            InputStream is = res.openRawResource(db_criar);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            executarSQLScript(db, reader);
            reader.close();
            is.close();
        }catch (IOException e){
            throw new RuntimeException("Não foi possível ver o arquivo SQLite.", e);
        }
    }

    private void executarSQLScript(SQLiteDatabase db, BufferedReader reader) throws IOException {
        String line;
        StringBuilder statement = new StringBuilder();
        while((line = reader.readLine()) != null){
            statement.append(line);
            statement.append("\n");
            if(line.endsWith(";")){
                String toExec = statement.toString();
                log("Executing Script " + toExec);
                db.execSQL(toExec);
                statement = new StringBuilder();
            }
        }
    }

    private void log(String msg) {
        Log.d(DBOpenHelper.class.getSimpleName(), msg);
    }

}
