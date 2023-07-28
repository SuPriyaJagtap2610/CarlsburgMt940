package com.byzan.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.byzan.operations.FolderCreation;
import com.byzan.operations.Mt940ReadOperation;
import com.byzan.operations.Mt940WriteOperation;
import com.byzan.utility.BZNUtils;
import com.byzan.utility.MyLogger;
import com.byzan.utility.PathConstant;

public class Carlsburg_Mt940_To_Excel implements PathConstant{

	public static void main(String args[])
	{

		if(!AuditLog.isEmpty())
		{
			MyLogger.createLogFile(AuditLog);
		}else
		{
			MyLogger.error("Path for log file in properties file not found ");
		} 

		Carlsburg_Mt940_To_Excel main = new Carlsburg_Mt940_To_Excel();
		main.run();

	}

	public void run() {
		 ArrayList<ArrayList<String>> filedata11 = new ArrayList<>();

		int folderReturnValue = FolderCreation.foldercreation();
		if(folderReturnValue == 1)
		{
			File file = new File(INPUT);
			File[] files =file.listFiles();

			if(files.length > 0)
			{
				for(File inputFile : files)
				{

						MyLogger.info("************Started Processing Input File "+inputFile.getName()+" ***************");
						Mt940ReadOperation read=new Mt940ReadOperation();
						filedata11=Mt940ReadOperation.readFile(inputFile);
						{
							if(filedata11.size() > 0 && filedata11!=null)
							{
								Mt940WriteOperation write=new Mt940WriteOperation();
								
							if(write.writeFile(inputFile,filedata11,read.ClosingBalance,read.additionalinformation)== true)
							{
								try {
									BZNUtils.moveFile(INPUT+inputFile.getName(), INPUT_BACKUP+inputFile.getName());
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							}
						}
//try {
//	System.gc();
//	Thread.sleep(1000);
//} catch (InterruptedException e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//}
				}
			}
		}
	}
}
