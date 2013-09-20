package chiper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Protector {
	
	byte[] key;
	
	public Protector(String key){
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] SIMkey = digest.digest(key.getBytes("UTF-8"));
			this.key = SIMkey;
		} catch (UnsupportedEncodingException e) {
			// NOT GONNA THROW THIS
			e.printStackTrace();
			this.key = key.getBytes();
		} catch (NoSuchAlgorithmException e) {
			// I KNOW IT EXISTS
			e.printStackTrace();
			this.key = key.getBytes();
		}
	}
	public Protector(byte[] key){
		this.key = key;
	}
	
	public void Protect(File source){
		
		File shadedFile = new File(source.getParent() + "/PRT/" + source.getName());
		try {
			FileInputStream input = new FileInputStream(source);
			FileOutputStream output = new FileOutputStream(shadedFile);
			int i;
		    byte[] b = new byte[1024];
		    byte[] c = new byte[1024];
		    i = input.read(b);
		    int k = 0;
		    for(byte j : key){
		    	c[k] = (byte) (key[k] ^ b[k]);
		    	k++;
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
	
	public File UnProtect(File source){
		
		File tempFile = new File(source.getParentFile().getParent() + "/TMP/" + source.getName());
		try {
			FileInputStream input = new FileInputStream(source);
			FileOutputStream output = new FileOutputStream(tempFile);
			int i;
		    byte[] b = new byte[1024];
		    byte[] c = new byte[1024];
		    i = input.read(b);
		    int k = 0;
		    for(byte j : b){
		    	c[k] = (byte) (key[k%key.length] ^ b[k]);
		    	k++;
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
