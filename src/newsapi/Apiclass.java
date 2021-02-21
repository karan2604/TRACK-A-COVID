/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package newsapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author asus
 */
public class Apiclass {
    
    Offlinenewchartdata offlinenewchartdata=new Offlinenewchartdata();
    
    ArrayList<Covidapi> articlelist=new ArrayList<Covidapi>();
    
        HttpURLConnection connection = null;
        BufferedReader reader;
        String line;
        StringBuffer content=new StringBuffer();
        
        

    public Apiclass() {
            boolean flag=offlinenewchartdata.checkinternet();
            if(flag)
            {
        try {
            URL url = new URL("https://api.covidtracking.com/v1/states/info.json");
            connection=(HttpURLConnection) url.openConnection();
            
            
             connection.setRequestMethod("GET");
             connection.setConnectTimeout(5000);
             connection.setReadTimeout(5000);
                    
             int status=connection.getResponseCode();
             
             if(status>299)
            {
            
                reader=new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line=reader.readLine())!=null){
                content.append(line);
            }
            reader.close();
        
            }else{
                reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line=reader.readLine())!=null){
                content.append(line);
            }
            reader.close();
         }
             
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally
        {
            connection.disconnect();
        }   
            }
    }
    
    ArrayList<Covidapi> jsondata()
    {
        JSONArray jsonarray;
        if(content.length()!=0)
        {
            jsonarray=new JSONArray(content.toString());
            offlinenewchartdata.storedata(content.toString());
        }
        else
        {
            String data=offlinenewchartdata.readdata();
            jsonarray=new JSONArray(data);
        }
            
        
        for(int i=0;i<jsonarray.length();i++)
        {
            JSONObject jsonobj=jsonarray.getJSONObject(i);
            articlelist.add(new Covidapi(jsonobj.get("notes").toString(),jsonobj.get("covid19Site").toString()));
        }
        
        return articlelist;
    }
    
    
    
    
    
        
        
}
        
    
