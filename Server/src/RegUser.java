import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class RegUser {
	Servidor respuesta=null;
	Gson gson = new Gson();
	ListaEnlazada listaUsuarios=null;
	Comparar comparar= null;
	public RegUser(){
		
	}
	public void newUser(JsonElement elemento){
		//respuesta=new Servidor();
		String usuario=elemento.getAsJsonObject().get("nombre").getAsString();
		if(true){
			Gson gson = new Gson();
			listaUsuarios=new ListaEnlazada();
			listaUsuarios.add(elemento);
			System.out.println(listaUsuarios.get(0)); 
			JsonObject o = new JsonObject();
			o.addProperty("tipo", String.valueOf("registro"));
			o.addProperty("estado", String.valueOf("completo"));
			String enviar_mensaje = gson.toJson(o);
			respuesta.escribir(enviar_mensaje);
			
			}
		else
		{
			JsonObject comp = new JsonObject();
            comp.addProperty("tipo","registro");
            comp.addProperty("estado","existe");
            String comparar=gson.toJson(comp);
			respuesta.escribir(comparar);
		}
	}
	
	
	
}
