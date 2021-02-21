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
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author asus
 */
public class Tempflie {
    
    
    File filedata=new File("tempfile.txt");
    //wrting from file 
    public void writedata(String title,String link)
    {
        title=removespacefromtitle(title);
        
        try
        {
        filedata=new File("tempfile.txt");
        FileWriter filewriter=new FileWriter(filedata,true);
        filewriter.write(title);
        filewriter.write("\n");
        filewriter.write(link);
        filewriter.write("\n");
        
        filewriter.close();
      
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    
    //reading from file 
    public String readdata(String title)
    {
        title=removespacefromtitle(title);
       
        String line=null;
        
        int flag=0;
        
      try{
        BufferedReader reader=new BufferedReader(new FileReader(filedata));
        int i=0;
        while((line=reader.readLine())!=null)
        {
           
            if(flag==1)
                return line;
            
            if(title.equals(line))
            {
                flag=1;
            }
            
            
            
        }
        
            reader.close();
        }
        catch(Exception ex)
        {
          ex.printStackTrace();
        }
      
      return line;
        
    }
    
    
    //removing all spaces from string 
    private String removespacefromtitle(String title)
    {
        StringBuffer str=new StringBuffer();
        for(int i=0;i<title.length();i++)
        {
            char ch=title.charAt(i);
            boolean b=Character.isWhitespace(ch);
            if(!b)
                str.append(ch);
        }
        
        return str.toString();
    }
    
    
}
