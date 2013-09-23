package activities;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import support.AddDirItem;
import support.AddFileItem;

import chiper.Protector;

import com.example.vitocrypt.R;
import com.example.vitocrypt.Start;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
 
public class AddFragment extends VCFragment {
 
	public final static String EXTRA_FILE_PATH = "file_path";
	public static final String EXTRA_TITLE = "title";
	public final static String EXTRA_SHOW_HIDDEN_FILES = "show_hidden_files";
	public final static String EXTRA_ACCEPTED_FILE_EXTENSIONS = "accepted_file_extensions";
	protected File mDirectory;
	protected boolean mShowHiddenFiles = false;
	protected String[] acceptedFileExtensions;
	LinearLayout layout;
	LinearLayout container;
	LinearLayout back;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	super.onCreate(inflater.getContext());
    	System.out.println("CREATED");
		// Set the view to be shown if the list is empty
    	layout = (LinearLayout) inflater.inflate(R.layout.file_picker, layout);
    	this.container = (LinearLayout) layout.findViewById(R.id.fileItemContainer);
    	this.back = (LinearLayout) layout.findViewById(R.id.filePickerBack);
    	this.back.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				folderBack();
			}	
    	});
		// Set initial directory
		mDirectory = new File("sdcard/");
		// Initialize the extensions array to allow any file extensions
		acceptedFileExtensions = new String[] {".png",".jpg",".jpeg",".bmp",".gif",".mp3",".wav",".mp4",".avi"};
		// Return layout
		refreshFilesList();
		return layout;
    	
    }
    
    public void onResume(){
    	super.onResume();
    	System.out.println("RESUMED");
    }

    public void showFiles(File directory){
    	mDirectory = directory;
    	refreshFilesList();    	
    }
    
    public void protectFile(File file){
    	// TODO
    }
    @Override
    public void onStart(){
    	super.onStart();
    	System.out.println("START!!!");
    }
    
    protected void refreshFilesList() {
    	ShowLoading();
    	container.removeAllViews();
		// Set the extension file filter
		ExtensionFilenameFilter filter = new ExtensionFilenameFilter(acceptedFileExtensions);
		// Get the files in the directory
		File[] files = mDirectory.listFiles(filter);
		if(files != null && files.length > 0) {
			for(File f : files) {
				if(f.isHidden()) continue;
				LinearLayout newItem;
				if(f.isDirectory()) newItem = new AddDirItem(context,f,this);
				else  newItem = new AddFileItem(context,f,this);
				container.addView(newItem);
			}
		}
		HideLoading();
	}
    
    public void folderBack(){
    	if(!mDirectory.getName().equalsIgnoreCase("sdcard")){
    		mDirectory = new File(mDirectory.getParent());
    		refreshFilesList();
    	}
    }
    
    private class ExtensionFilenameFilter implements FilenameFilter {
		private String[] mExtensions;
		
		public ExtensionFilenameFilter(String[] extensions) {
			super();
			mExtensions = extensions;
		}
		
		public boolean accept(File dir, String filename) {
			if(new File(dir, filename).isDirectory()) {
				// Accept all directory names
				return true;
			}
			if(mExtensions != null && mExtensions.length > 0) {
				for(int i = 0; i < mExtensions.length; i++) {
					if(filename.endsWith(mExtensions[i])) {
						// The filename ends with the extension
						return true;
					}
				}
				// The filename did not match any of the extensions
				return false;
			}
			// No extensions has been set. Accept all file extensions.
			return true;
		}
	}

    public static Bundle createBundle( String title ) {
        Bundle bundle = new Bundle();
        bundle.putString( EXTRA_TITLE, title );
        return bundle;
    }
    
}