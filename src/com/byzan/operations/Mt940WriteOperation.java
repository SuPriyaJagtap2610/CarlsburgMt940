package com.byzan.operations;


import java.io.BufferedWriter;
import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.byzan.utility.BZNUtils;
import com.byzan.utility.MyLogger;
import com.byzan.utility.PathConstant;



public class Mt940WriteOperation implements PathConstant{


	
	public  boolean writeFile(File inputFile,ArrayList<ArrayList<String>> filedata1,String closingbalance,String additionalInfo) {
		
	
				boolean flag = true;
				BufferedWriter bw =null;
				String fileName =inputFile.getName().replace(".txt", "")+".csv";
		
				try
				{
					
					
					if(filedata1.size() >0)
					{
						bw = new BufferedWriter(new FileWriter(OUTPUT+fileName));
		
						String[] header1= {"Transaction Reference Number","Related Reference","Account Identification","Statement_Sequence Number",
								"Intermediate Balance for the date requested","Opening Balance","Value Date","Entry Date","Credit_Debit Indicator",
								"Funds Distribution","Transaction Amount","Transaction Type","YOUR REF","Bank reference number","YOUR REF1","Information to Account Owner",
								"Intermediate Closing Balance","Closing Balance","Closing Available Balance","Forward Available Balance","additional information",
								};
		
		
						for(int i = 0 ; i <header1.length; i++)
						{
		
							bw.write(header1[i]);
							if(i < header1.length-1) {
								bw.write(","); 
							}
		
						}
		
						bw.write("\n");
		
						for(int i = 0 ; i <filedata1.size(); i++)
						{
		
							ArrayList<String> rowData = filedata1.get(i);
							for(int i1 = 0 ; i1 <rowData.size(); i1++)
							{
		
								bw.write(rowData.get(i1));
								if(i1 < rowData.size()-1) {
									bw.write(","); 
								}
		
							}
							bw.write("\n");
						}
						Mt940ReadOperation re=new Mt940ReadOperation();
						
						String[] footer= {"","","","","","","","","",
								"","","","","","",
								"",closingbalance,closingbalance,"",additionalInfo.split(":86:")[1]};
						for(int i = 0 ; i <footer.length; i++)
						{
		
							bw.write("\""+footer[i]+"\"");
							if(i < footer.length-1) {
								bw.write(","); 
							}
		
						}
		
						bw.write("\n");
						filedata1.clear();

						MyLogger.info("Output file "+fileName+" written successfully");
					}else
					{
						MyLogger.error("File does not contain data for writting");
					}
		
				}catch(Exception e)
				{
					flag = false;
					MyLogger.error("Error while writting output file");
					filedata1.clear();
					BZNUtils.deleteFile(OUTPUT+fileName);		
					e.printStackTrace();
				}finally
				{
					if(bw!=null)
					{
						try {
							bw.close();
							filedata1.clear();
						} catch (IOException e) {
		
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				return flag;
			}
		}