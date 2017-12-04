
package Util;

import java.io.*;
import java.net.*;

/**
 *
 * @author Ana San
 */
public class Hilo implements Runnable{
    
    Socket socket;
    OutputStream flujoS;
    PrintWriter writer;
    String mensaje;
    InputStream flujoE;
    BufferedReader reader;

    public Hilo(String mensaje, String servidor, int puerto) throws IOException{
            socket = new Socket(servidor, puerto);
            flujoS = socket.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(flujoS));
            this.mensaje = mensaje;
            flujoE = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(flujoE));
    }

    public void run(){
        try{
            writer.println(mensaje);
            writer.flush();
        }
        catch(Exception e){}
    }

    public String parar() throws IOException{
        String respuesta = "";
        respuesta = reader.readLine();
        socket.close();
        return respuesta;
    }

}
