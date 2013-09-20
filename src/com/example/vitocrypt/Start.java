package com.example.vitocrypt;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import chiper.Encrypter;
import chiper.Protector;
import chiper.Shader;

import activities.CryptedFragment;
import activities.ObscuredFragment;
import activities.ProtectedFragment;
import activities.TabSwipeActivity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Start extends TabSwipeActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addTab( "Protetti", ProtectedFragment.class, ProtectedFragment.createBundle( "Protetti") );
        addTab( "Oscurati", ObscuredFragment.class, ObscuredFragment.createBundle( "Oscurati") );
        addTab( "Criptati", CryptedFragment.class, CryptedFragment.createBundle( "Criptati") );
        Protector protecter;
        TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        SharedPreferences IMSIPref = getSharedPreferences("VitoCrypt", 0);
        SharedPreferences.Editor editor = IMSIPref.edit();
        String imsi = mTelephonyMgr.getSubscriberId();
        editor.clear();
        editor.putString("userImsi", imsi);
		editor.commit();
		protecter = new Protector(imsi);
		
		File baseDirectory = new File(Environment.getExternalStorageDirectory() + "/VitoCrypt");
        if(!baseDirectory.exists()) {
        	baseDirectory.mkdir();
        	File cryptoDirectory = new File(baseDirectory.getPath() + "/CYP");
        	File tempDirectory = new File(baseDirectory.getPath() + "/TMP");
        	File shadeDirectory = new File(baseDirectory.getPath() + "/SHD");
        	File protectDirectory = new File(baseDirectory.getPath() + "/PRT");
        	cryptoDirectory.mkdir();
        	tempDirectory.mkdir();
        	shadeDirectory.mkdir();
        	protectDirectory.mkdir();
        }
        
        File[] files = new File(baseDirectory.getPath()).listFiles(); 
        for(File file : files){
        	if(!file.isDirectory()){
        		protecter.Protect(file);
        		file.delete();
        	}
        }
        
        
        
//        TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
//        String imsi = mTelephonyMgr.getSubscriberId();
//        try {
//        	MessageDigest digest = MessageDigest.getInstance("SHA-256");
//			byte[] SIMkey = digest.digest(imsi.getBytes("UTF-8"));
//			File clear = new File("/sdcard/DCIM/Camera/bimba.jpg");
//			File newClear;
//			Encrypter e = new Encrypter(SIMkey);
//			e.Encrypt(clear, "/sdcard/DCIM/Camera/CYP_bimba.jpg");
//			
//			File cacheDir = this.getCacheDir();
//			newClear = e.Decrypt("/sdcard/DCIM/Camera/CYP_bimba.jpg", "/sdcard/DCIM/Camera/tmp_bimba.jpg");
//			
//			Intent intent = new Intent();
//			intent.setAction(android.content.Intent.ACTION_VIEW);
//			intent.setDataAndType(Uri.fromFile(newClear), "image/*");
//			startActivity(intent);
//			
//			
//		} catch (UnsupportedEncodingException e) {
//			// I know it's supported dummy
//			e.printStackTrace();
//		} catch (NoSuchAlgorithmException e) {
//			// Right
//			e.printStackTrace();
//		} catch (InvalidKeyException e) {
//			// Oh come on!
//			e.printStackTrace();
//		}
    }
    
    public void onPause(){
    	super.onPause();
    	System.out.println("PAUSED");
    }
    
    public void onBackPressed(){
    	Dialog caricamento;
    	caricamento = new Dialog(this,R.style.caricamento);
		caricamento.setContentView(R.layout.caricamento);
		// faccio in modo che l'utente non possa chiudere la dialog di attesa
		caricamento.show();
    }
    
    public void openFile(View v){
    	String filename = (String) ((TextView)v.findViewById(R.id.textView1)).getText();
    	File currentPicture = new File("sdcard/VitoCrypt/TMP/" + filename);
    	Intent intent = new Intent();
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(currentPicture), "image/*");
		startActivity(intent);
    }
}
