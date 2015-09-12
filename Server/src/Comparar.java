import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Comparar {
	RegUser listUsers;
	public Comparar(){
		
	}
	public boolean UserComp(String usuario){
		listUsers=new RegUser();
		int tam=listUsers.listaUsuarios.size();
		String resultado="true";
		for(int i=0; i<= tam; i++){
			Object user=listUsers.listaUsuarios.get(i);
			JsonParser parser = new JsonParser();
			JsonElement elemento = parser.parse((String) user);
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
