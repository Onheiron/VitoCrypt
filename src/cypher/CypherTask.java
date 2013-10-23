package cypher;

import java.io.File;

import support.FileItem;
import android.os.AsyncTask;

public abstract class CypherTask  extends AsyncTask<String,Integer,Integer>{
	FileItem fileItem;
	File file;
	float percentage;
	int lastProgress;
	String encryption;
	boolean decryption = false;
	
	public CypherTask(String encryption){
		this.encryption = encryption; // PRT, SHD, CYP
		this.lastProgress = 0;
	}
	public void encrypt(FileItem fileItem, float percentage){
		this.decryption = false;
		this.fileItem = fileItem;
		this.percentage = percentage; 
		this.file = new File("sdcard/VitoCrypt/"+encryption+"/" + fileItem.getFile().getName());
		this.execute("encrypt");
	}
	protected abstract int encrypt();
	
	public void decrypt(FileItem fileItem, File file, float percentage){
		this.decryption = true;
		this.fileItem = fileItem;
		this.file = file;
		this.percentage = percentage;
		this.execute("decrypt");
	}
	protected abstract int decrypt();
	
	@Override
	protected Integer doInBackground(String... params) {
		if(params[0].equalsIgnoreCase("encrypt")) return encrypt();
		else return decrypt();
	}
	
	protected void onProgressUpdate(Integer... progress) {
        fileItem.setProgress(progress[0]);
    }
	@Override
	protected void onPostExecute(Integer result) {
		if(result == 1) fileItem.Broken();
		fileItem.HideLoading();
		if(!decryption) fileItem.getFile().delete();
	}
}
