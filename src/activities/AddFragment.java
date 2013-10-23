package activities;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import magic.TypeMagic;
import support.FileItem;
import com.example.vitocrypt.R;

import cypher.Protector;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.LinearLayout;
import android.widget.ListView;
 
public class AddFragment extends GenericFragment {
 
	protected File mDirectory;
	LinearLayout back;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	layout = (LinearLayout) inflater.inflate(R.layout.file_picker, layout);
    	super.onCreate(inflater.getContext(), (ListView) layout.findViewById(R.id.fileList));
		this.container.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			public void onItemClick(AdapterView<?> AdapterView, View SelectedView, int position, long id) {
				FileItem item = (FileItem) SelectedView;
				if(item.getFileType().equalsIgnoreCase("directory")) showFiles(item.getFile());
				else start.openContextMenu(item);
			}
		});
    	this.back = (LinearLayout) layout.findViewById(R.id.filePickerBack);
    	this.back.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				folderBack();
			}	
    	});
		mDirectory = start.getSDFolder();
		refreshFilesList();
		return layout;
    }
    public void showFiles(File directory){
    	mDirectory = directory;
    	refreshFilesList();
    }
    
    protected void refreshFilesList() {
    	adapter.clear();
		ExtensionFilenameFilter filter = new ExtensionFilenameFilter(TypeMagic.ACCEPTED_TYPES);
		File[] files = mDirectory.listFiles(filter);
		if(files != null && files.length > 0) {
			for(File f : files) {
				if(f.isHidden()) continue;
				LinearLayout newItem;
				newItem = new FileItem(start,f,this);
				adapter.addItem((FileItem) newItem);
			}
		}
		container.setAdapter(adapter);
		registerForContextMenu(container);
	}
    
    public void folderBack(){
    	if(!mDirectory.getName().equalsIgnoreCase(start.getSDFolder().getName())){
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
				return true;
			}
			if(mExtensions != null && mExtensions.length > 0) {
				for(int i = 0; i < mExtensions.length; i++) {
					if(filename.endsWith(mExtensions[i])) return true;
				}
				return false;
			}
			return true;
		}
	}
	@Override
	public void onSelect() {}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    menu.getItem(4).setVisible(false);
	    menu.getItem(5).setVisible(false);
	}
}