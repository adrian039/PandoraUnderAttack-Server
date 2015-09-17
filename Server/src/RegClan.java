import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RegClan {
	Gson gson = new Gson();
	Comparar comparar=new Comparar();
	HiloServidor comunicacion;
	public RegClan(){
		
	}
	public void newClan(Socket socket, JsonElement elemento){
		System.out.println(Servidor.listaClanes);
		comunicacion=new HiloServidor(socket);
		//respuesta=new Servidor();
		String clan=elemento.getAsJsonObject().get("nombre").getAsString();
		String creador=elemento.getAsJsonObject().get("creador").getAsString();
		if(Servidor.listaClanes==null){
			Servidor.listaClanes.add(elemento);
			System.out.println(Servidor.listaClanes.get(0)); 
			findUser(creador,clan);
			JsonObject o = new JsonObject();
			o.addProperty("tipo", String.valueOf("registroClan"));
			o.addProperty("estado", String.valueOf("completo"));
			String enviar_mensaje = gson.toJson(o);
			//funcion=enviar_mensaje.toString();
			comunicacion.escribir(socket,enviar_mensaje);
			System.out.println(Servidor.listaClanes); 
		}
		else if(comparar.ClanComp(socket,clan)){
			Servidor.listaClanes.add(elemento);
			System.out.println(Servidor.listaClanes.get(0)); 
			findUser(creador,clan);
			JsonObject o = new JsonObject();
			o.addProperty("tipo", String.valueOf("registroClan"));
			o.addProperty("estado", String.valueOf("completo"));
			String enviar_mensaje = gson.toJson(o);
			//funcion=enviar_mensaje.toString();
			comunicacion.escribir(socket,enviar_mensaje);
			
			}
		else
		{
			JsonObject comp = new JsonObject();
            comp.addProperty("tipo","registroClan");
            comp.addProperty("estado","existe");
            String comparar=gson.toJson(comp);
            //funcion=comparar.toString();
            comunicacion.escribir(socket, comparar);
		}
		
	}
	public void findUser(String nombre, String clan){
		System.out.println(Servidor.listaUsuarios.get(0));
		for(int i=0;i<Servidor.listaUsuarios.size();i++){
			String usuario=Servidor.listaUsuarios.get(i).toString();
			JsonParser parser = new JsonParser();
			JsonElement elemento = parser.parse(usuario);
			String Name=elemento.getAsJsonObject().get("nombre").getAsString();
			if(nombre.equals(Name)){
				Servidor.listaUsuarios.remove(i);
				JsonObject user=elemento.getAsJsonObject();
				user.remove("clan");
				user.addProperty("clan",clan );
				String user2=user.toString();
				JsonParser parser1 = new JsonParser();
				JsonElement user1=parser1.parse(user2);
				Servidor.listaUsuarios.add(user1);
				System.out.println(Servidor.listaUsuarios.get(Servidor.listaUsuarios.size()-1));
				break;
			}
		}
	}
}
