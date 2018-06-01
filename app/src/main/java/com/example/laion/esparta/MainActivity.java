package com.example.laion.esparta;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;

    ArrayList estrutura_lista;
    ListView listView;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.listView);

        estrutura_lista = new ArrayList();

        FloatingActionButton addTask = findViewById(R.id.addTask);  //botao para adicionar nova tarefa

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {     //quando apertar botao para adicionar nova tarefa vai abrir outra atividade
                Intent intent = new Intent(MainActivity.this, addtarefa.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
            }
        });

        /* quando apertar em um item da lista ele marca ou desmarca o checkbox, que significa que completou aquela tarefa ou nao */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Estrutura_lista dataModel= (Estrutura_lista) estrutura_lista.get(position);  //pega posicao do item na lista
                dataModel.checked = !dataModel.checked;                                      //se estiver marcado desmarca e viceversa
                adapter.notifyDataSetChanged();                                              //avisa adaptador das mudanças
            }
        });
        // em um longo click seleciona o item da lista
        listView.setOnItemLongClickListener (new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, final View view, final int position, long id) {
                for (int j = 0; j < parent.getChildCount(); j++)                      //para todos os items o fundo fica transparente
                    parent.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
                // muda cor de fundo do item para cinza
                view.setBackgroundColor(Color.LTGRAY);

                final Button button = findViewById(R.id.deletar);
                button.setVisibility(View.VISIBLE);                 //deixa visivel botao para excluir o item
                //se clicar em excluir vai aparecer uma mensagem para confirmar a exclusao ou cancelar
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        showSimplePopUp(position);
                        button.setVisibility(View.GONE);
                        view.setBackgroundColor(Color.TRANSPARENT);
                    }
                });
                return true; //faz só o click longo sem o click normal
            }
        });
    }
    //faz aparecer menu na barra de navegação
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Estrutura_lista item_lista ;
        ArrayList lista_pendente;
        ArrayList lista_finalizado;
        CustomAdapter adapter1,adapter2;
        switch (item.getItemId()) {
            case R.id.pendentes:
                lista_pendente = new ArrayList();
                for(int i = 0; i < adapter.getCount(); i++){          //for para todos os itens da lista
                    item_lista = (Estrutura_lista) estrutura_lista.get(i);
                    //se nao estiver marcado entao é uma atividade pendente e eh adicionado na lista de atividades pendentes
                    if(!item_lista.checked){
                        lista_pendente.add(new Estrutura_lista(item_lista.tarefa, item_lista.checked));
                    }
                }
                adapter1 = new CustomAdapter(lista_pendente, getApplicationContext());
                listView.setAdapter(adapter1);  //faz aparecer na tela lista só com as atividades pendentes
                listView.setEnabled(false);
                return true;
            case R.id.finalizadas:
                lista_finalizado = new ArrayList();
                for(int i = 0; i < adapter.getCount(); i++){           //for para todos os itens da lista
                    item_lista = (Estrutura_lista) estrutura_lista.get(i);
                    //se estiver marcado entao é uma atividade finalizada e eh adicionado na lista de atividades finalizadas
                    if(item_lista.checked){
                        lista_finalizado.add(new Estrutura_lista(item_lista.tarefa, item_lista.checked));
                    }
                }
                adapter2 = new CustomAdapter(lista_finalizado, getApplicationContext());
                listView.setAdapter(adapter2);  //faz aparecer na tela lista só com as atividades finalizadas
                listView.setEnabled(false);
                return true;
            case R.id.todas:
                adapter = new CustomAdapter(estrutura_lista, getApplicationContext());   //pega a lista normal com todas as atividades
                listView.setAdapter(adapter);
                listView.setEnabled(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // metodo chamado quando a segunda atividade termina
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // pega a string do intent
                String titulo = data.getStringExtra("keyName");

                estrutura_lista.add(new Estrutura_lista(titulo, false));

                adapter = new CustomAdapter(estrutura_lista, getApplicationContext());
                listView.setAdapter(adapter);

            }
        }
    }

    private void showSimplePopUp(final int position) {
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setCancelable(true);
        helpBuilder.setTitle("EXCLUIR:");
        helpBuilder.setMessage("Depois que exluir não há como voltar: ");
        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.remove(estrutura_lista.get(position));  //deleta item da lista
                        adapter.notifyDataSetChanged();
                    }
                });
        helpBuilder.setNegativeButton(android.R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //nao faz nada
                    }
                });
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }
}
