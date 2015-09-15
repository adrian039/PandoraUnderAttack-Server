import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Comparar {
	HiloServidor comunicacion;
	Gson gson = new Gson();
	public Comparar(){
	}
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
				String enviar_mensaje = gson.toJson(o);
				//funcion=enviar_mensaje.toString();
				comunicacion.escribir(enviar_mensaje);
				break;
			}
			else{}
			cont++;
		}
		if (tam==cont){
			JsonObject o = new JsonObject();
			o.addProperty("tipo", String.valueOf("login"));
			o.addProperty("estado", String.valueOf("error"));
			String enviar_mensaje = gson.toJson(o);
			//funcion=enviar_mensaje.toString();
			comunicacion.escribir(enviar_mensaje);
		}
	}

}
