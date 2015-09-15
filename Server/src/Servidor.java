
import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.util.concurrent.Semaphore;
import java.io.*;
import java.awt.event.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class Servidor {
	ServerSocket servidor=null;
	String funcion;
	public static ListaEnlazada listaUsuarios;
	public static ListaEnlazada listaSockets;
	BufferedReader lector=null;
	RegUser registrar=null;
	PrintWriter escritor=null;
	Gson gson = new Gson();
	public Servidor(){
		
	}
	public static void main(String[] args) {
		try{
		Servidor server=new Servidor();
		ServerSocket servidor=new ServerSocket(8080);
		while(true){
			try{
				Socket socket=servidor.accept();
				new HiloServidor(socket).start();
				System.out.println("Nuevo cliente conectado: "+String.valueOf(socket));
				listaSockets.add(socket);
				}
				catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	}
	
}
	
