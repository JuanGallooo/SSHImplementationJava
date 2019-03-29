package modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
 
public class SSHConector {
 /**
  * Hace referencia a dar enter dentro de la maquina, siendo n la jey 
  */
    private static final String ENTER_KEY = "n";
    /**
     * es la sesion con la cual trabajaremos 
     */
    private Session session;
 
    public void connect(String username, String password, String host, int port)
        throws JSchException, IllegalAccessException {
        if (this.session == null || !((Session) this.session).isConnected()) {
            JSch jsch = new JSch();
            this.session =  jsch.getSession(username, host, port);
            ((Session) this.session).setPassword(password);
 
            // Parametro para no validar key de conexion.
            
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            config.put("PreferredAuthentications", "password");
            
            ((Session) this.session).setConfig(config);
            ((Session) this.session).connect();
        } else {
            throw new IllegalAccessException("Sesion SSH ya iniciada.");
        }
    }
 
    public final String executeCommand(String command)
        throws IllegalAccessException, JSchException, IOException {
        if (this.session != null && ((Session) this.session).isConnected()) {
 
            // Abrimos un canal SSH. 
            ChannelExec channelExec = (ChannelExec) ((Session) this.session).
                openChannel("exec");
 
            InputStream in = channelExec.getInputStream();
 
            // Ejecutamos el comando.
            channelExec.setCommand(command);
            channelExec.connect();
 
            // Obtenemos el texto impreso en la consola.
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            String linea;
 
            while ((linea = reader.readLine()) != null) {
                builder.append(linea);
                builder.append(ENTER_KEY);
            }
 
            // Cerramos el canal SSH.
            channelExec.disconnect();
 
            // Retornamos el texto impreso en la consola.
            return builder.toString();
        } else {
            throw new IllegalAccessException("No existe sesion SSH iniciada.");
        }
    }
 
    public final void disconnect() {
    	session.disconnect();
    }
}