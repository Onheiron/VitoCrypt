package support;

import java.io.File;

import magic.TypeMagic;

import com.example.vitocrypt.R;
import com.example.vitocrypt.Start;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FileItem extends LinearLayout{
	
	File file;
	ImageView fileThumb;
	TextView fileName;
	Start context;
	private String fileType;

	public FileItem(Context context, File file, Fragment caller){
		super(context);
		this.context = (Start) context;
		this.file = file;
		LinearLayout.inflate(context, R.layout.file_item, this);
		fileThumb = (ImageView) findViewById(R.id.imageView1);
		fileName = (TextView) findViewById(R.id.textView1);
		String[] parts = file.getName().split("\\.");
		String extension = parts.length > 0 ? parts[parts.length - 1] : "";
		
    	if(file.isDirectory()){
    		fileThumb.setImageResource(R.drawable.directory);
    		this.fileType = "directory";
    	}else if(java.util.Arrays.binarySearch(TypeMagic.IMAGE_TYPES, extension) >= 0){
    		fileThumb.setImageResource(R.drawable.image);
    		this.fileType = "image";
    	}else if(java.util.Arrays.binarySearch(TypeMagic.AUDIO_TYPES, extension) >= 0){
    		fileThumb.setImageResource(R.drawable.audio);
    		this.fileType = "audio";
    	}else if(java.util.Arrays.binarySearch(TypeMagic.VIDEO_TYPES, extension) >= 0){
    		fileThumb.setImageResource(R.drawable.video);
    		this.fileType = "video";
    	}
    	fileName.setText(file.getName());
	}
	
	public File getFile(){ return this.file; }
	public String getFileType() { return fileType; }
}
