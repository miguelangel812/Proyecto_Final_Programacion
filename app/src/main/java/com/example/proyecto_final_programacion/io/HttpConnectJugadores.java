package com.example.proyecto_final_programacion.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnectJugadores {

    /*Url principal de mi api*/
    private static String URL_BASE = "https://api.imgflip.com";

    public static String getRequest(String strUrl )
    {
        HttpURLConnection http = null;
        String content = null;
        try {
            /* Se forma la url más el endpoint */
            URL url = new URL( URL_BASE + strUrl );
            http = (HttpURLConnection)url.openConnection();
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");

            /*EN caso de que este bien el servidor devuelve HTTP_OK*/
            if( http.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader( http.getInputStream() ));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                content = sb.toString();
                reader.close();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            /*Fin de la conexión*/
            if( http != null ) http.disconnect();
        }
        return content;
    }

}
