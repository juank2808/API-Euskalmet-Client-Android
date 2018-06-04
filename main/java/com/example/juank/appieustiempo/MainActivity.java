package com.example.juank.appieustiempo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    public static ProcessConSendRec con = new ProcessConSendRec();

    public static final String EXTRA_MESSAGE = "com.example.RegistroActivity.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        con.execute();
        Button btnSend = findViewById(R.id.btnEntrar);
        final EditText etUser=findViewById(R.id.etNombreUser);
        final EditText etPass=findViewById(R.id.etPassword);
        final TextView registrate = findViewById(R.id.idRegistro);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                con.senderMjs("ACCESO "+"USER "+etUser.getText().toString()+" CONTRA "+etPass.getText().toString());

                Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);

                        Log.w("MainCONSULTA","/ "+msg);
                        String [] mMensaje = msg.obj.toString().split(" ");
                        Log.w("MainCONSULTA","/ "+ Arrays.toString(mMensaje));
                        switch (mMensaje[0]){
                            case "ACCESO:":
                                if(mMensaje[1].equals("UsuarioIncorrecto")){
                                    Toast.makeText(getApplicationContext(),mMensaje[1],Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(),mMensaje[1],Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this,MainConsulta.class));
                                }
                                break;
                        }
                    }
                };

                con.recibirMensaje(handler);

            }
        });
        registrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegistroActivity.class));

            }

        });



    }



}
