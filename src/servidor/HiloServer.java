/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 *
 * @author Jesus
 */
public class HiloServer implements Runnable, Observer {
    
    private DataOutputStream salida;
    private DataInputStream entrada;
    private Socket cliente;
    private Comunicacion com;
    private boolean running = true;
    private int listos = 0;
    private String mensajeCliente;
    private Constantes con;
    private int id;
    
    public HiloServer(Socket cliente, Comunicacion com, int id) {
        this.cliente = cliente;
        this.com = com;
        this.id = id;
        con = new Constantes();
    }
    
    @Override
    public void run() {
        try {
            entrada = new DataInputStream(cliente.getInputStream());
            salida = new DataOutputStream(cliente.getOutputStream());
            com.addObserver(this);
            //Envia id
            String enviaID = "ID;" + String.valueOf(id);
            salida.writeUTF(enviaID);
            //Ciclo para recibir los mensajes del cliente
            while (running) {
                mensajeCliente = entrada.readUTF();
                mensaje(mensajeCliente);
            }
        } catch (IOException ex) {
            running = false;
            System.out.println("Un jugador se ha desconectado.");
        }
    }
    
    public void mensaje(String mensaje) {
        //Procesa el mensaje recibido
        //accion;
        try {
            String split[] = mensaje.split(";");
            if (split[0].equals(con.LISTO)) {
                com.setJugadoresListos(com.getJugadoresListos() + 1);
                listos = com.getJugadoresListos();
                System.out.println(listos);
                if (listos == 2) {
                    mensajeCliente = con.JUGAR;
                    com.setMensaje(mensajeCliente);
                } else {
                    mensajeCliente = con.ESPERA;
                    com.setMensaje(mensajeCliente);
                }
            } else if (split[0].equals("3")) {
                String reparte = com.repartirFichas(id);
                //4;listafichasJugador1;idJugador
                mensajeCliente = "4" + reparte + ";" + id;
                salida.writeUTF(mensajeCliente);
            } else if (split[0].equals("4")) {
                System.out.println("Entro a 4: poner ficha");
                
                System.out.println("Si se manda mensaje");
                if (split[1].equals("1")) {
                    mensajeCliente = "5" + ";" + "2" + ";" + split[2] + ";" + split[3] + ";" + split[4];
                } else {
                    mensajeCliente = "5" + ";" + "1" + ";" + split[2] + ";" + split[3] + ";" + split[4];
                }                
                com.setMensaje(mensajeCliente);
                
            } else if (split[0].equals("6")) {
                if (Integer.valueOf(split[1]) == 1) {
                    mensajeCliente = "6;" + "2";
                } else {
                    mensajeCliente = "6;" + "1";
                }
                com.setMensaje(mensajeCliente);
            }
            
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
        try {
            String update = (String) arg;
            salida.writeUTF(update);
        } catch (IOException e) {
        }
    }
    
}
