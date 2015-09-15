

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class RegUser extends Servidor{
	Gson gson = new Gson();
	Comparar comparar=new Comparar();
	public RegUser(){
	}
	public void newUser(JsonElement elemento){
		System.out.println(Servidor.listaUsuarios);
		//respuesta=new Servidor();
		String user=elemento.getAsJsonObject().get("nombre").getAsString();
		if(Servidor.listaUsuarios==null){
			Servidor.listaUsuarios=new ListaEnlazada();
			Servidor.listaUsuarios.add(elemento);
			System.out.println(Servidor.listaUsuarios.get(0)); 
			JsonObject o = new JsonObject();
			o.addProperty("tipo", String.valueOf("registro"));
			o.addProperty("estado", String.valueOf("completo"));
			String enviar_mensaje = gson.toJson(o);
			funcion=enviar_mensaje.toString();
			escribir();
			System.out.println(Servidor.listaUsuarios); 
		}
		else if(comparar.UserComp(user)){
			Servidor.listaUsuarios.add(elemento);
			System.out.println(Servidor.listaUsuarios.get(0)); 
			JsonObject o = new JsonObject();
			o.addProperty("tipo", String.valueOf("registro"));
			o.addProperty("estado", String.valueOf("completo"));
			String enviar_mensaje = gson.toJson(o);
			funcion=enviar_mensaje.toString();
			escribir();
			
			}
		else
		{
			JsonObject comp = new JsonObject();
            comp.addProperty("tipo","registro");
            comp.addProperty("estado","existe");
            String comparar=gson.toJson(comp);
            funcion=comparar.toString();
            escribir();
		}
		
	}
	
	
	
}
