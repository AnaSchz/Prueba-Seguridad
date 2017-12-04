/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package x;

import Util.Hilo;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ana
 */
public class CAcceso extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse resp)
            throws ServletException, IOException {
        String usu = "marbellaca";
        InetAddress address = InetAddress.getLocalHost();
        String ip = address.getHostAddress();
        String mensaje = ip + "&" + request.getParameter("Servicio") + "&" + usu + "&";
        //request.getParameter("Servicio") trae el nombre del servicio al que quiere acceder el usuario
        // Aquí se obtienen los datos del usuario que se enviarán al servidor de autenticacion
        String mensaje2 = ip + "&" + request.getParameter("Servicio") + "&";
        String respuesta = "", respuesta2="";
        try{
            //Cambiar las address dependiendo de la máquina donde esté cada servidor
        //Mensaje al servidor de autenticación
        respuesta = mensaje(mensaje, "192.168.1.72",5000);
 
        //Mensaje al servidor de tickets
        respuesta2 = mensaje(mensaje2, "192.168.1.72",3000);
        }
        catch (Exception e){
        }   
        
        if(respuesta.equals("Error")){
            resp.sendRedirect("http://localhost:8080/VenusProject/Plantillas/Error.html");
            //Enivarle un alert que diga "Ha ocurrido un error, por favor vuelve a iniciar sesion"
            //Direccionarlo al inicio de sesion y cerrar la sesion
        }
        else{
            if(respuesta2.equals("Error")){}
            else{
                String servicio = "";
                for(int i=0; i<respuesta.length(); i++){
                    if(respuesta.charAt(i)=='&'){
                        servicio = respuesta.substring(i+1);
                    }
                }
                switch(servicio){
                    case "CambiarD": resp.sendRedirect("http://localhost:8080/VenusProject/Plantillas/CambiarD.html");
                    //Envía al módulo de cambiar datos
                        break;
                    default:
                        break;
                }
            }
        }
    }
    
    private String mensaje(String mensaje, String address, int puerto) throws Exception{
        Hilo hilo = new Hilo(mensaje,address,puerto);
        Thread h = new Thread(hilo);
        h.start();
        String msj="";
        msj = hilo.parar();
        return msj;
    }
}
