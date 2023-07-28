package com.byzan.operations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import com.byzan.utility.MyLogger;
import com.byzan.utility.PathConstant;



public class Mt940ReadOperation implements PathConstant{

//	public static ArrayList<ArrayList<String>> filedata = new ArrayList<>();
//	public static ArrayList<ArrayList<String>> filedata1 = new ArrayList<>();
//	public static ArrayList<ArrayList<String>> filedata11 = new ArrayList<>();
//	public static String TransactionReferenceNumber ="";
//	public static String RelatedReference ="";
//	public static String AccountIdentification = "";
//	public static String  StatementSequenceNumber ="";
//	 String  InformationtoAccountOwner ="";
//
//	 public static String  IntermediateBalance ="";
//	
//	 public static String OpeningBalance="";
//	 public  static String ClosingBalance ="";
//	 public static String additionalinformation ="";

	public static  String ClosingBalance ="";
	public static  String additionalinformation ="";
	public static ArrayList<ArrayList<String>> readFile(File inputFile)
	{
	
		ArrayList<ArrayList<String>> filedata = new ArrayList<>();
		ArrayList<ArrayList<String>> filedata1 = new ArrayList<>();
		ArrayList<ArrayList<String>> filedata11 = new ArrayList<>();
		String TransactionReferenceNumber ="";
		String RelatedReference ="";
		String AccountIdentification = "";
		String  StatementSequenceNumber ="";
		 String  InformationtoAccountOwner ="";

		 String  IntermediateBalance ="";
		
		 String OpeningBalance="";
//		 String ClosingBalance ="";
//		 String additionalinformation ="";
		ArrayList<String> lineData = new ArrayList<>();

		File file = new File(inputFile.toString()); 



		String Tag86Value="";
		String actualTag86Value="";
		boolean tag86flag =false;
		boolean tag61flag=false;
		boolean flag64=false;
		boolean flag62Mtag=false;
		boolean dashInFile=false;
		boolean last86TagOfFile=false;
		int counter =0;
		int tag61Counter =0;
		int tag86Counter=0;

		try (BufferedReader br =new BufferedReader(
				new InputStreamReader(
						new FileInputStream(file)))) {
			String line;
			String templine61 = "";
			String line61="";
			String temp86line="";
			while ((line = br.readLine()) != null) {

				counter++;
//				System.out.println(line);
				//lineData = new ArrayList<>();
				if(line.length() > 4 || line.startsWith("-"))
				{
					if(line.startsWith("-"))
					{
//						tag86flag = true;
//						dashInFile=true;
//						flag64=false;
//						flag62Mtag=true;
//						last86TagOfFile=false;

					}else {
					String tag= line.substring(0,4);
					
//					System.out.println(tag);
					switch (tag) {
					case ":20:":

						//Transaction Reference Number
						TransactionReferenceNumber = line.replace(":20:", "");
						tag86flag = false;flag62Mtag=false;
						dashInFile=false;
						last86TagOfFile=false;
						flag64=false;
						Tag86Value ="";
						break;
					case ":21:":

						//Related Reference
						RelatedReference = line.replace(":21:", "");
						tag86flag = false;flag62Mtag=false;
						dashInFile=false;
						last86TagOfFile=false;
						flag64=false;
						Tag86Value ="";
						break;
					case ":25:":
						tag86flag = false;flag62Mtag=false;
						dashInFile=false;
						last86TagOfFile=false;
						flag64=false;
						//Account Identification
						AccountIdentification = line.replace(":25:", "");
						Tag86Value ="";
						break;
					case ":28C":
						//Statement_Sequence Number
						StatementSequenceNumber = line.replace(":28C:", "");
						Tag86Value ="";
						tag86flag = false;flag62Mtag=false;
						dashInFile=false;
						last86TagOfFile=false;
						flag64=false;
						break;
					case ":62M":
						//Intermediate Balance for the date requested
						IntermediateBalance = line.replace(":62M:", "");
//							Tag86Value ="";
							tag86flag = true;
							flag62Mtag=true;
							dashInFile=false;
							last86TagOfFile=false;
							flag64=false;
							tag86Counter++;
							break;
					case ":62F":
						tag61Counter=1;
//							Tag86Value ="";
							tag86flag = false;flag62Mtag=false;
							dashInFile=false;
							last86TagOfFile=false;
							flag64=false;
							tag86Counter=0;
							break;	
						
					case ":60F":
						tag86flag = false;flag62Mtag=false;
						dashInFile=false;
						last86TagOfFile=false;

						OpeningBalance = line.replace(":60F:", "");
						Tag86Value ="";
						flag64=false;
						break;
						
					
					case ":61:":

						tag86Counter=0;
						tag61Counter++;
						tag86flag = false;flag62Mtag=false;
						last86TagOfFile=false;
						dashInFile=false;
						templine61=line;
						flag64=false;
//						if(tag61Counter>1 && Tag86Value.isEmpty())
//						{
//							filedata.remove(filedata.size()-1);
//						}

						break;
					case ":64:":
						ClosingBalance=line.replace(":64:", "");
						last86TagOfFile=true;flag62Mtag=false;
						
					tag61Counter=0;
						tag86Counter=0;
						tag86flag = false;flag64=true;
						break;
					case ":86:":	
						tag86Counter++;
						tag61Counter=0;
						tag86flag = true;
						flag62Mtag=true;
						last86TagOfFile=true;
						line61=templine61;
						//tag61Counter=0;
						break;

					}
					}
					if(tag86flag == true&& IntermediateBalance.isBlank()||last86TagOfFile==true)
					{
						if(tag86Counter<=1)
						{
							Tag86Value = Tag86Value+line;
							 if(line.startsWith("-"))
							 {	dashInFile=true;
								 Tag86Value=Tag86Value+line;
							 }
							
							 else
							 {
								 
							 }
							
						}
						
						else
						{
							
						}
				
					}
					if(tag86flag == true && !IntermediateBalance.isBlank()&&flag62Mtag==true)
					{
						if(tag86Counter>1)
						{
							Tag86Value = Tag86Value+line+" -";
							tag86Counter=0;
							tag61Counter=1;
							tag86flag=false;
						}
						
							
							
					
					}
					 if(flag64==true&&last86TagOfFile==true)
					 {
						 if(tag86Counter<=1)
							{
								Tag86Value = Tag86Value+line;
								additionalinformation=Tag86Value;
								 //additionalinformation=additionalinformation.split(":86:")[1];
							}
								 else
								 {
									 
								 }
								
							}
						 
					
										
					if(tag86flag==false && !Tag86Value.isBlank() && tag86Counter==0 && tag61Counter==1 )//&& flag64==false||dashInFile==true)
					{
						lineData.add(line61);

						lineData.add(Tag86Value.replace(",",";"));
						filedata.add(lineData);
						lineData=new ArrayList<>();
						tag86Counter=0;
						Tag86Value="";

					}
					
//					filedata11.addAll(filedata);
//					filedata=new ArrayList<>();
				}

				
			}
		
			String ValueDate="";
			String EntryDate="";
			String Credit_Debit="";
			String FundsDistribution ="";
			String TransactionAmount="";
			String TransactionType="";
			String YOURREF="";

			String Bankreferencenumber="";
			String YOURREF1="";
//			String InformationtoAccountOwner="";
			String IntermediateClosingBalance="";
			String cclosingBalance="";
			String ClosingAvailableBalance="";
			String ForwardAvailableBalance="";
			String aadditionalinformation="";
			ArrayList<String> tempData =new ArrayList<>();
		
			for(int j =0; j<filedata.size();j++)
			{
				
				ArrayList<String> rowData = filedata.get(j);
			
				if(rowData.get(0).startsWith(":61:"))
				{
					String tag61= rowData.get(0).replace(":61:", "");
					ValueDate = tag61.substring(0,6);
					EntryDate = tag61.substring(6,10);
					Credit_Debit= tag61.substring(10,11);
					FundsDistribution= tag61.substring(11,12);
					TransactionAmount = tag61.substring(12,tag61.split(",")[0].length())+","+tag61.split(",")[1].substring(0,2);
					TransactionAmount=TransactionAmount.replace(",",".");
					
					TransactionType = tag61.split(",")[1].substring(2,6);
					String YOURREF11= tag61.split(",")[1].substring(6,tag61.split(",")[1].length());
					YOURREF=YOURREF11.split("//")[0];
					Bankreferencenumber=YOURREF11.split("//")[1];
					
				}

				if(rowData.get(1).startsWith(":86:"))
				{
					String tag86=rowData.get(1).replace(":86:", "");
					
					InformationtoAccountOwner = tag86;
					
					//YOURREF1=InformationtoAccountOwner;
				}else
				{
					
				}


					tempData.add(TransactionReferenceNumber);
					tempData.add(RelatedReference);
					tempData.add(AccountIdentification);
					tempData.add(StatementSequenceNumber);
					tempData.add("\""+IntermediateBalance+"\"");
					tempData.add("\""+OpeningBalance+"\"");
					tempData.add(ValueDate);
					tempData.add(EntryDate);
					tempData.add(Credit_Debit);
					tempData.add(FundsDistribution);
					tempData.add("\""+TransactionAmount+"\"");
					tempData.add(TransactionType);
					tempData.add(YOURREF);
					tempData.add(Bankreferencenumber);
					tempData.add(YOURREF1);
					tempData.add(InformationtoAccountOwner);
					tempData.add(IntermediateClosingBalance);
					tempData.add(cclosingBalance);
					tempData.add(ClosingAvailableBalance);
					tempData.add(ForwardAvailableBalance);
					tempData.add(aadditionalinformation);
				
					filedata1.add(tempData);

					tempData =new ArrayList<>();

				}
//			}


		}
		catch (Exception e1) {

			
			MyLogger.error(e1.toString());
			filedata1.clear();
			e1.printStackTrace();
		}
		return filedata1;
	}
}

