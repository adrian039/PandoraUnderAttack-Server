import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
/**
 * Esta clase permite realizar comparaciones de los datos almacenados en las listas enlazadas
 * @author Adrian Sánchez
 * 
 *
 */
public class Comparar{
	HiloServidor comunicacion;
	Gson gson = new Gson();
	public Comparar(){
	}
	/**
	 * Verifica si el usuario esta repetido
	 * @param socket
	 * @param usuario
	 * @return boolean
	 */
	public boolean UserComp(Socket socket, String usuario){
		//listUsers=new RegUser();
		comunicacion=new HiloServidor(socket);
		if (Servidor.listaUsuarios==null){
			System.out.println("Lista Vacia");
			return true;
		}
		else{
			int tam=Servidor.listaUsuarios.size();
			String resultado="true";
			for(int i=0; i< tam; i++){
				Object user=Servidor.listaUsuarios.get(i);
				String user1=user.toString();
				JsonParser parser = new JsonParser();
				JsonElement elemento = parser.parse(user1);
				String mensaje_in=elemento.getAsJsonObject().get("nombre").getAsString();
				if (mensaje_in.equals(usuario)){
					resultado="false";
					break;
				}
			
			}
			if(resultado.equals("true")){
				return true;
			}
			else{
				return false;
			}
		}
	}
	/**
	 * Verifica si el clan ingresado esta repetido
	 * @param socket
	 * @param nombreClan
	 * @return bolean
	 */
	public boolean ClanComp(Socket socket, String nombreClan){
		comunicacion=new HiloServidor(socket);
		if (Servidor.listaClanes==null){
			System.out.println("Lista Vacia");
			return true;
		}
		else{
			int tam=Servidor.listaClanes.size();
			String resultado="true";
			for(int i=0; i< tam; i++){
				Object clan=Servidor.listaClanes.get(i);
				String clan1=clan.toString();
				JsonParser parser = new JsonParser();
				JsonElement elemento = parser.parse(clan1);
				String mensaje_in=elemento.getAsJsonObject().get("nombre").getAsString();
				if (mensaje_in.equals(nombreClan)){
					resultado="false";
					break;
				}
			
			}
			if(resultado.equals("true")){
				return true;
			}
			else{
				return false;
			}
		}
	}
	
	/**
	 * Valida si el usuario ya existe y si la contraseña es correcta
	 * @param socket
	 * @param dato
	 */
	public void ValUser(Socket socket,JsonElement dato){
		comunicacion=new HiloServidor(socket);
		int tam=Servidor.listaUsuarios.size();
		String userName=dato.getAsJsonObject().get("nombre").getAsString();
		String userPass=dato.getAsJsonObject().get("clave").getAsString();
		int cont=0;
		for(int i=0; i<tam; i++){
			Object objeto = Servidor.listaUsuarios.get(i);
			String datosUser=objeto.toString();
			JsonParser parser = new JsonParser();
			JsonElement elemento = parser.parse(datosUser);
			String Name=elemento.getAsJsonObject().get("nombre").getAsString();
			String Pass=elemento.getAsJsonObject().get("clave").getAsString();
			if(userName.equals(Name) && userPass.equals(Pass)){
				JsonObject o = new JsonObject();
				o.addProperty("tipo", String.valueOf("login"));
				o.addProperty("estado", String.valueOf("completo"));
				if(FindClanUser(Name)){
					o.addProperty("clan", String.valueOf("si"));
				}
				else{
					o.addProperty("clan", String.valueOf("no"));
				}
				String enviar_mensaje = gson.toJson(o);
				//funcion=enviar_mensaje.toString();
				comunicacion.escribir(socket, enviar_mensaje);
				break;
			}
			else{
				
			}
			cont++;
		}
		if (tam==cont){
			JsonObject o = new JsonObject();
			o.addProperty("tipo", String.valueOf("login"));
			o.addProperty("estado", String.valueOf("error"));
			String enviar_mensaje = gson.toJson(o);
			//funcion=enviar_mensaje.toString();
			comunicacion.escribir(socket, enviar_mensaje);
		}
	}
	/**
	 * Valida si el usuario ya pertenece a un clan
	 * @param nombre
	 * @return boolean
	 */
	public boolean FindClanUser(String nombre){
		boolean resultado=false;
		for(int i=0;i<Servidor.listaUsuarios.size();i++){
			String user=Servidor.listaUsuarios.get(i).toString();
			JsonParser parser = new JsonParser();
			JsonElement elemento = parser.parse(user);
			String nombreUser=elemento.getAsJsonObject().get("nombre").getAsString();
			if(nombre.equals(nombreUser)){
				String clan=elemento.getAsJsonObject().get("clan").getAsString();
				if(clan.equals("")){
					resultado=false;
					break;
				}
				else{
					resultado=true;
					break;
				}
			}
			else{}
		}
		return resultado;
	}

}
