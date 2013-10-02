package chiper;

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
	
	public void Protect(FileItem source, float percentage) throws IOException{
		ProtectingTask protectingTask = new ProtectingTask(source, percentage);
		protectingTask.execute("PRT");
	}
	
	public void Obscure(FileItem source, float percentage) throws IOException{
		ProtectingTask protectingTask = new ProtectingTask(source, percentage);
		protectingTask.execute("SHD");
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
		UnProtectingTask unProtectingTask = new UnProtectingTask(source, destination, percentage);
		unProtectingTask.execute();
//		File tempFile = new File("sdcard/VitoCrypt/TMP/" + source.getName());
//		int shadedBitSize = Math.round((source.length()*percentage)/32);
//		FileInputStream input = new FileInputStream(source);
//		FileOutputStream output = new FileOutputStream(tempFile);
//		int i;
//	    byte[] b = new byte[32];
//	    byte[] c = new byte[32];
//	    for(int j = 0; j < shadedBitSize; j++){
//	    	i = input.read(b);
//	    	for(int k = 0; k < 32; k++){
//	    		c[k] = (byte) (b[k] ^ key[k]);
//	    	}
//	    	output.write(c, 0, i);
//	    }
//	    while((i=input.read(b))!=-1) {
//	      output.write(b, 0, i);
//	    }
//	    input.close();
//	    output.close();
//	    return tempFile;
	}
	
	private class ProtectingTask extends AsyncTask<String, Integer, Integer>{
		FileItem source;
		float percentage;
		int lastProgress = 0;
		
		public ProtectingTask(FileItem source, float percentage){
			this.source = source;
			this.percentage = percentage;
		}
		@Override
		protected Integer doInBackground(String... url) {
			File shadedFile = new File("sdcard/VitoCrypt/"+url[0]+"/" + source.getFile().getName());
			int shadedBitSize = Math.round((source.getFile().length()*percentage)/32);
			int newProgress = 0;
			FileInputStream input;
			try {
				input = new FileInputStream(source.getFile());
				FileOutputStream output = new FileOutputStream(shadedFile);
				int i;
				int p = 0;
			    byte[] b = new byte[32];
			    byte[] d = new byte[1024];
			    byte[] c = new byte[32];
			    for(int j = 0; j < shadedBitSize; j++){
			    	i = input.read(b);
			    	for(int k = 0; k < 32; k++){
			    		c[k] = (byte) (b[k] ^ key[k]);
			    	}
			    	output.write(c, 0, i);
			    	p++;
			    	newProgress = Math.round(((p*3200)/source.getFile().length()));
			    	if(newProgress != lastProgress){
			    		publishProgress(newProgress);
			    		lastProgress = newProgress;
			    	}
			    }
			    p = 0;
			    while((i=input.read(d))!=-1) {
			    	output.write(d, 0, i);
			      	p++;
			      	newProgress = Math.round((((p*102400)+(3200*shadedBitSize))/source.getFile().length()));
			    	if(newProgress != lastProgress){
			    		publishProgress(newProgress);
			    		lastProgress = newProgress;
			    	}
			    }
			    input.close();
			    output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		protected void onProgressUpdate(Integer... progress) {
	        source.setProgress(progress[0]);
	        source.refreshAdapter();
	    }
		
		@Override
		protected void onPostExecute(Integer result) {
			source.getFile().delete();
			source.HideLoading();
		}
		
	}
	
	private class UnProtectingTask extends AsyncTask<String, Integer, Integer>{
		File source;
		FileItem destination;
		float percentage;
		int lastProgress = 0;
		
		public UnProtectingTask(File source, FileItem destination, float percentage){
			this.source = source;
			this.percentage = percentage;
			this.destination = destination;
		}
		@Override
		protected Integer doInBackground(String... url) {
			File shadedFile = destination.getFile();
			int shadedBitSize = Math.round((source.length()*percentage)/32);
			int newProgress = 0;
			FileInputStream input;
			try {
				input = new FileInputStream(source);
				FileOutputStream output = new FileOutputStream(shadedFile);
				int i;
				int p = 0;
			    byte[] b = new byte[32];
			    byte[] d = new byte[1024];
			    byte[] c = new byte[32];
			    for(int j = 0; j < shadedBitSize; j++){
			    	i = input.read(b);
			    	for(int k = 0; k < 32; k++){
			    		c[k] = (byte) (b[k] ^ key[k]);
			    	}
			    	output.write(c, 0, i);
			    	p++;
			    	newProgress = Math.round(((p*3200)/source.length()));
			    	if(newProgress != lastProgress){
			    		publishProgress(newProgress);
			    		lastProgress = newProgress;
			    	}
			    }
			    p = 0;
			    while((i=input.read(d))!=-1) {
			    	output.write(d, 0, i);
			      	p++;
			      	newProgress = Math.round((((p*102400)+(3200*shadedBitSize))/source.length()));
			    	if(newProgress >= lastProgress+5){
			    		publishProgress(newProgress);
			    		lastProgress = newProgress;
			    	}
			    }
			    input.close();
			    output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		protected void onProgressUpdate(Integer... progress) {
	        destination.setProgress(progress[0]);
	        destination.refreshAdapter();
	    }
		
		@Override
		protected void onPostExecute(Integer result) {
			destination.HideLoading();
		}
		
	}
}
