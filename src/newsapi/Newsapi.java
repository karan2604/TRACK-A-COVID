/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package newsapi;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
/**
 *
 * @author asus
 */

public class Newsapi{

    /**
     * @param args the command line arguments
     */

    JFrame frame = new JFrame("Covid-19 News");
    /*
    JList<Product> list = new JList<>();
    DefaultListModel<Product> model = new DefaultListModel<>();
    */
    
    JList list;   //JLISt
    DefaultListModel model=new DefaultListModel();
    
    StringBuffer content;
    File bookmarkfile=new File("articlebookmark.txt");
    
    private static ArrayList<Covidapi> articlelist;    //Arraylist for storing api data
    
    JPopupMenu popmenu;
    JMenuItem open,bookmark;
    
    JPopupMenu combomenu;
    JMenuItem remove,openwebsite;
    
    String selectitemcombo="Bookmark";
         
    int index=0;
    
    JComboBox combo=new JComboBox();
    HashSet<String> set;
    public Newsapi()
    {
        
        //frame.setVisible(true);
        initializelist();
       
        list.setSelectionBackground(Color.cyan);                      //Setting Backgroundcolor on selection of Jlist
        list.setFixedCellHeight(80);                                  //Setting maxHeight of Jlist
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);   //Setting SingleSelction of Jlist **MULTIPLE OPTION CANNOT SELECTED
       
        list.setBackground(Color.LIGHT_GRAY);             //Setting Background of Jlist
        
        list.setFont(new Font("Dialog",Font.PLAIN,15));   //Changing the font of Jlist
        list.setFixedCellWidth(50);                       //Setting maxwidth of Jlist
        
        //Making theJpopupmenu and adding the items into it
        popmenu=new JPopupMenu();
        popmenu.add(open=new JMenuItem("Open in Browser"));
        popmenu.add(new JPopupMenu.Separator());
        popmenu.add(bookmark=new JMenuItem("Add to Bookmark"));
        
        combomenu=new JPopupMenu();
        combomenu.add(openwebsite=new JMenuItem("Open in Browser"));
        combomenu.add(new JPopupMenu.Separator());
        combomenu.add(remove=new JMenuItem("Remove"));
        
        
        /*
        list.getSelectionModel().addListSelectionListener(e -> {
            int i=list.getSelectedIndex();
            openwebsite(i);
        });
        */
         
     
        
        index=0;
        
