import org.apache.commons.io.FileUtils;

import java.util.*;
import java.io.*;

public class XILFF_Migration {
	public static HashSet<String> excludeFiles = new HashSet<String>();
	public static String mainFolder = "C:\\Users\\whe\\Documents\\Studio 2014\\Projects";
	public static String targetFolder = "\\\\nyal-sol_eng\\Sol_Eng\\HPE\\TM_Migration";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GenerateExcludeFiles();	
		SearchFiles(mainFolder);
		System.out.println("Done");
	}
	
	public static void GenerateExcludeFiles(){
		excludeFiles.add("HPE");
		excludeFiles.add("Samples");
	}
	
	public static void SearchFiles(String parfolder){
		try{
			File folder = new File(parfolder);
			File[] listOfFiles = folder.listFiles();
			for(int i = 0; i < listOfFiles.length; i++){
				String str = listOfFiles[i].getAbsolutePath();
				String part = str.substring(mainFolder.length()+1);
				String temp = str.substring(str.lastIndexOf("\\")+1);
				if(excludeFiles.contains(temp) || listOfFiles[i].getName().endsWith(".xml")){
					continue;
				}
				String projectName = temp.substring(0, temp.length()-12);
				String sourceLang = part.substring(projectName.length()+1, projectName.length()+6);
				String targetLang = part.substring(projectName.length()+7, projectName.length()+12);
				
				if(listOfFiles[i].isDirectory()){
					Search(listOfFiles[i].getAbsolutePath(), sourceLang, targetLang, projectName);
				}
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public static void Search(String filePath, String sourceLang, String targetLang, String projectName){
		try{
			File folder = new File(filePath);
			File[] listOfFiles = folder.listFiles();
			for(int i = 0; i < listOfFiles.length; i++){
				if(listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".sdlxliff")){
					//System.out.println(listOfFiles[i].getAbsolutePath());
					MoveFile(listOfFiles[i].getAbsolutePath(), sourceLang, targetLang, projectName);
				}
				else if(listOfFiles[i].isDirectory() && listOfFiles[i].getName().equals(targetLang)){
					Search(listOfFiles[i].getAbsolutePath(), sourceLang, targetLang, projectName);
				}
				else if(listOfFiles[i].isDirectory() && listOfFiles[i].getName().equals(sourceLang)){
					DeleteFiles(listOfFiles[i].getAbsolutePath());
				}
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public static void DeleteFiles(String filePath){
		try{
			File folder = new File(filePath);
			File[] listOfFiles = folder.listFiles();
			for(int i = 0; i < listOfFiles.length; i++){
				String d = listOfFiles[i].getAbsolutePath();
				File deleteFile = new File(listOfFiles[i].getAbsolutePath());
				deleteFile.delete();
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public static void MoveFile(String sourcePath, String sourceLang, String targetLang, String projectName){
		try{
			String targetPath = targetFolder + "\\" + projectName + "\\" + sourceLang + "_" + targetLang;
			String fileName = sourcePath.substring(sourcePath.lastIndexOf("\\")+1);
			File targetdir = new File(targetPath);
			if (!targetdir.exists()) {
				targetdir.mkdir();
			}
			targetPath = targetPath + "\\" + fileName;
			File source = new File(sourcePath);
			File dest = new File(targetPath);
			FileUtils.copyFile(source, dest);
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
