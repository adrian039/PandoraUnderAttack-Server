import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HiloServidor extends Thread{
	protected Socket socket;
    protected Servidor server;
    BufferedReader lector=null;
    PrintWriter escritor=null;
	Gson gson = new Gson();
    
    public HiloServidor(Socket socketCliente){
    	
    	this.socket=socketCliente;
    	leer();
    }
    public void leer(){
		Thread leer_hilo=new Thread(new Runnable(){
		public void run(){
			try{
				lector=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while(true){
					JsonParser parser = new JsonParser();
					String mensaje= lector.readLine();
					JsonElement elemento = parser.parse(mensaje);
					String mensaje_in=elemento.getAsJsonObject().get("tipo").getAsString();
					if (lector==null){
						System.out.println("Conexion Interrumpida....");
					}
					if (mensaje_in.equals("registrar")){
						RegUser registrar=new RegUser();
						System.out.println("Solicitud de Registro de Usuario : "+socket.toString());
						registrar.newUser(socket,elemento);
					}
					else if (mensaje_in.equals("ingresar")){
						System.out.println("Solicitud de Ingreso de Usuario: "+socket.toString());
						Comparar valUser=new Comparar();
						valUser.ValUser(socket,elemento);
					}
					else if (mensaje_in.equals("registrarClan")){
						RegClan registrar=new RegClan();
						System.out.println("Solicitud de Registro de Clan de: "+socket.toString());
						registrar.newClan(socket,elemento);
					}
					else if(mensaje_in.equals("buscarClan")){
						Comparar buscarClan= new Comparar();
						String clan=elemento.getAsJsonObject().get("clan").getAsString();
						JsonObject o = new JsonObject();
						if(buscarClan.ClanComp(socket, clan)){
							o.addProperty("tipo", String.valueOf("RespSolicitud"));
							o.addProperty("estado", String.valueOf("error"));
							String enviar_mensaje = gson.toJson(o);
							escribir(socket,enviar_mensaje);
						}
						else{
							SolicitudClan enviar= new SolicitudClan();
							enviar.EnviarSolicitud(socket, elemento);
							o.addProperty("tipo", String.valueOf("RespSolicitud"));
							o.addProperty("estado", String.valueOf("enviada"));
							String enviar_mensaje=gson.toJson(o);
							escribir(socket,enviar_mensaje);
						}
					}
					
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
	});
		leer_hilo.start();
	}
	public void escribir(final Socket socket, final String dato){
		Thread escribir_hilo=new Thread(new Runnable(){
			public void run(){
					try{
						if(dato!=null){
						escritor= new PrintWriter(socket.getOutputStream(),true);
						escritor.println(dato);
						System.out.println(dato);
						
						}
						else{}
					}catch(Exception ex){
						ex.printStackTrace();
					}
				
				
			}
		});
		escribir_hilo.start();
	}
}
