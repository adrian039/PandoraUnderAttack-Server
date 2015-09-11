
import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class Servidor {
	ServerSocket servidor=null;
	Socket socket=null;
	BufferedReader lector=null;
	PrintWriter escritor=null;
	Gson gson = new Gson();
	public Servidor(){
		iniciarHilo();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Servidor();
		RegUser registrar=new RegUser();
	}
	public void iniciarHilo(){
	
	Thread principal=new Thread(new Runnable(){
		public void run(){
			try{
			servidor=new ServerSocket(8080);
			while(true){
				socket=servidor.accept();
				leer();
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
				RegUser registrar=new RegUser();
				lector=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while(true){
					JsonParser parser = new JsonParser();
					String mensaje= lector.readLine();
					JsonElement elemento = parser.parse(mensaje);
					String mensaje_in=elemento.getAsJsonObject().get("tipo").getAsString();
					if (mensaje_in.equals("registrar")){
						System.out.println("Solicitud de Registro");
						registrar.newUser(elemento);
					}
					else if (mensaje_in.equals("ingresar")){
						System.out.println("Solicitud de Ingreso");
					}
					
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
	});
		leer_hilo.start();
	}
	public void escribir(String dato){
		try{
			escritor= new PrintWriter(socket.getOutputStream(),true);
			escritor.println(dato);
			}
		catch(Exception ex){
			ex.printStackTrace();
		}
			
		
	}
	
	

}