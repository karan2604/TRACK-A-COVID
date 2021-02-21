/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package track.a.cov;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author ALOK RANJAN
 */
public class ReturnedObject 
{
    public String getObject(String api)
    {
             BufferedReader reader;
       String line;
       StringBuffer response= new StringBuffer();
        HttpURLConnection connection = null;
  try  
  {
    URL url = new URL(api);
   connection =(HttpURLConnection) url.openConnection();
   connection.setRequestMethod("GET");
  reader =new BufferedReader(new InputStreamReader(connection.getInputStream()));
   while((line=reader.readLine())!=null)
  {
      response.append(line);
  }
   reader.close();
    
 }
catch(Exception e)
    {
        System.out.println(e);
    }
  finally
  {
      connection.disconnect();
  }
  //toString () it return string value
  return response.toString();
    }
}
