package com.byzan.operations;

import java.io.File;

import com.byzan.utility.MyLogger;
import com.byzan.utility.PathConstant;




public class FolderCreation implements PathConstant {

	public static int foldercreation()
	{
		File tempFiile = null;
		try
		{
			tempFiile = new File(INPUT);
			if(!tempFiile.exists())
			{
				tempFiile.mkdirs();
				MyLogger.info("Creating "+INPUT+" folder");
				
			}
			tempFiile = new File(OUTPUT);
			if(!tempFiile.exists())
			{
				tempFiile.mkdirs();
				MyLogger.info("Creating "+OUTPUT+" folder");
				
			}
			
			tempFiile = new File(INPUT_BACKUP);
			if(!tempFiile.exists())
			{
				tempFiile.mkdirs();
				MyLogger.info("Creating "+INPUT_BACKUP+" folder");
				
			}
			
			tempFiile = new File(ERROR);
			if(!tempFiile.exists())
			{
				tempFiile.mkdirs();
				MyLogger.info("Creating "+OUTPUT+" folder");
				
			}
			
		}catch (Exception e) {
			
			MyLogger.error("Error while creating folder  "+e.toString());
			return 0;
		}
		
		
		return 1;
		
	}
}
