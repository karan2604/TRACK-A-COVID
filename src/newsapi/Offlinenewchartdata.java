/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package newsapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author asus
 */
public class Offlinenewchartdata {
    
    File filedata=new File("offlinenewchartdata.txt");;
    public void storedata(String data)
    {
        try
      {
        filedata=new File("offlinenewchartdata.txt");
        FileWriter filewriter=new FileWriter(filedata);
        filewriter.write(data);
        filewriter.close();
      
      }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
      
    }
    
    public String readdata()
    {
        String line=null;
        StringBuffer content=new StringBuffer();
      try{
        BufferedReader reader=new BufferedReader(new FileReader(filedata));
        while((line=reader.readLine())!=null)
        {
        content.append(line);
        }
        
            reader.close();
        }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }
       
      line=content.toString();
      
        return line;
        
    }
    
    public boolean checkinternet()
    {
        try {
            final URL url=new URL("https://api.apify.com/v2/datasets/58a4VXwBBF0HtxuQa/items?format=json&clean=1");
            final URLConnection conn=url.openConnection();
            conn.connect();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    
}
