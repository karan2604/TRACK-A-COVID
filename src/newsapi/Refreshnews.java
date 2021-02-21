/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package newsapi;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author asus
 */
public class Refreshnews {

    public Refreshnews() {
        /*
        timerclass();
                */
    }
    
    
    public void timerclass()
    {
         Thread thread=new Thread(){   //creating this taks in background
            public void run()
            {
                try{
                    while(true)
                    {
                        Date d=new Date();   //getting the date
                        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm:ss");  //formatting the date
                        String datetime=sdf.format(d);
                        System.out.println(datetime);
                        if(datetime.equals("00:00:00"))    //refreshing when it is midnight 
                        {
                            new Newsapi();
                        }
                        Thread.sleep(1000);
                    }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
         };
         thread.start();
     }
    
}
