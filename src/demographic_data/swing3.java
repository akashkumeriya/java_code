package demographic_data;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;			  										 
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

			
			
				//	public class swing3 extends JFrame implements ActionListener {

					
							
							public class swing3 extends JFrame {
							    private JSpinner startDateSpinner;
							    private JSpinner endDateSpinner;
							    private JLabel startDateLabel;
							    private JLabel endDateLabel;

							    public swing3() {
							        super("Date Range Picker");

							      //  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							        setPreferredSize(new Dimension(400, 150));

							        JPanel mainPanel = new JPanel(new GridLayout(2, 2, 10, 10));

							        startDateLabel = new JLabel("Start Date:");
							        mainPanel.add(startDateLabel);

							        startDateSpinner = new JSpinner(new SpinnerDateModel());
							        startDateSpinner.setEditor(new JSpinner.DateEditor(startDateSpinner, "yyyy-MM-dd"));
							        mainPanel.add(startDateSpinner);

							        endDateLabel = new JLabel("End Date:");
							        mainPanel.add(endDateLabel);

							        endDateSpinner = new JSpinner(new SpinnerDateModel());
							        endDateSpinner.setEditor(new JSpinner.DateEditor(endDateSpinner, "yyyy-MM-dd"));
							        mainPanel.add(endDateSpinner);

							        add(mainPanel, BorderLayout.CENTER);

							        JButton okButton = new JButton("OK");
							        okButton.addActionListener(new ActionListener() {
							            public void actionPerformed(ActionEvent e) {
							                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							                String startDateString = sdf.format(startDateSpinner.getValue());
							                String endDateString = sdf.format(endDateSpinner.getValue());
							                JOptionPane.showMessageDialog(swing3.this, "Start date: " + startDateString + "\nEnd date: " + endDateString);
							                System.out.println("startDateString==="+startDateString);
							                System.out.println("endDateString==="+endDateString);
							                
							                
							                
							                swing2.d1.setText("Start date : " +startDateString );
							                swing2.d2.setText("End date : " +endDateString );
							                
							             
							                try
							                {
							                	
							                	double[] values=swing2.verify_input(startDateString,endDateString);
												
							                	for (int i = 0; i < values.length; i++) {
							                      //  System.out.println("Value " + (i + 1) + ": " + values[i]);
							                        
							                        String s1=String.valueOf(values[0]); 
							                        
							                        swing2.text1.setText(s1); 
							                        
                                                    String s2=String.valueOf(values[1]); 
							                        
							                        swing2.text2.setText(s2); 
							                        
                                                   String s3=String.valueOf(values[2]); 
							                        
							                        swing2.text3.setText(s3); 
                                                    String s4=String.valueOf(values[3]); 
							                        
							                        swing2.text4.setText(s4); 
							                        
                                                   String s5=String.valueOf(values[4]); 
							                        
							                        swing2.text5.setText(s5); 
							                        
                                                   String s6=String.valueOf(values[5]); 
							                        
							                        swing2.text6.setText(s6); 
							                        
                                                  String s7=String.valueOf(values[6]); 
							                        
							                        swing2.text7.setText(s7); 
							                        
							                        
                                                   String s8=String.valueOf(values[7]); 
							                        
							                        swing2.text8.setText(s8); 
							                        
                                                    String s9=String.valueOf(values[8]); 
							                        
							                        swing2.text9.setText(s9); 
							                     }
											 
							                }
							                catch(Exception obj)
							                {
							                	
							                	System.out.println("error occured during method calling");
							                }
							             
							            }
							        });

							        add(okButton, BorderLayout.SOUTH);

							        pack();
							        setLocationRelativeTo(null);
							        setVisible(true);
							    }

							    public static void main(String[] args) {
							    	
							        new swing3();
							    }
							}




