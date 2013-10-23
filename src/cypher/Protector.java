package cypher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.example.vitocrypt.R;

import support.FileItem;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

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
	
	public void Protect(FileItem source, float percentage) throws IOException{
		ProtectingTask protectingTask = new ProtectingTask("PRT");
		protectingTask.encrypt(source, percentage);
	}
	
	public void Obscure(FileItem source, float percentage) throws IOException{
		ProtectingTask protectingTask = new ProtectingTask("SHD");
		protectingTask.encrypt(source, percentage);
	}
	
	public void ProtectImage(FileItem source) throws IOException{
		Protect(source,(float) 0.1);
	}
	public void ProtectVideo(FileItem source) throws IOException{
		Protect(source,(float) 0.1);
	}
	public void ProtectAudio(FileItem source) throws IOException{
		Protect(source,(float) 0.5);
	}
	
	public void UnProtect(File source, FileItem destination, float percentage) throws IOException{
		ProtectingTask protectingTask = new ProtectingTask("");
		protectingTask.decrypt(destination, source, percentage);
		
	}
	
	private class ProtectingTask extends CypherTask{

		public ProtectingTask(String encryption) {
			super(encryption);
		}
		@Override
		protected int encrypt() {
			PercXORCopy(fileItem.getFile(), file);
			return 0;
		}
		@Override
		protected int decrypt() {
			PercXORCopy(file, fileItem.getFile());
			return 0;
		}
		protected void PercXORCopy(File input, File output){
			int shadedBitSize = Math.round((input.length()*percentage)/1024);
			int newProgress = 0;
			FileInputStream inputStream;
			try {
				inputStream = new FileInputStream(input);
				FileOutputStream outputStream = new FileOutputStream(output);
				int i;
				int p = 0;
			    byte[] b = new byte[1024];
			    byte[] c = new byte[1024];
			    for(int j = 0; j < shadedBitSize; j++){
			    	i = inputStream.read(b);
			    	for(int k = 0; k < 1024; k++){
			    		c[k] = (byte) (b[k] ^ key[k%32]);
			    	}
			    	outputStream.write(c, 0, i);
			    	p++;
			    	newProgress = Math.round(((p*102400)/input.length()));
			    	if(newProgress != lastProgress){
			    		publishProgress(newProgress);
			    		lastProgress = newProgress;
			    	}
			    }
			    p = 0;
			    while((i=inputStream.read(c))!=-1) {
			    	outputStream.write(c, 0, i);
			      	p++;
			      	newProgress = Math.round((((p+shadedBitSize)*102400)/input.length()));
			    	if(newProgress >= lastProgress+5){
			    		publishProgress(newProgress);
			    		lastProgress = newProgress;
			    	}
			    }
			    inputStream.close();
			    outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
