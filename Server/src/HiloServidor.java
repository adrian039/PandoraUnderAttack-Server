import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
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
						System.out.println("Solicitud de Registro");
						registrar.newUser(socket,elemento);
					}
					else if (mensaje_in.equals("ingresar")){
						System.out.println("Solicitud de Ingreso");
						Comparar valUser=new Comparar();
						valUser.ValUser(socket,elemento);
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
