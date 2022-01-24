/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author Jesus
 */
public class Servidor{

    private final int PUERTO = 9192;
    private final int CONEXIONES = 4;
    private ServerSocket ss;
    private HiloServer servidor;
    private Comunicacion com;
    private int jugadores;

    public Servidor() {
    }

    public void iniciar() {
        try {
            //Se crea el servidor con un max de 4 jugadores
            ss = new ServerSocket(PUERTO, CONEXIONES);
            System.out.println("Iniciando servidor.");
            System.out.println("Esperando jugadores...");
            com = new Comunicacion();
            jugadores = 0;
            //Bucle infinito para recibir jugadores nuevos
            while (true) {
                //Se acepta al jugador
                Socket socket = ss.accept();
                jugadores++;
                com.setJugadores(jugadores);
                System.out.println("Se conecto: " + socket.getInetAddress());
                //Hilo para el nuevo jugador
                servidor = new HiloServer(socket, com, jugadores);
                Thread hilo = new Thread(servidor);
                hilo.start();
                
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Servidor server = new Servidor();
        server.iniciar();
    }

}
