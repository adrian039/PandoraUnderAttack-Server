import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
/**
 * Solicitud del clan
 * @author Adrian Sánchez
 *
 */
public class SolicitudClan extends Servidor{
	HiloServidor comunicacion;
	Gson gson = new Gson();
	public SolicitudClan(){
		
	}
	/**
	 * 
	 * @param elemento
	 * @return
	 */
	public boolean EnviarSolicitud(JsonElement elemento){
		boolean resultado=false;
		System.out.println(Servidor.listaClanes);
		System.out.println(Servidor.listaUsuarios);
		System.out.println(Servidor.listaSockets);
		String clan=elemento.getAsJsonObject().get("clan").getAsString();
		String solicitante=elemento.getAsJsonObject().get("usuario").getAsString();
		for(int i=0; i<Servidor.listaClanes.size();i++){
			String clan1=Servidor.listaClanes.get(i).toString();
			JsonParser parser = new JsonParser();
			JsonElement elemento1 = parser.parse(clan1);
			String Name=elemento1.getAsJsonObject().get("nombre").getAsString();
			if(clan.equals(Name)){
				String creador=elemento1.getAsJsonObject().get("creador").getAsString();
				for(int a=0;a<Servidor.listaUsuarios.size();a++){
					String usuario1=Servidor.listaUsuarios.get(a).toString();
					JsonElement nombre = parser.parse(usuario1);
					String nombreUsuario=nombre.getAsJsonObject().get("nombre").getAsString();
					if(creador.equals(nombreUsuario)){
						String socket1 =nombre.getAsJsonObject().get("socket").getAsString();
						JsonObject o = new JsonObject();
						o.addProperty("tipo", String.valueOf("solicitud"));
						o.addProperty("usuario", String.valueOf(solicitante));
						String enviar_mensaje = gson.toJson(o);
						for(int x=0; x<Servidor.listaSockets.size(); x++){
							String newSocket=Servidor.listaSockets.get(x).toString();
							if(socket1.equals(newSocket)){
								Socket finalSocket=(Socket)Servidor.listaSockets.get(x);
								comunicacion=new HiloServidor(finalSocket);
								comunicacion.escribir(finalSocket,enviar_mensaje);
								System.out.println("Nueva solicitud de union al clan " + Name +" enviada");
								resultado=true;
								break;
							}
							
						}
						//comunicacion.escribir(socket1,enviar_mensaje);
						break;
						
					}
					
				}
				break;
			}
			
		}
		return resultado;
	}
	public void ResponderSolicitud(){
		
	}
}
