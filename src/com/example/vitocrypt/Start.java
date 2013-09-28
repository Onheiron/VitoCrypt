package com.example.vitocrypt;

import java.io.File;

import activities.AddFragment;
import activities.CryptedFragment;
import activities.ObscuredFragment;
import activities.ProtectedFragment;
import activities.TabSwipeActivity;
import android.os.Bundle;
import android.os.Environment;
import android.content.Context;
import android.telephony.TelephonyManager;

public class Start extends TabSwipeActivity {
	
	File SDFolder;
	File baseDirectory;
	File cryptoDirectory;
	File tempDirectory;
	File shadeDirectory;
	File protectDirectory;
	
	TelephonyManager mTelephonyMgr;
	String imsi;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addTab( "Aggiungi", AddFragment.class);
        addTab( "Protetti", ProtectedFragment.class);
        addTab( "Oscurati", ObscuredFragment.class);
        addTab( "Criptati", CryptedFragment.class);
        
        mTelephonyMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        imsi = mTelephonyMgr.getSubscriberId();
        
        SDFolder = Environment.getExternalStorageDirectory();
        baseDirectory = new File(Environment.getExternalStorageDirectory() + "/VitoCrypt");
        cryptoDirectory = new File(baseDirectory.getPath() + "/CYP");
        tempDirectory = new File(baseDirectory.getPath() + "/TMP");
        shadeDirectory = new File(baseDirectory.getPath() + "/SHD");
        protectDirectory = new File(baseDirectory.getPath() + "/PRT");
       
	    if(!baseDirectory.exists()) {
	    	baseDirectory.mkdir();
	    	cryptoDirectory.mkdir();
	    	tempDirectory.mkdir();
	    	shadeDirectory.mkdir();
	    	protectDirectory.mkdir();
	    }
    }
    
    public File getSDFolder(){ return this.SDFolder; }
    public File getBaseDirectory(){ return this.baseDirectory; }
    public File getTempDirectory(){ return this.tempDirectory; }
    public File getProtectDirectory(){ return this.protectDirectory; }
    public File getShadeDirectory(){ return this.shadeDirectory; }
    public File getCryptoDirectory(){ return this.cryptoDirectory; }
    public String getIMSI(){ return this.imsi; }
}
/**
 * @TODO clean poop
 * */
//TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
//String imsi = mTelephonyMgr.getSubscriberId();
//try {
//	MessageDigest digest = MessageDigest.getInstance("SHA-256");
//	byte[] SIMkey = digest.digest(imsi.getBytes("UTF-8"));
//	File clear = new File("/sdcard/DCIM/Camera/bimba.jpg");
//	File newClear;
//	Encrypter e = new Encrypter(SIMkey);
//	e.Encrypt(clear, "/sdcard/DCIM/Camera/CYP_bimba.jpg");
//	
//	File cacheDir = this.getCacheDir();
//	newClear = e.Decrypt("/sdcard/DCIM/Camera/CYP_bimba.jpg", "/sdcard/DCIM/Camera/tmp_bimba.jpg");
//	
//	Intent intent = new Intent();
//	intent.setAction(android.content.Intent.ACTION_VIEW);
//	intent.setDataAndType(Uri.fromFile(newClear), "image/*");
//	startActivity(intent);
//	
//	
//} catch (UnsupportedEncodingException e) {
//	// I know it's supported dummy
//	e.printStackTrace();
//} catch (NoSuchAlgorithmException e) {
//	// Right
//	e.printStackTrace();
//} catch (InvalidKeyException e) {
//	// Oh come on!
//	e.printStackTrace();
//}
