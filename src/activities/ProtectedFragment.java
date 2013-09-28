package activities;

import java.io.File;
import java.io.IOException;

import support.ProtectFileItem;

import chiper.Protector;

import com.example.vitocrypt.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
 
public class ProtectedFragment extends GenericFragment {
 
    LinearLayout fileItemContainer;
    Fragment thisFragment = this;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	super.onCreate(inflater.getContext());
        layout = (LinearLayout) inflater.inflate(R.layout.protected_layout, null);
        fileItemContainer = (LinearLayout)layout.findViewById(R.id.fileItemContainer);
        return layout;
    }
    
	@Override
	public void onSelect() {
    	fileItemContainer.removeAllViews();
        LoadProtected fileLoader = new LoadProtected();
        ShowLoading();
        fileLoader.execute();
	}
    
    private class LoadProtected extends AsyncTask<Integer,File,Integer>{

		@Override
		protected Integer doInBackground(Integer... params) {
			File[] protectedFiles = context.getProtectDirectory().listFiles(); //new File(Environment.getExternalStorageDirectory() + "/VitoCrypt/PRT/").listFiles();
	    	Protector protector = new Protector(context.getIMSI());
	        for(File file : protectedFiles){
	        	try {
	        		publishProgress(protector.UnProtect(file,(float) 0.1));
				} catch (IOException e) {
					continue;
				}
	        }
			return null;
		}
		protected void onProgressUpdate(File... files) {
	         ProtectFileItem fileItem = new ProtectFileItem(context,files[0], thisFragment);
	         fileItemContainer.addView(fileItem);
	     }
		@Override
		protected void onPostExecute(Integer result) {
	        HideLoading();
		}
    	
    }
 
}