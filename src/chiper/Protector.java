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
	
	public void Protect(File source) throws IOException{
		File shadedFile = new File("sdcard/VitoCrypt/PRT/" + source.getName());
		FileInputStream input = new FileInputStream(source);
		FileOutputStream output = new FileOutputStream(shadedFile);
		int i;
	    byte[] b = new byte[1024];
	    byte[] c = new byte[1024];
	    i = input.read(b);
	    int k = 0;
	    for(byte j : key){
	    	c[k] = (byte) (j ^ b[k]);
	    	k++;
	    }
	    output.write(c, 0, i);
	    while((i=input.read(b))!=-1) {
	      output.write(b, 0, i);
	    }
	    input.close();
	    output.close();
	}
	
	public void Protect(File source, float percentage) throws IOException{
		File shadedFile = new File("sdcard/VitoCrypt/PRT/" + source.getName());
		int shadedBitSize = Math.round((source.length()*percentage)/32);
		FileInputStream input = new FileInputStream(source);
		FileOutputStream output = new FileOutputStream(shadedFile);
		int i;
	    byte[] b = new byte[32];
	    byte[] c = new byte[32];
	    for(int j = 0; j < shadedBitSize; j++){
	    	i = input.read(b);
	    	for(int k = 0; k < 32; k++){
	    		c[k] = (byte) (b[k] ^ key[k]);
	    	}
	    	output.write(c, 0, i);
	    }
	    while((i=input.read(b))!=-1) {
	      output.write(b, 0, i);
	    }
	    input.close();
	    output.close();
	}
	
	public void ProtectImage(File source) throws IOException{
		Protect(source,(float) 0.1);
	}
	public void ProtectVideo(File source) throws IOException{
		Protect(source,(float) 0.1);
	}
	public void ProtectAudio(File source) throws IOException{
		Protect(source,(float) 0.5);
	}
	
	public File UnProtect(File source, float percentage) throws IOException{
		
		File tempFile = new File("sdcard/VitoCrypt/TMP/" + source.getName());
		int shadedBitSize = Math.round((source.length()*percentage)/32);
		FileInputStream input = new FileInputStream(source);
		FileOutputStream output = new FileOutputStream(tempFile);
		int i;
	    byte[] b = new byte[32];
	    byte[] c = new byte[32];
	    for(int j = 0; j < shadedBitSize; j++){
	    	i = input.read(b);
	    	for(int k = 0; k < 32; k++){
	    		c[k] = (byte) (b[k] ^ key[k]);
	    	}
	    	output.write(c, 0, i);
	    }
	    while((i=input.read(b))!=-1) {
	      output.write(b, 0, i);
	    }
	    input.close();
	    output.close();
	    return tempFile;
	}
	
	public File UnProtect(File source) throws IOException{
		
		File tempFile = new File("sdcard/VitoCrypt/TMP/" + source.getName());
		FileInputStream input = new FileInputStream(source);
		FileOutputStream output = new FileOutputStream(tempFile);
		int i;
	    byte[] b = new byte[1024];
	    byte[] c = new byte[1024];
	    i = input.read(b);
	    int k = 0;
	    for(byte j : key){
	    	c[k] = (byte) (j ^ b[k]);
	    	k++;
	    }
	    output.write(c, 0, i);
	    while((i=input.read(b))!=-1) {
	      output.write(b, 0, i);
	    }
	    input.close();
	    output.close();
		return tempFile;
	}

}
