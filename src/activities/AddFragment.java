package activities;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import magic.TypeMagic;
import support.AddDirItem;
import support.AddFileItem;
import chiper.Protector;
import com.example.vitocrypt.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
 
public class AddFragment extends GenericFragment {
 
	protected File mDirectory;
	LinearLayout back;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	super.onCreate(inflater.getContext());
    	layout = (LinearLayout) inflater.inflate(R.layout.file_picker, layout);
    	this.container = (LinearLayout) layout.findViewById(R.id.fileItemContainer);
    	this.back = (LinearLayout) layout.findViewById(R.id.filePickerBack);
    	this.back.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				folderBack();
			}	
    	});
		mDirectory = context.getSDFolder();
		refreshFilesList();
		return layout;
    }
    public void showFiles(File directory){
    	mDirectory = directory;
    	refreshFilesList();
    }
    
    public void protectFile(File file, AddFileItem element) throws IOException{
    	Protector protector;
    	protector = new Protector(context.getIMSI());
    	protector.Protect(file,(float) 0.1);
		file.delete();
		element.setBackgroundResource(R.color.green);
    }
    
    protected void refreshFilesList() {
    	container.removeAllViews();
		ExtensionFilenameFilter filter = new ExtensionFilenameFilter(TypeMagic.ACCEPTED_TYPES);
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
	}
    
    public void folderBack(){
    	if(!mDirectory.getName().equalsIgnoreCase(context.getSDFolder().getName())){
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
    
}