        //double click -> when user double click on list item then we are Bookmarking the this item
        list.addMouseListener(new MouseAdapter() {
    public void mouseClicked(MouseEvent evt) {
        
        JList list = (JList)evt.getSource();
        if(SwingUtilities.isRightMouseButton(evt)
                &&!list.isSelectionEmpty()
                &&list.locationToIndex(evt.getPoint())==list.getSelectedIndex())
        {
            popmenu.show(list,evt.getX(),evt.getY());
            index =list.locationToIndex(evt.getPoint());
        }
     }
    });
        
        
        //creating mouse click of combobox
         combo.addMouseListener(new MouseAdapter() {
    public void mouseClicked(MouseEvent evt) {
        
        JComboBox combo = (JComboBox)evt.getSource();
        if(SwingUtilities.isRightMouseButton(evt)
                )
        {
            selectitemcombo=combo.getSelectedItem().toString();
            combomenu.show(combo,evt.getX(),evt.getY());
        }
     }
    });
         
         
        //Action performed Method of JMENUITEMS of JComboBOx
         ActionListener clickActionListeneroncombobox = new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
          if(actionEvent.getSource()==openwebsite)
          {
                    if(selectitemcombo.equals("Bookmark"))
                    {
                        JOptionPane.showMessageDialog(frame, "Please Select an Item!!!");
                    }
                    else
                    {
                        int i=-1;
                        for(i=0;i<articlelist.size();i++)
                        {
                            if(selectitemcombo.equals(articlelist.get(i).getTitle()))
                            {
                                openwebsite(i);
                            }
                        }
                        
                        if(i==-1)
                            readdatafromtempfile(selectitemcombo);
                        
                    
                    }
          }
          else
          {
              if(selectitemcombo.equals("Bookmark"))
                    {
                        JOptionPane.showMessageDialog(frame, "Please Select an Item!!!");
                    }
                    else
                    {
                        removeitemfromcombobox(selectitemcombo);
                    }
          }
        
      }
    };
         
         
         openwebsite.addActionListener(clickActionListeneroncombobox);  //Jmenuitem1(openwebsite) calling action performed method
         remove.addActionListener(clickActionListeneroncombobox);       //Jmenuitem1(remove) calling action performed method
     
         
       
        
        //Action performed Method of JMENUITEMS of Jlist
        ActionListener clickActionListener = new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()==open)
        {
            openwebsite(index);
        }
        else
        {
            
            if(set.contains(Integer.toString(index)))
            alreadypresent();
            else
                {
            int result=JOptionPane.showConfirmDialog(frame,"Sure,You Want To Add?","Bookmark", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(result==JOptionPane.YES_NO_OPTION)
            {
                try {
                    FileWriter filewriter=new FileWriter(bookmarkfile,true);    //writing the data into file(bookmarkfile)
                    
                    if(!set.contains(Integer.toString(index)))
                    {
                    filewriter.write(Integer.toString(index));
                    filewriter.write("\n");
                    filewriter.close();
                    set.add(Integer.toString(index));
                    addtobookmark(index);
                    }
                    
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
                }
        }
      }
    };
      
        
        open.addActionListener(clickActionListener);         //Jmenuitem1(open) calling action performed method
        bookmark.addActionListener(clickActionListener);     //Jmenuitem1(bookmark) calling action performed method
        
        //for making each list items border
        list.setCellRenderer(new MyListCellRenderer());
        
        
       
        
        JLabel label=new JLabel("COVID-19 NEWS");   //Making the Jlabel for displaying Covid-19 news
        
        label.setBounds(25, 18, 200, 50);           //Setting the boundation on label
        label.setFont(new Font("Dialog",Font.PLAIN,20));   //Changing the font of label
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));     //setting the border of label
        
        combo.setBounds(450, 18, 150, 25);   //setting the position of combobox
        readfrombookmarkfile();   //callinf funtion to read data from bookmarkfile
        additemincombobox();      //adding item from file to combobox in offline mode
        
        
        JScrollPane scroll=new JScrollPane(list);   //Making the Scrollbar and adding Jlist into it
        scroll.setBounds(25, 100, 600, 500);        //Setting the boundation on scrollpane
        
        JButton btnrefresh=new JButton("Refresh");
        btnrefresh.setBounds(300, 18, 100, 25);
        
         ActionListener refreshlistener = new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
          if(actionEvent.getSource()==btnrefresh)
              /*
              new Refreshnews();
              */
              initializelist();
        }
      };
        btnrefresh.addActionListener(refreshlistener);
        
        frame.add(label);   //Adding the label in JFrame
        frame.add(combo);
        frame.add(scroll);  //Adding the ScrollPane in JFrame
        frame.add(btnrefresh);
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);    
        frame.setSize(700, 700);  //Setting the Defautlt size of Jframe
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);   //Making the Jframe visible
    }
    
    public void initializelist()
    {
        gettingapidata();   //calling this function to get data from api class

        list=new JList(model);
        
        for(int i=0;i<articlelist.size();i++)                         //Adding the items into jlist
            model.addElement("->"+articlelist.get(i).getTitle());
    }
    
    //for opening a website
    private void openwebsite(int i)
    {
        
        Desktop d=Desktop.getDesktop();
        try {
            d.browse(new URI(articlelist.get(i).getLink()));
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
        
    }
    
    
    //reading data from bookmarkfile
    private void readfrombookmarkfile()
    {
        try {
            Scanner reader=new Scanner(bookmarkfile);
            String line;
            set=new HashSet<String>();
            while(reader.hasNextLine())
            {
                line=reader.nextLine();
                set.add(line);
            }
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    //adding the new in runtime to bookmark
    private void addtobookmark(int index)
    {
        combo.addItem(articlelist.get(index).getTitle());
        //adding to temp file
        addtotempfile(articlelist.get(index).getTitle(),articlelist.get(index).getLink());
    }
    
    //addtotemporary file incase api is updated and in updated that link does not found
    private void addtotempfile(String title,String link)
    {
        Tempflie tempfile=new Tempflie();
        tempfile.writedata(title,link);    //adding data
    }
    
    //reading from temp file if it is not found in current api data
    private void readdatafromtempfile(String title)
    {
        Tempflie tempfile=new Tempflie();
        String link=tempfile.readdata(title);    //getting data
        if(link!=null)
        openwebsitethroughlink(link);
        
    }
    
    //opening website thorough link
    private void openwebsitethroughlink(String link)
    {
        Desktop d=Desktop.getDesktop();
        try {
            d.browse(new URI(link));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    //adding item from file to combobox in offline mode
    private void additemincombobox()
    {
        combo.addItem("Bookmark");
        Iterator<String> i=set.iterator();  
        while(i.hasNext())
            combo.addItem(articlelist.get(Integer.parseInt(i.next())).getTitle());
    }
    
    // if new is already present in combobox then it show the dialog box
    private void alreadypresent()
    {
       JOptionPane.showMessageDialog(frame, "Already added to Bookmark!!!");
    }
    
    //remove bookmark from combobox and bookmarkfile
    private void removeitemfromcombobox(String selectitemcombo)
    {
        combo.removeItem(selectitemcombo);   //removing form combobox
        
        int out = 0;    //storing the selected combobox title
                        for(int i=0;i<articlelist.size();i++)
                        {
                            if(selectitemcombo.equals(articlelist.get(i).getTitle()))
                            {
                                out=i;   //finding the selected combobox title
                            }
                        }
       //deleting operation from start from here                   
       set=new HashSet<String>();
       readfrombookmarkfile();     //reading from file
       set.remove(Integer.toString(out));   //deleting a specific string from file
       
       FileWriter filewriter;    //overwriting in the file 
        try {
            filewriter = new FileWriter(bookmarkfile); //writing the data into file(bookmarkfile)
            
                    Iterator<String> itr=set.iterator();  
                        while(itr.hasNext()){  
                    filewriter.write(itr.next());
                    filewriter.write("\n");
                   }
                    filewriter.close();
                    
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    //MAIN FUNCTION
    public static void main(String[] args) {
        // TODO code application logic here
        /*
        new Refreshnews();
                */
        SwingUtilities.invokeLater(Newsapi::new);
        
    }
    
    //getting data from api 
    public static void gettingapidata()
    {
        
        Apiclass obj=new Apiclass();   //Making the object of ApiClass - apiclass stores the information of api data
        articlelist=new ArrayList<>(obj.jsondata());  //calling the method in api to get arraylist of title and http link of website
    }
    
    //ListCellRendered for making border in each jlist item
    public class MyListCellRenderer implements ListCellRenderer{

        final JLabel jlblCell = new JLabel(" ", JLabel.LEFT); //making label
        Border lineBorder = BorderFactory.createLineBorder(Color.GRAY, 1); //making border

        //ListCellRendered Interface @overriding
        public Component getListCellRendererComponent(JList jList, Object value, 
        int index, boolean isSelected, boolean cellHasFocus) {

            
        jlblCell.setOpaque(true);

        if (isSelected) {
        jlblCell.setForeground(jList.getSelectionForeground());
        jlblCell.setBackground(jList.getSelectionBackground());
        jlblCell.setBorder(new LineBorder(Color.BLUE));
        } else {
        jlblCell.setForeground(jList.getForeground());
        jlblCell.setBackground(jList.getBackground());
        }
        jlblCell.setText(value.toString());
        jlblCell.setBorder(lineBorder);  //setting the border of each Jlist item
        jlblCell.setFont(new Font("Dialog",Font.PLAIN,20));  //setting the font of each Jlist item
        
        return jlblCell;   //returning the component(JLabel)
    }
  }
 }
