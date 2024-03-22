package demographic_data;


import javax.swing.*;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import bsh.ParseException;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
//import com.opencsv.CsvReader;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import java.io.FileReader;



	import javax.swing.*;
	import java.awt.*;
	import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import au.com.bytecode.opencsv.CSVReader;
	import au.com.bytecode.opencsv.CSVWriter;			  										 
	import javax.swing.*;
	import javax.swing.event.*;
	import java.awt.*;
	import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;  

	
					
					public class swing2 implements ActionListener{  
						
						
					
						
						 final static JTextField text1 = new JTextField();
						
						 final static JTextField text2 = new JTextField(); 
						 
						 final static JTextField text3 = new JTextField();
						 final static JTextField text4 = new JTextField();
						 final static JTextField text5 = new JTextField();
						 
						 
						 final static JTextField text6 = new JTextField();
						 final static JTextField text7 = new JTextField();
						 final static JTextField text8 = new JTextField();
						 final static JTextField text9 = new JTextField();
						 JButton c = new JButton("Select Date"); 
						 
						 JButton select_csv;
						 static String csv_file_path;
						 static JLabel d1=new JLabel(); 
						 static JLabel d2=new JLabel(); 
						 
						 final JFrame f=new JFrame(); 
						
						public static void main(String[] args) throws Throwable 
					    { 
					    
						      swing2 obj=new swing2();
						    
					    }
						
						
						
						
					
					    
					    public swing2()
						
					    {
					    
					    	//final JFrame f=new JFrame();  
					    
					    
					    f.setLayout( new FlowLayout() );

	                    f.setTitle("PinkTempo Application");
	                    
	                 
	                   
					    JLabel l1=new JLabel("no.of orders");    
				        l1.setBounds(20,20, 200,30); 
				        
				        
				        
				        
				        
				        
				        
				        select_csv = new JButton("select csv file");
				       
				        select_csv.setBounds(400,40, 140,30);
				        
				   //     select_csv.addActionListener(this);
				        select_csv.addActionListener(this);
				        
				        
				        
				        
				        
				        
				        
				     //   JButton c = new JButton("Select Date");  
				        c.setBounds(400,100, 140,30); 
				        
				        c.setEnabled(false);
				        
				        
				     //   JButton b = new JButton("Submit");  
				     //   b.setBounds(400,80, 140,30); 
				        
				        
				     
				        text1.setBounds(200,20, 160,30);
				        
				      
				        JButton jb_clear=new JButton("Clear");
				        jb_clear.setBounds(400,160, 140,30);
				        
				        
				        
				        
				        jb_clear.addActionListener(this);
				        
				        
				        
				      //  JLabel d1=new JLabel("start date");    
				        d1.setBounds(400,220, 200,30); 
				        
				        d2.setBounds(400,260, 200,30); 
				        
				        
				        JLabel l2=new JLabel("Total Fullfilled Orders:");    
				        l2.setBounds(20,60, 200,30); 
				        
				    //    final JTextField text2 = new JTextField(); 
				        text2.setBounds(200,60, 160,30);
				        
				        
				        JLabel l3=new JLabel("Not Fullfilled Orders:");    
				        l3.setBounds(20,100, 200,30); 
				        
				     //   final JTextField text3 = new JTextField(); 
				        text3.setBounds(200,100, 160,30);
				        
				        JLabel l4=new JLabel("Restocked Order:");    
				        l4.setBounds(20,140, 200,30); 
				        
				    //    final JTextField text4 = new JTextField(); 
				        text4.setBounds(200,140, 160,30);
				        
				        
				        
				        JLabel l5=new JLabel("Total Sales:");    
				        l5.setBounds(20,180, 200,30); 
				        
				     //   final JTextField text5 = new JTextField(); 
				        text5.setBounds(200,180, 160,30);
				        
				        
				        JLabel l6=new JLabel("Total Payment Recieved ");    
				        l6.setBounds(20,220, 200,30); 
				        
				     //   final JTextField text5 = new JTextField(); 
				        text6.setBounds(200,220, 160,30);
				        
				        
				        
				        
				        JLabel l7=new JLabel("Total Pending Amount");    
				        l7.setBounds(20,260, 200,30); 
				        
				     //   final JTextField text5 = new JTextField(); 
				        text7.setBounds(200,260, 160,30);
				        
				        
				        
				        JLabel l8=new JLabel("Partially Paid");    
				        l8.setBounds(20,300, 200,30); 
				        
				     //   final JTextField text5 = new JTextField(); 
				        text8.setBounds(200,300, 160,30);
				        
				        
				        
				        
				        JLabel l9=new JLabel("Voided");    
				        l9.setBounds(20,340, 200,30); 
				        
				     //   final JTextField text5 = new JTextField(); 
				        text9.setBounds(200,340, 160,30);
				        
				        
				        
				                f.add(l1); f.add(text1); f.add(l2); f.add(text2);  f.add(l3); f.add(text3); f.add(l4);f.add(select_csv);f.add(d1);f.add(d2);
				                f.add(text4);f.add(l5); f.add(text5);f.add(jb_clear);f.add(c);f.add(l6);f.add(l7);f.add(text6);f.add(text7);f.add(l8);f.add(l9);f.add(text8);f.add(text9);
				                f.setLocation(600,300);
				                f.setSize(650,500);    
				                f.setLayout(null);  
				                
				               
				                
				                f.setVisible(true);  
				                
				                
				                
				                c.addActionListener(new ActionListener()
				                {  
				                    public void actionPerformed(ActionEvent e){  
				                    	swing3 obj=new swing3();
				                    	
				                    }  
			                    });  
				                
				                
				                
				              
				                
				                
				                
				                
				                
				                
				                
				                
				                jb_clear.addActionListener(new ActionListener()
				                {  
				                    public void actionPerformed(ActionEvent e)
				                    {  
				                    	
				                    	    text1.setText("");
				                            text2.setText("");  
				                            
				                            text3.setText(""); 
				                            
				                            text4.setText(""); 
				                            
				                            text5.setText(""); 
				                            text6.setText("");
				                            text7.setText("");
				                            text8.setText("");
				                            text9.setText("");
				                      }  
				                  });  
				                
				             

					    }


						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							
							
							 if (e.getSource() == select_csv)
						        {
						            JFileChooser fileChooser = new JFileChooser();
						           
						           
						            int returnValue = fileChooser.showOpenDialog(f);


						            if (returnValue == JFileChooser.APPROVE_OPTION)
						            {
						            	csv_file_path=fileChooser.getSelectedFile().getAbsolutePath();
						            	System.out.println("csv file path===="+csv_file_path);
						            	
						            
						            }
						            c.setEnabled(true);
						        }
							
							 
							
							System.out.println("-------------------------------------------------------");
						
					    }
						
						
						
						
						
						
						

						@SuppressWarnings("unlikely-arg-type")
						public static double[] verify_input(String startDateString, String endDateString) throws NumberFormatException, IOException, java.text.ParseException
						{
							String strFile = "/home/shatam-system-i2/Desktop/report_apps_sales.csv";
							//String order_report_path = "/home/shatam-system-i2/Desktop/orders_app_reports.csv";
							
							String order_report_path = csv_file_path;
							
							
							
							
							
							@SuppressWarnings("unused")
							Set<String> uniqueStrings = new HashSet<String>();
							
						
						     
					    	
						      CSVReader reader = new CSVReader(new FileReader(order_report_path));
						      String [] nextLine;
						      String Start_date;
						      String End_date;
						      String order;
						      String financial_status;
						      double total_sales;
						      double sum1=0.0;
						      double sum2=0.0;
						      double sum3=0.0;
						      double sum=0.0;
						      double subtotal_paid=0.0;
						      double subtotal_unpaid=0.0;
						      double partially_paid=0.0;
						      double voided=0.0;
						      
						      String Fulfillment_Status;
						      int order_fullfilled=0;
						      int not_fullfilled=0;
						      int restocked_orders=0;
						  	
						 
						   int count=0;
						   int count1=0;
						      while ((nextLine = reader.readNext()) != null) 
						      {
						    	  Start_date= nextLine[15];
						    	  
						    	  End_date= nextLine[15];
						    	  
						    	  financial_status=nextLine[2];
						    	  
						    	  order=nextLine[0];
						    	  
						    	  Fulfillment_Status= nextLine[4];
						    	  
						    	//  total_sales=nextLine[26];
						    	  try
						    	  {
						    	  total_sales = Double.parseDouble(nextLine[11]);
						    	 
						    	 
						    	  
						    	  
						    	  String[] parts = Start_date.split(" ");
						    	  String start_date_split = parts[0];
						    	  
						    	  
						    	  
						    	  String[] parts1 = Start_date.split(" ");
						    	  String end_date_split = parts[0];
						    	  
						    	  if(startDateString.equals(start_date_split))
						    	  {
						    		 // System.out.println("start_date_split===="+date);
						    		  System.out.println("order = "+order +" total_sales = "+total_sales);
						    		//  sum1=sum1+total_sales;
						    		  sum=sum+total_sales;
						    		  
						    		  uniqueStrings.add(order);
						    		  
						    		  
						    		  if(financial_status.equals("partially_paid"))
							    	  {
						    			  partially_paid= partially_paid+total_sales;
							    	  } 
						    		  
						    		  if(financial_status.equals("voided"))
							    	  {
						    			  voided= voided+total_sales;
							    	  } 
						    		  
						    		  if(financial_status.equals("paid"))
							    	  {
						    			  subtotal_paid= subtotal_paid+total_sales;
							    	  } 
						    		  
						    		  if(financial_status.equals("pending"))
						    		 // if (financial_status.equals("pending") || financial_status.equals("partially_paid"))
							    	  {
						    			  subtotal_unpaid= subtotal_unpaid+total_sales;
							    	  } 
						    		  
						    		  if(Fulfillment_Status.equals("restocked"))
							    	  {
						    			  restocked_orders++;
							    	  }
						    		  
						    		  
						    		  
						    		  if(Fulfillment_Status.equals("fulfilled"))
							    	  {
						    			  order_fullfilled++;
							    	  }
							    	  
							    	  if(Fulfillment_Status.equals("unfulfilled"))
							    	  {
							    		  not_fullfilled++;
							    	  }
						    		  
						    		  count++;
						    	  }
						    	  
						    
						    	  
						    	  if(!startDateString.equals(endDateString))
					    		  { 
						    		  
						    	  if(endDateString.equals(end_date_split))
						    	  {
						    		  
						    	
						    		  
						    		 // System.out.println("start_date_split===="+date);
						    		//  System.out.println("endDateString match found");
						    		  System.out.println("order = "+order+" total_sales = "+total_sales);
						    		 // sum2=sum2+total_sales;
						    		  sum=sum+total_sales;
						    		  
						    		  
						    		  
						    		  if(financial_status.equals("partially_paid"))
							    	  {
						    			  partially_paid= partially_paid+total_sales;
							    	  } 
						    		  
						    		  if(financial_status.equals("voided"))
							    	  {
						    			  voided= voided+total_sales;
							    	  } 
						    		  
						    		  
						    		  if(financial_status.equals("paid"))
							    	  {
						    			  subtotal_paid= subtotal_paid+total_sales;
							    	  } 
						    		  
						    		  //if (financial_status.equals("pending") || financial_status.equals("partially_paid"))
						    		  if(financial_status.equals("pending"))
							    	  {
						    			  subtotal_unpaid= subtotal_unpaid+total_sales;
							    	  } 
						    		  
						    		  if(Fulfillment_Status.equals("restocked"))
							    	  {
						    			  restocked_orders++;
							    	  }
						    		  
						    		  if(Fulfillment_Status.equals("fulfilled"))
							    	  {
						    			  order_fullfilled++;
							    	  }
							    	  
							    	  if(Fulfillment_Status.equals("unfulfilled"))
							    	  {
							    		  not_fullfilled++;
							    	  }
						    		  
						    		  uniqueStrings.add(order);
						    		  count1++;
						    	  }
					    		  } 
						    	  
						    	  try
						    	  {
						    		
						    	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						    	        LocalDate csv_start_date = LocalDate.parse(start_date_split, formatter);
						    	      
						    	        LocalDate csv_end_date = LocalDate.parse(end_date_split, formatter);
						    	        
						    	        
						    	        
						    	     
						    	        LocalDate user_input_start_date = LocalDate.parse(startDateString, formatter);
						    	        
						    	     
						    	        LocalDate user_input_end_date = LocalDate.parse(endDateString, formatter);
						    	        
								    
						    	        if (csv_start_date.isAfter(user_input_start_date) && csv_start_date.isBefore(user_input_end_date)) 
						    	        {
						    	            System.out.println("within the range."+csv_start_date+"  order= "+order+" total_sales = "+total_sales);
						    	          //  sum3=sum3+total_sales;
						    	            sum=sum+total_sales;
						    	            
						    	            if(financial_status.equals("partially_paid"))
									    	  {
								    			  partially_paid= partially_paid+total_sales;
									    	  } 
								    		  
								    		  if(financial_status.equals("voided"))
									    	  {
								    			  voided= voided+total_sales;
									    	  } 
						    	            
						    	            
						    	            if(financial_status.equals("paid"))
									    	  {
								    			  subtotal_paid= subtotal_paid+total_sales;
									    	  } 
								    		  
								    		  if(financial_status.equals("pending"))
								    			//  if (financial_status.equals("pending") || financial_status.equals("partially_paid"))
									    	  {
								    			  subtotal_unpaid= subtotal_unpaid+total_sales;
									    	  } 
								    		  
								    		  if(Fulfillment_Status.equals("restocked"))
									    	  {
								    			  restocked_orders++;
									    	  }
						    	            
						    	            if(Fulfillment_Status.equals("fulfilled"))
									    	  {
						    	            	order_fullfilled++;
									    	  }
									    	  
									    	  if(Fulfillment_Status.equals("unfulfilled"))
									    	  {
									    		  not_fullfilled++;
									    	  }
						    	            uniqueStrings.add(order);
						    	        } 
						    	       
								    	  
								    	
						    	    
						    	  }
						    	  catch(Exception obj)
						    	  {
						    		  System.out.println("error found while date conversion");
						    	  }
						    	  
						    	  }
						    	  catch(Exception obj1)
						    	  {
						    		  
						    	  }
						   
						    	 
						      }
						      
						      System.out.println("Number of unique strings: " + uniqueStrings.size());
						      
						      
						      
						      
						      @SuppressWarnings("unused")
							int count_of_orders = uniqueStrings.size();
						      
						      System.out.println("count_of_orders====="+count_of_orders);
						      
						      
						      
						      
						      
						      
						      
						        System.out.println("Unique strings: " + uniqueStrings);
						      System.out.println("start date count"+count);
						     System.out.println("end date count"+count1);
						     
						   System.out.println("......................................");
						   System.out.println();
						     System.out.println("no_of_orders====="+count_of_orders);
						   
						   
						     
						     System.out.println("order fullfilled==========="+order_fullfilled);
						     
						     System.out.println("Total sales=============="+sum);
						     
						     System.out.println("not fullfilled ==========="+not_fullfilled);
						     
						     System.out.println("total payment recieved==========="+subtotal_paid);
						     
						     System.out.println("total pending amount ==========="+subtotal_unpaid);
						     
                            System.out.println("partially_paid==========="+partially_paid);
						     
						     System.out.println("voided ==========="+voided);
						     
						     System.out.println("restocked orders ==========="+restocked_orders);
						     
						    
						 
						    
						     
						     double[] values = new double[9];
						      values[0] = count_of_orders;
						      values[1] = order_fullfilled;
						      values[2] = not_fullfilled;
						      values[3] = restocked_orders;
						      values[4] = sum;
						      values[5] = subtotal_paid;
						      values[6] = subtotal_unpaid;
						      values[7] = partially_paid;
						      values[8] = voided;
						      return values;
						    
						      
						}	
					
					}
