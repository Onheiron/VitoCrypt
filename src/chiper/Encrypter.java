package chiper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

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
	
	private void copy(InputStream is, OutputStream os) throws IOException {
	    int i;
	    byte[] b = new byte[1024];
	    while((i=is.read(b))!=-1) {
	      os.write(b, 0, i);
	    }
	  }

}
