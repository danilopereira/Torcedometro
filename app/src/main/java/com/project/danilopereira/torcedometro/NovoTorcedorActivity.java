package com.project.danilopereira.torcedometro;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import dao.ClubeDAO;
import dao.TorcedorDAO;
import model.Clube;
import model.Torcedor;

public class NovoTorcedorActivity extends AppCompatActivity {

    public final static int CODE_NOVO_TORCEDOR = 1002;

    private TextInputLayout tilNomeTorcedor;
    private Spinner spClube;

    private List<Clube> clubes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_torcedor);

        tilNomeTorcedor = (TextInputLayout) findViewById(R.id.tilNomeTorcedor);
        spClube = (Spinner) findViewById(R.id.spClube);

        ClubeDAO clubeDAO = new ClubeDAO(this);

        clubes = clubeDAO.getAll();

        ArrayAdapter<Clube> adapter = new ArrayAdapter<Clube>(getApplicationContext(), R.layout.clube_spinner_item, clubes);

        adapter.setDropDownViewResource(R.layout.clube_spinner_item);

        spClube.setAdapter(adapter);

    }

    public void cadastrar(View v){
        TorcedorDAO torcedorDAO = new TorcedorDAO(this);
        Torcedor torcedor = new Torcedor();
        torcedor.setNome(tilNomeTorcedor.getEditText().getText().toString());
        torcedor.setClube((Clube)spClube.getSelectedItem());

        torcedorDAO.add(torcedor);

        retornaParaTelaAnterior();
    }

    private void retornaParaTelaAnterior() {
        Intent intent = new Intent();
        setResult(CODE_NOVO_TORCEDOR, intent);
        finish();
    }
}
