package chiper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Shader {
	
	byte[] key;
	
	public Shader(String key){
		this.key = key.getBytes();
	}
	public Shader(byte[] key){
		this.key = key;
	}
	
	public void Shade(File source){
		
		File shadedFile = new File(source.getParent() + "/SHD/" + source.getName());
		try {
			FileInputStream input = new FileInputStream(source);
			FileOutputStream output = new FileOutputStream(shadedFile);
			int i;
		    byte[] b = new byte[1024];
		    byte[] c = new byte[1024];
		    i = input.read(b);
		    int k = 0;
		    for(byte j : key){
		    	c[k] = (byte) (key[k] ^ b[k++]);
		    }
		    output.write(c, 0, i);
		    while((i=input.read(b))!=-1) {
		      output.write(b, 0, i);
		    }
		} catch (FileNotFoundException e) {
			// No file man
			e.printStackTrace();
		} catch (IOException e) {
			// Exceptions
			e.printStackTrace();
		} 
	}
	
	public File Unshade(File source){
		
		File tempFile = new File(source.getParentFile().getParent() + "/TMP/" + source.getName());
		try {
			FileInputStream input = new FileInputStream(source);
			FileOutputStream output = new FileOutputStream(tempFile);
			int i;
		    byte[] b = new byte[1024];
		    byte[] c = new byte[1024];
		    i = input.read(b);
		    int k = 0;
		    for(byte j : key){
		    	c[k] = (byte) (key[k] ^ b[k++]);
		    }
		    output.write(c, 0, i);
		    while((i=input.read(b))!=-1) {
		      output.write(b, 0, i);
		    }
		} catch (FileNotFoundException e) {
			// No file man
			e.printStackTrace();
		} catch (IOException e) {
			// Exceptions
			e.printStackTrace();
		} 
		return tempFile;
	}

}
