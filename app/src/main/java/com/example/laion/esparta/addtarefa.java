package com.example.laion.esparta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class addtarefa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtarefa);

        Button btn = (Button)findViewById(R.id.salvar);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent();
                    EditText editText = (EditText) findViewById(R.id.Tarefa);
                    String tarefa = editText.getText().toString();
                    intent.putExtra("keyName", tarefa);
                    setResult(RESULT_OK, intent);
                    finish();
            }
        });
    }
}
