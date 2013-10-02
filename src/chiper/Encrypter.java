package chiper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import support.FileItem;

import android.os.AsyncTask;

public class Encrypter {
	
	Cipher c;
	SecretKeySpec k;
	
	public Encrypter(byte[] key) throws InvalidKeyException{
		try {
			c = Cipher.getInstance("AES");
			k = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException e) {
			// WTF this is bad
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// Don't even know what this is about...
			e.printStackTrace();
		}
	}
	
	public Encrypter(String key) throws InvalidKeyException{
		MessageDigest digest;
		try {
			c = Cipher.getInstance("AES");
			digest = MessageDigest.getInstance("SHA-256");
			byte[] SIMkey = digest.digest(key.getBytes("UTF-8"));
			k = new SecretKeySpec(SIMkey, "AES");
		} catch (NoSuchAlgorithmException e) {
			// WTF this is bad
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// Don't even know what this is about...
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Encrypt(File file, String path){
		try {
			c.init(Cipher.ENCRYPT_MODE, k);
			FileInputStream input = new FileInputStream(file);
			CipherOutputStream output = new CipherOutputStream(new FileOutputStream(path), c);
			copy(input, output);
			output.close();
			
		} catch (FileNotFoundException e) {
			// WTF no file
			e.printStackTrace();
		} catch (IOException e) {
			// Can't write the file
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// Bad key
			e.printStackTrace();
		}
	}
	
	public void Encrypt(FileItem fileItem){
		EncryptingTask et = new EncryptingTask(fileItem);
		et.execute();
	}
	
	public File Decrypt(String pathFrom, String pathTo){
		File temp = new File(pathTo);
		try {
			c.init(Cipher.DECRYPT_MODE, k);
			CipherInputStream input = new CipherInputStream(new FileInputStream(pathFrom), c);
			FileOutputStream output = new FileOutputStream(temp);
			copy(input, output);
			input.close();
		    output.close();
			
		} catch (FileNotFoundException e) {
			// WTF no file
			e.printStackTrace();
		} catch (IOException e) {
			// Can't write the file
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// Bad key
			e.printStackTrace();
		}
		return temp;
	}
	
	public void Decrypt(File source, FileItem destination){
		DecryptingTask dt = new DecryptingTask(source, destination);
		dt.execute();
	}
	
	private void copy(InputStream is, OutputStream os) throws IOException {
	    int i;
	    byte[] b = new byte[1024];
	    while((i=is.read(b))!=-1) {
	      os.write(b, 0, i);
	    }
	  }

	public class EncryptingTask extends AsyncTask<String, Integer, Integer>{
		FileItem source;
		int lastProgress = 0;
		
		public EncryptingTask(FileItem source){
			this.source = source;
		}
		
		@Override
		protected Integer doInBackground(String... path) {
			try {
				c.init(Cipher.ENCRYPT_MODE, k);
				FileInputStream input = new FileInputStream(source.getFile());
				CipherOutputStream output = new CipherOutputStream(new FileOutputStream("sdcard/VitoCrypt/CYP/" + source.getFile().getName()), c);
				int i;
				int p = 0;
			    byte[] b = new byte[1024];
			    while((i=input.read(b))!=-1) {
			    	output.write(b, 0, i);
			    	p++;
			    	int newProgress = Math.round((p*102400)/source.getFile().length());
			    	System.out.println(lastProgress);
			    	if(newProgress != lastProgress){
			    		publishProgress(newProgress);
			    		lastProgress = newProgress;
			    	}
			    }
				output.close();
				input.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
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
			source.HideLoading();
			source.getFile().delete();
		}
		
	}
	
	public class DecryptingTask extends AsyncTask<String, Integer, Integer>{

		File source;
		FileItem destination;
		int lastProgress = 0;
		
		public DecryptingTask(File source, FileItem destination){
			this.source = source;
			this.destination = destination;
		}
		
		@Override
		protected Integer doInBackground(String... params) {
			try {
				c.init(Cipher.DECRYPT_MODE, k);
				FileInputStream input = new FileInputStream(source);
				CipherOutputStream output = new CipherOutputStream(new FileOutputStream(destination.getFile()), c);
				int i;
				int p = 0;
			    byte[] b = new byte[1024];
			    while((i=input.read(b))!=-1) {
			    	output.write(b, 0, i);
			    	p++;
			    	int newProgress = Math.round((p*102400)/source.length());
			    	System.out.println(lastProgress);
			    	if(newProgress != lastProgress){
			    		publishProgress(newProgress);
			    		lastProgress = newProgress;
			    	}
			    }
				output.close();
				input.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
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
