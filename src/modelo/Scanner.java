package modelo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.jcraft.jsch.JSchException;

public class Scanner {
	public static void main(String[] args) {
		
		//for (int i = 0; i < 100; i++) {
			//try {
				//if(available(i)) {
			        try {
			        	
			        	// el escaner se preoparó principalmente para mirar los puertos del 1-100
			        	// Al tener el puerto ya seleccionado en clase vendria a ser el puerto 22, por lo cual se hace la conexion.
			        	
			            SSHConector sshConnector = new SSHConector();
			            sshConnector.connect("msfadmin", "msfadmin", "192.168.149.129",22 );
			            String result = sshConnector.executeCommand("mkdir hackedpro");
			            sshConnector.disconnect();
			            System.out.println(result);
			        } catch (JSchException ex) {
			            ex.printStackTrace();
			            System.out.println(ex.getMessage());
			        } catch (IllegalAccessException ex) {
			            ex.printStackTrace();
			            System.out.println(ex.getMessage());
			        } catch (IOException ex) {
			            ex.printStackTrace();
			            System.out.println(ex.getMessage());
			        }
				//}
			//} catch (Exception e) {
				//System.out.println("Error en el puerto");
			//}
		//}
	}
	private static boolean available(int port) {
	    System.out.println("--------------Testing port " + port);
	    Socket s = null;
	    try {
	        s = new Socket();
	        s.connect(new InetSocketAddress("192.168.17.129", port),5);
	        System.out.println("--------------Port " + port + " no esta disponible");
	        return false;
	    } catch (IOException e) {
	        System.out.println("--------------Port " + port + " esta disponible");
	        return true;
	    } finally {
	        if( s != null){
	            try {
	                s.close();
	            } catch (IOException e) {
	                throw new RuntimeException("Error runtime" , e);
	            }
	        }
	    }
	}

}
