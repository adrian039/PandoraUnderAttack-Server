
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
	public static Socket socket;
	BufferedReader lector=null;
	RegUser registrar=null;
	PrintWriter escritor=null;
	Gson gson = new Gson();
	public Servidor(){
		
	}
	public static void main(String[] args) {
		Servidor server=new Servidor();
		server.iniciarHilo();
	}
	public void iniciarHilo(){
		
	Thread principal=new Thread(new Runnable(){
		public void run(){
			try{
			servidor=new ServerSocket(8080);
			while(true){
				socket=servidor.accept();
				leer();
				escribir();
			}
			
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	});
	principal.start();
	System.out.println("Servidor iniciado......");
	}
	public void leer(){
		Thread leer_hilo=new Thread(new Runnable(){
		public void run(){
			try{
				lector=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while(true){
					JsonParser parser = new JsonParser();
					String mensaje= lector.readLine();
					JsonElement elemento = parser.parse(mensaje);
					String mensaje_in=elemento.getAsJsonObject().get("tipo").getAsString();
					if (lector==null){
						System.out.println("Conexion Interrumpida....");
					}
					if (mensaje_in.equals("registrar")){
						RegUser registrar=new RegUser();
						System.out.println("Solicitud de Registro");
						registrar.newUser(elemento);
					}
					else if (mensaje_in.equals("ingresar")){
						System.out.println("Solicitud de Ingreso");
						Comparar valUser=new Comparar();
						valUser.ValUser(elemento);
					}
					
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
	});
		leer_hilo.start();
	}
	public void escribir(){
		Thread escribir_hilo=new Thread(new Runnable(){
			public void run(){
					try{
						if(funcion!=null){
						escritor= new PrintWriter(socket.getOutputStream(),true);
						escritor.println(funcion);
						System.out.println(funcion);
						
						}
						else{}
					}catch(Exception ex){
						ex.printStackTrace();
					}
				
				
			}
		});
		escribir_hilo.start();
	}
}
	
