package com.example.juank.appieustiempo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.UiThread;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by juank on 13/03/2018.
 */

public class ProcessConSendRec extends AsyncTask<Void, Void, Void> implements Runnable{

    private static Socket socket;

    private HandlerThread handlerThreadSender;
    private Handler handlerEnviar;
    private BufferedWriter out;

    private BufferedReader in;
    //para recibir
    private HandlerThread handlerThreadReceiver;
    private Handler handlerRecibir;
    private Handler handlerHiloP;

    public Socket conexion(){
        try {
            socket=new Socket("10.0.2.2",2018);
            Log.w("ESTE ES MI SOCKET ", ""+socket);
            return socket;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        conexion();
        return null;
    }


    public void senderMjs(String mMensaje){
        handlerThreadSender = new HandlerThread("enviarMsj");
        handlerThreadSender.start();

        Looper looper = handlerThreadSender.getLooper();

        handlerEnviar = new Handler(looper){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                try {

                    out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    Log.w ("ESTO ES MI MENSAJE A ENVIAR",String.valueOf(msg.obj.toString()) );

                    out.write(msg.obj.toString()+"\n");

                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        //necesito esto para enviar le mensaje
        Message msg = handlerEnviar.obtainMessage();
        msg.obj = mMensaje;
        handlerEnviar.sendMessage(msg);
        /**XXXXXXXXXXX*****/
    }
    public void recibirMensaje(final Handler handlerHiloPrin){
        handlerHiloP = handlerHiloPrin;
        handlerThreadReceiver = new HandlerThread("recibir");
        handlerThreadReceiver.start();
        /* ademas de hacer referancia el handler del hilo principal, tambian hay que hacerlo con el hiloprincipal*/
        Looper looper = handlerThreadReceiver.getLooper();
        handlerHiloP = new Handler(looper){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String mensajeRec;

                try {

                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while((mensajeRec= in.readLine())!=null){
                        Message mensaje = handlerHiloPrin.obtainMessage();
                        mensaje.obj=mensajeRec;
                        handlerHiloPrin.sendMessage(mensaje);
                        Log.w("MI MENSAJE A ENVIAR A UI :"," " +mensaje.obj.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Log.w("ESTO RECIBE EN EL HANDLER FUERA DEL HILO:", handlerHiloP.obtainMessage().toString());
        /*esto acciona el hilo*/
        Message mensaje=handlerHiloP.obtainMessage();
        handlerHiloP.sendMessage(mensaje);


    }

    @Override
    public void run() {
        //recibirMSJ();
    }
}

