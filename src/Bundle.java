import org.apache.commons.io.FileUtils;

import java.util.*;
import java.io.*;

public class Bundle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String mainfolder = "\\\\nyal-sol_eng\\Sol_Eng\\HPE\\Program_Requirements\\File_Formats\\Bundle_1";
		searchFiles(mainfolder);
		System.out.println("Done");
	}

	public static void searchFiles(String mainfolder){
		try{
			File folder = new File(mainfolder);
			File[] listOfFiles = folder.listFiles();
			for(File file : listOfFiles){
				if(file.isFile() && file.getName().endsWith(".xml"))
					System.out.println(file.getAbsolutePath());
				else if(file.isDirectory()){
					searchFiles(file.getAbsolutePath());
				}
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
