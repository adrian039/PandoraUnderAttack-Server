import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLEncoder;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class Cliente {
	JFrame ventana_chat=null;
	JButton btn_enviar=null;
	JTextField txt_mensaje=null;
	JTextArea area_chat=null;
	JPanel contenedor_areachat=null;
	JPanel contenedor_btntxt=null;
	JScrollPane scroll=null;
	Socket socket=null;
	BufferedReader lector=null;
	PrintWriter escritor=null;
	Gson gson = new Gson();
	public static void main(String[] args) {
		new Cliente();
	}
	public Cliente(){
		hacerInterfaz();
	}
	public void hacerInterfaz(){
	ventana_chat=new JFrame("Cliente");
	btn_enviar = new JButton("Enviar");
	txt_mensaje=new JTextField(4);
	area_chat=new JTextArea(10,12);
	scroll= new JScrollPane(area_chat);
	contenedor_areachat=new JPanel();
	contenedor_areachat.setLayout(new GridLayout(1,1));
	contenedor_areachat.add(scroll);
	contenedor_btntxt=new JPanel();
	contenedor_btntxt.setLayout(new GridLayout(1,2));
	contenedor_btntxt.add(txt_mensaje);
	contenedor_btntxt.add(btn_enviar);
	ventana_chat.setLayout(new BorderLayout());;
	ventana_chat.add(contenedor_areachat,BorderLayout.NORTH);
	ventana_chat.add(contenedor_btntxt,BorderLayout.SOUTH);
	ventana_chat.setSize(300, 220);
	ventana_chat.setVisible(true);;
	ventana_chat.setResizable(false);
	ventana_chat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Thread principal =new Thread(new Runnable(){
		public void run(){
			try{
			socket=new Socket("localhost",8080);
			leer();
			escribir();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	});
	principal.start();
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
					String mensaje_in=elemento.getAsJsonObject().get("mensaje").getAsString();
					area_chat.append("Servidor: "+mensaje_in+"\n");
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
	});
		leer_hilo.start();
	}
	public void escribir(){
		Thread escribir_hilo=new Thread(new Runnable(){
			public void run(){
				try{
					escritor= new PrintWriter(socket.getOutputStream(),true);
					btn_enviar.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							JsonObject o =new JsonObject();
							o.addProperty("mensaje", txt_mensaje.getText());
							String enviar_mensaje = gson.toJson(o);
							escritor.println(enviar_mensaje);
							txt_mensaje.setText("");
						}
						
					});
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		escribir_hilo.start();
	}

}
