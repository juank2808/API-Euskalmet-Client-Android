package com.example.juank.appieustiempo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_layout);
        Intent intent = getIntent();

        final EditText etUsuarioRg = findViewById(R.id.etNombreUserReg);
        final EditText etContraRg = findViewById(R.id.etPasswordReg);
        final ProcessConSendRec send = MainActivity.con;

        Button btnRegistro = findViewById(R.id.btnRegistar);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send.senderMjs("REGISTRO "+"USER "+etUsuarioRg.getText().toString()+" CONTRA "+etContraRg.getText().toString());
                Toast.makeText(getApplicationContext(),"Registro realizado",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
