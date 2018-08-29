package org.myProject.utils.ALMInfrastructure;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.io.FileUtils;
import org.testng.ITestResult;

public class TestNGLog {

	public FileWriter oResultsFile;
	
	public void pushResults(String sTestStatus, String sFileLocation, String sFileName)
	{
		try
		{
			File file = new File(sFileLocation+File.separator+sFileName+".txt");
			if (file.exists()) 
			{
				file.delete();
				file.createNewFile();
				oResultsFile=new FileWriter(sFileLocation+File.separator+sFileName+".txt",false);
				PrintWriter printWriter= new PrintWriter(oResultsFile);
				oResultsFile.write(sTestStatus);
				oResultsFile.write((System.getProperty("line.separator")));
				printWriter.println(System.getProperty("user.dir")+File.separator+"target"+File.separator+"surefire-reports"+File.separator+sFileName+"_Report.html");
				if(sTestStatus.equalsIgnoreCase("Failed"))
				{
					String Path=getLatestScreenshotFolderPath(System.getProperty("user.dir")+File.separator+"target"+File.separator+"screenshots");
					File folder = new File(Path);
					File[] listOfFiles = folder.listFiles();
					for (int i = 0; i < listOfFiles.length; i++)
					{
						 if (listOfFiles[i].isFile())
							 printWriter.println(Path+File.separator+listOfFiles[i].getName());
						 else if (listOfFiles[i].isAbsolute()) 
						 {
							 File[] sublistOfFiles =listOfFiles[i].listFiles();
							 for (int j = 0; j < sublistOfFiles.length; j++)
								{
									 if (sublistOfFiles[j].isFile())
										 printWriter.println(Path+File.separator+listOfFiles[i].getName()+File.separator+sublistOfFiles[j].getName());
								}	 
						 }
					}
				}
				oResultsFile.close();
				printWriter.close();
			}
			else
			{
				file.createNewFile();
				oResultsFile=new FileWriter(sFileLocation+File.separator+sFileName+".txt",false);
				PrintWriter printWriter= new PrintWriter(oResultsFile);
				oResultsFile.write(sTestStatus);
				oResultsFile.write((System.getProperty("line.separator")));
				printWriter.println(System.getProperty("user.dir")+File.separator+"target"+File.separator+"surefire-reports"+File.separator+sFileName+"_Report.html");
				if(sTestStatus.equalsIgnoreCase("Failed"))
				{
					String Path=getLatestScreenshotFolderPath(System.getProperty("user.dir")+File.separator+"target"+File.separator+"screenshots");
					File folder = new File(Path);
					File[] listOfFiles = folder.listFiles();
					for (int i = 0; i < listOfFiles.length; i++)
					{
						 if (listOfFiles[i].isFile())
							 printWriter.println(Path+File.separator+listOfFiles[i].getName());
						 else if (listOfFiles[i].isAbsolute()) 
						 {
							 File[] sublistOfFiles =listOfFiles[i].listFiles();
							 for (int j = 0; j < sublistOfFiles.length; j++)
								{
									 if (sublistOfFiles[j].isFile())
										 printWriter.println(Path+File.separator+listOfFiles[i].getName()+File.separator+sublistOfFiles[j].getName());
								}	 
						 }
					}
				}
				oResultsFile.close();
				printWriter.close();
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception while pushing data on File : "+e.getMessage());
			
		}
	}
	public void createFolder(String folderPath) {
		try {
			File folder = new File(folderPath);
			if (folder.exists()) {
				FileUtils.deleteDirectory(folder); // clean out directory (this is
												// optional -- but good know)
				FileUtils.forceDelete(folder); // delete directory
				FileUtils.forceMkdir(folder);
			} // create directory
			else {
				FileUtils.forceMkdir(folder);
			} // create directory

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public String getLatestScreenshotFolderPath(String path)
	{
		 File dir = new File(path);
		    File max = null;
		    for (File file : dir.listFiles()) {
		        if (file.isDirectory() && (max == null || max.lastModified() < file.lastModified())) {
		            max = file;
		        }
		    }
		    
		    return max.getPath();
	}
	

}
