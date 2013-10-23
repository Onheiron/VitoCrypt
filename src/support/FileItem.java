package support;

import java.io.File;

import magic.TypeMagic;

import com.example.vitocrypt.R;
import com.example.vitocrypt.Start;

import activities.GenericFragment;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FileItem extends LinearLayout{
	
	File file;
	ImageView fileThumb;
	TextView fileName;
	TextView subText;
	Start context;
	Fragment caller;
	ProgressBar loading;
	private String fileType;

	public FileItem(Context context, File file, Fragment caller){
		super(context);
		this.context = (Start) context;
		this.caller = caller;
		this.file = file;
		LinearLayout.inflate(context, R.layout.file_item, this);
		fileThumb = (ImageView) findViewById(R.id.imageView1);
		fileName = (TextView) findViewById(R.id.textView1);
		subText = (TextView) findViewById(R.id.textView2);
		loading = (ProgressBar) findViewById(R.id.ProgressBar);
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
    	subText.setText(fileType);
	}
	
	public File getFile(){ return this.file; }
	public String getFileType() { return fileType; }
	public void ShowLoading(){ loading.setVisibility(VISIBLE); }
	public void HideLoading(){ loading.setVisibility(GONE); }
	public void setProgress(int progress){ loading.setProgress(progress); }
	public void refreshAdapter(){ ((GenericFragment)caller).refreshAdapter(); }
	public GenericFragment getCaller(){ return (GenericFragment) this.caller; }
	public void Queued(){
		this.subText.setText("in coda...");
	}
	public void Protecting(){
		this.subText.setTextColor(Color.parseColor("#86CB86"));
		this.subText.setText("... protezione avviata...");
	}
	public void Protected(){
		this.subText.setText("...protetto!");
	}
}
