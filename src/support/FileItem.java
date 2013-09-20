package support;

import java.io.File;

import android.content.Context;
import android.widget.LinearLayout;

public class FileItem extends LinearLayout{
	
	File file;

	public FileItem(Context context) {
		super(context);
	}

	public FileItem(Context context, File file){
		super(context);
		this.file = file;
	}
	
	public void setFile(File file){
		this.file = file;
	}
	
	public File getFile(){
		return this.file;
	}
}
