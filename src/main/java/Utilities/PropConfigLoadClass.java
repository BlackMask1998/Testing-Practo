package Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropConfigLoadClass {

	public static Properties propfile;
	
	public static Properties loadFile() 
	{
	
		if(propfile==null)
		{
			propfile= new Properties();
			String filepath= System.getProperty("user.dir")+"\\Resources\\ObjectRepository\\Config.properties";
			FileInputStream in;
			try {
				in = new FileInputStream(filepath);
				try {
					propfile.load(in);
					} catch (IOException e) {
						System.out.println(" Unable to Load Config File");
					}
				} catch (FileNotFoundException e) {
					System.out.println(" Config File Not Found ");
				}
			
		}
		return propfile;
	}
}
