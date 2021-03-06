

import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
/**
 * Esta clase es la encargada de registar a un usuario
 * @author Adrian Sanchez
 * 
 *
 */
public class RegUser{
	Gson gson = new Gson();
	Comparar comparar=new Comparar();
	HiloServidor comunicacion;
	public RegUser(){
	}
	/**
	 * Crea al nuevo usuario
	 * @param socket
	 * @param elemento
	 */
	public void newUser(Socket socket, JsonElement elemento){
		System.out.println(Servidor.listaUsuarios);
		comunicacion=new HiloServidor(socket);
		//respuesta=new Servidor();
		JsonObject agregar=elemento.getAsJsonObject();
		agregar.remove("socket");
		agregar.addProperty("socket", socket.toString());
		String user=agregar.getAsJsonObject().get("nombre").getAsString();
		if(Servidor.listaUsuarios==null){
			Servidor.listaUsuarios.add(elemento);
			System.out.println(Servidor.listaUsuarios.get(0)); 
			JsonObject o = new JsonObject();
			o.addProperty("tipo", String.valueOf("registro"));
			o.addProperty("estado", String.valueOf("completo"));
			String enviar_mensaje = gson.toJson(o);
			//funcion=enviar_mensaje.toString();
			comunicacion.escribir(socket,enviar_mensaje);
			System.out.println(Servidor.listaUsuarios); 
		}
		else if(comparar.UserComp(socket,user)){
			Servidor.listaUsuarios.add(elemento);
			System.out.println(Servidor.listaUsuarios.get(0)); 
			JsonObject o = new JsonObject();
			o.addProperty("tipo", String.valueOf("registro"));
			o.addProperty("estado", String.valueOf("completo"));
			String enviar_mensaje = gson.toJson(o);
			//funcion=enviar_mensaje.toString();
			comunicacion.escribir(socket,enviar_mensaje);
			
			}
		else
		{
			JsonObject comp = new JsonObject();
            comp.addProperty("tipo","registro");
            comp.addProperty("estado","existe");
            String comparar=gson.toJson(comp);
            //funcion=comparar.toString();
            comunicacion.escribir(socket, comparar);
		}
		
	}
	
	
	
}
