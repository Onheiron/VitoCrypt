package activities;

import java.io.File;
import java.io.IOException;

import support.FileItem;
import chiper.Protector;
import com.example.vitocrypt.R;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
 
public class ProtectedFragment extends GenericFragment {
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = (LinearLayout) inflater.inflate(R.layout.protected_layout, null);
        super.onCreate(inflater.getContext(), (ListView)layout.findViewById(R.id.protectedListView));
        this.container.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			public void onItemClick(AdapterView<?> AdapterView, View SelectedView, int position, long id) {
				FileItem item = (FileItem) SelectedView;
				start.openContextMenu(item);
			}
		});
        return layout;
    }
    
	@Override
	public void onSelect() {
    	adapter.clear();
    	container.setAdapter(adapter);
        LoadProtected fileLoader = new LoadProtected();
        ShowLoading();
        fileLoader.execute();
	}
    
    private class LoadProtected extends AsyncTask<Integer,File,Integer>{

		@Override
		protected Integer doInBackground(Integer... params) {
			File[] protectedFiles = start.getProtectDirectory().listFiles();
	    	Protector protector = new Protector(start.getIMSI());
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
	         FileItem fileItem = new FileItem(start,files[0], thisFragment);
	         adapter.addItem(fileItem);
	         container.setAdapter(adapter);
	     }
		@Override
		protected void onPostExecute(Integer result) {
			registerForContextMenu(container);
	        HideLoading();
		}
    	
    }
    
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    menu.getItem(1).setVisible(false);
	}
 
}