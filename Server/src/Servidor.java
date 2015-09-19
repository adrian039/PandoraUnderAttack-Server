
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;

/**
 * Clase servidor (Main)
 * @author Adrian Sánchez
 *
 */
public class Servidor {
	ServerSocket servidor=null;
	String funcion;
	public static ListaEnlazada listaUsuarios= new ListaEnlazada();
	public static ListaEnlazada listaSockets= new ListaEnlazada();
	public static ListaEnlazada listaClanes= new ListaEnlazada();
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
		System.out.println("Servidor iniciado");
		while(true){
			try{
				Socket socket=servidor.accept();
				new HiloServidor(socket).start();
				System.out.println("Nuevo cliente conectado: "+String.valueOf(socket));
				AgregarSocket(socket);
				}
				catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	}
	/**
	 * Agrega a un nuevo socket
	 * @param socket1
	 */
	public static void AgregarSocket(Socket socket1){
		boolean result=false;
		if(Servidor.listaSockets!=null){
			for(int s=0; s<Servidor.listaSockets.size();s++){
				if(Servidor.listaSockets.get(s).equals(socket1)){
					result=true;
					break;
				}
				else{}
			}
			if(result==false){
				Servidor.listaSockets.add(socket1);
			}
			else{}
		}
		else{
			Servidor.listaSockets.add(socket1);
		}
	}
	
}
	
