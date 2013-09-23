package support;

import java.io.File;

import com.example.vitocrypt.R;

import activities.AddFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddDirItem extends LinearLayout{
	
	File directory;
	ImageView directoryThumb;
	TextView directoryName;
	Context context;
	AddFragment caller;

	public AddDirItem(Context context, AddFragment caller) {
		super(context);
		this.context = context;
		this.caller = caller;
		this.setOrientation(LinearLayout.HORIZONTAL);
		this.setBackgroundResource(R.drawable.abs__ab_transparent_dark_holo);
		directoryThumb = new ImageView(context);
		directoryName = new TextView(context);
    	directoryThumb.setMaxWidth(50);
    	directoryThumb.setMaxHeight(50);
    	LinearLayout.LayoutParams imageMargins = new LinearLayout.LayoutParams(50, 50);
    	imageMargins.setMargins(8,8,8,8);
    	directoryThumb.setLayoutParams(imageMargins);
    	directoryName.setTextSize(20);
    	directoryName.setTextColor(Color.parseColor("#ffffff"));
    	LinearLayout.LayoutParams textMargins = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    	textMargins.setMargins(5,22,0,0);
    	directoryName.setLayoutParams(textMargins);
    	this.addView(directoryThumb);
    	this.addView(directoryName);
	}

	public AddDirItem(Context context, File directory, AddFragment caller){
		super(context);
		this.context = context;
		this.caller = caller;
		this.directory = directory;
		this.setOrientation(LinearLayout.HORIZONTAL);
		this.setBackgroundResource(R.drawable.abs__ab_transparent_dark_holo);
		directoryThumb = new ImageView(context);
		directoryName = new TextView(context);
    	directoryThumb.setMaxWidth(50);
    	directoryThumb.setMaxHeight(50);
    	LinearLayout.LayoutParams imageMargins = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    	imageMargins.setMargins(8,8,8,8);
    	directoryThumb.setLayoutParams(imageMargins);
    	directoryName.setTextSize(20);
    	directoryName.setTextColor(Color.parseColor("#ffffff"));
    	LinearLayout.LayoutParams textMargins = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    	textMargins.setMargins(5,22,0,0);
    	directoryName.setLayoutParams(textMargins);
    	directoryThumb.setImageResource(R.drawable.directory);
    	directoryName.setText(directory.getName());
    	this.addView(directoryThumb);
    	this.addView(directoryName);
    	this.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				openDirectory();
			}
    	});
	}
	
	public void setDirectory(File directory){
		this.directory = directory;
		directoryThumb.setImageURI(Uri.parse(directory.getAbsolutePath()));
		directoryName.setText(directory.getName());
	}
	
	public File getDirectory(){
		return this.directory;
	}
	
	public void openDirectory(){
    	caller.showFiles(directory);
	}
}
