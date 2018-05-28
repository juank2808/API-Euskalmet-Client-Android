package com.example.juank.appieustiempo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class MainConsulta extends AppCompatActivity {

    String LOCALIDAD;
    final ProcessConSendRec con = MainActivity.con;
    TextView txtHora;
    final String HORA = String.valueOf(LocalDateTime.now().getHour())+" : "+String.valueOf(LocalDateTime.now().getMinute());
    TextView txtVientoMedio;
    TextView txtVientoMax;
    TextView txtDireccion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consulta_layout);
        Intent intent = getIntent();
        Toolbar mToolbar =  findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        Button btnrefresh = findViewById(R.id.btnRefresh);
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                Log.w("MainCONSULTA","/ "+msg);
                txtVientoMax = findViewById(R.id.txtVientoMax);
                txtDireccion = findViewById(R.id.txtDireccion);
                txtVientoMedio= findViewById(R.id.txtVientoMedio2);
                String [] mMensaje = msg.obj.toString().split(" ");
                Log.w("MainCONSULTA","/ "+ Arrays.toString(mMensaje));
                switch (mMensaje[0]){
                    case "RESPUESTA:":
                        //Toast.makeText(getApplicationContext(),mMensaje[63],Toast.LENGTH_SHORT).show();
                        Log.w("HOLA ESTOY EN EL CASE","/ "+ mMensaje[63]);
                        txtVientoMedio.setText(mMensaje[61]);
                        txtVientoMax.setText(mMensaje[63]);
                        txtDireccion.setText(mMensaje[62]);
                        break;
                }
                Log.w("MainCONSULTA","/ "+ mMensaje[1]);

            }
        };

        con.recibirMensaje(handler);
        txtHora= findViewById(R.id.txtHora);
        btnrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (LOCALIDAD){
                    case "Irún":
                        Toast.makeText(getApplicationContext(),"REFRESH "+LOCALIDAD,Toast.LENGTH_SHORT).show();
                        con.senderMjs("CONSULTA "+"C064");
                        txtHora.setText(HORA);
                        ;break;
                    case "Jaizkibel":
                        Toast.makeText(getApplicationContext(),"REFRESH "+LOCALIDAD,Toast.LENGTH_SHORT).show();
                        con.senderMjs("CONSULTA "+"C071");
                        txtHora.setText(HORA);
                        ;break;
                    case "Higuer":
                        Toast.makeText(getApplicationContext(),"REFRESH "+LOCALIDAD,Toast.LENGTH_SHORT).show();
                        con.senderMjs("CONSULTA "+"C018");
                        txtHora.setText(HORA);
                        ;break;
                    case "Zarautz":
                        Toast.makeText(getApplicationContext(),"REFRESH "+LOCALIDAD,Toast.LENGTH_SHORT).show();
                        con.senderMjs("CONSULTA "+"C064");
                        txtHora.setText(HORA);
                        ;break;
                }
            }
        });
        recibir();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TextView txtLocalidad = findViewById(R.id.txtLocalidad);

        switch(item.getItemId()){
            case R.id.consulta1:
                Toast.makeText(getApplicationContext(),""+item.toString(),Toast.LENGTH_SHORT).show();
                txtLocalidad.setText(item.toString());
                LOCALIDAD = item.toString();
                con.senderMjs("CONSULTA "+"C064");

                //Toast.makeText(getApplicationContext(),"HORA "+hora,Toast.LENGTH_SHORT).show();
                txtHora.setText(HORA);
                break;
            case R.id.consulta2:
                Toast.makeText(getApplicationContext(),""+item.toString(),Toast.LENGTH_SHORT).show();
                txtLocalidad.setText(item.toString());
                LOCALIDAD = item.toString();
                con.senderMjs("CONSULTA "+"C071");
                txtHora.setText(HORA);

                //Toast.makeText(getApplicationContext(),"HORA "+hora,Toast.LENGTH_SHORT).show();
                break;
            case R.id.consulta3:
                Toast.makeText(getApplicationContext(),""+item.toString(),Toast.LENGTH_SHORT).show();
                txtLocalidad.setText(item.toString());
                LOCALIDAD = item.toString();
                con.senderMjs("CONSULTA "+"C018");
                txtHora.setText(HORA);
                //Toast.makeText(getApplicationContext(),"HORA "+fecha3,Toast.LENGTH_SHORT).show();
                break;
            case R.id.consulta4:
                Toast.makeText(getApplicationContext(),""+item.toString(),Toast.LENGTH_SHORT).show();
                txtLocalidad.setText(item.toString());
                LOCALIDAD = item.toString();
                con.senderMjs("CONSULTA "+"C064");
                txtHora.setText(HORA);
                //Toast.makeText(getApplicationContext(),"HORA "+hora,Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void recibir(){
        new Thread(new ProcessConSendRec()).start();
    }
}
