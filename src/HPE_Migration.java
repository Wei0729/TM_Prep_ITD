import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.*;

import org.apache.commons.io.FileUtils;


public class HPE_Migration {
	public static int count = 0;
	public static int error = 0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> folders = new ArrayList<String>();
		folders.add("C:\\work\\HPE\\ACG-EMEA");
		folders.add("C:\\work\\HPE\\ESSN-HP");
		folders.add("C:\\work\\HPE\\HP Legal - VIA");
		folders.add("C:\\work\\HPE\\SDL");
		move(folders);
		System.out.println("Task is done");
	}
	
	public static void move(ArrayList<String> folders){
		for(String mainFolder : folders){
			System.out.println("working on " + mainFolder);
			//String mainFolder = "C:\\work\\HPE\\EULM";
			moveFiles(mainFolder);
			System.out.println("Total itd files " + count);
			System.out.println("Total error " + error);
		}
	}
	
	public static void moveFiles(String mainFolder){
		File folder = new File(mainFolder);
		File[] listOfFiles = folder.listFiles();
		for(int i = 0; i < listOfFiles.length; i++){
			if(listOfFiles[i].getName().endsWith(".zip")){
				String filePath = listOfFiles[i].getAbsolutePath();
				String outputFolder = filePath.substring(0, filePath.lastIndexOf("\\"));
				//System.out.println(listOfFiles[i].getAbsolutePath());
				unZipFile(filePath,outputFolder);
			}
		}
	}
	
	public static void unZipFile(String zipFile, String outputFolder){
		byte[] buffer = new byte[1024];
		try{
			
			// create output directory is not exists
			File folder = new File(outputFolder);
	    	if(!folder.exists()){
	    		folder.mkdir();
	    	}
	    	//get the zip file content
	    	ZipInputStream zis = 
	    		new ZipInputStream(new FileInputStream(zipFile));
	    	//get the zipped file list entry
	    	ZipEntry ze = zis.getNextEntry();
	    		
	    	while(ze!=null){
	    			
	    	   String fileName = ze.getName();
	    	   	    	
	    	   if(!fileName.endsWith(".itd") || !fileName.startsWith("TGT")){
	    		   ze = zis.getNextEntry();
	    		   continue;
	    	   }
	    	   count++;
	    	   String itdFile = fileName.substring(fileName.lastIndexOf("/")+1);
	    	   //System.out.println(fileName);
	   		   String rest = fileName.substring(fileName.indexOf("/")+1);
			   rest = rest.substring(rest.indexOf("/")+1);
			   String restfolder = fileName.substring(0, fileName.indexOf(rest));
	           File newFile = new File(outputFolder + File.separator + restfolder + File.separator + itdFile);
	                
	           //System.out.println("file unzip : "+ newFile.getAbsoluteFile());
	                
	           //create all non exists folders
	           //else you will hit FileNotFoundException for compressed folder
	           new File(newFile.getParent()).mkdirs();
	              
	           FileOutputStream fos = new FileOutputStream(newFile);             

	           int len;
	           while ((len = zis.read(buffer)) > 0) {
	        	   fos.write(buffer, 0, len);
	           }
	        		
	           fos.close();   
	           ze = zis.getNextEntry();
	    	}
	    	
	        zis.closeEntry();
	    	zis.close();
	    		
	    	    	
		}catch(Exception e){
			error++;
			System.out.println("error of zipping " + zipFile);	
			e.printStackTrace();
		}
	}
}
