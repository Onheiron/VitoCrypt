package activities;

import java.io.File;
import java.io.IOException;

import com.example.vitocrypt.R;

import support.FileItem;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import cypher.Protector;

public abstract class FastFragment extends GenericFragment{

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
	}
	
	class LoadProtected extends AsyncTask<String,FileItem,Integer>{
		
		@Override
		protected Integer doInBackground(String... password) {
			File[] protectedFiles = currentDirectory.listFiles();
			Protector protector;
			try{
				protector = new Protector(start.getIMSI() + password[0]);
			}catch(ArrayIndexOutOfBoundsException noPW){
				protector = new Protector(start.getIMSI());
			}
	        for(File file : protectedFiles){
	        	try {
	        		FileItem newFileItem = new FileItem(start,new File("sdcard/VitoCrypt/TMP/" + file.getName()),thisFragment);
	        		newFileItem.ShowLoading();
	        		protector.UnProtect(file, newFileItem,(float) 0.1);
	        		publishProgress(newFileItem);
				} catch (IOException e) {
					continue;
				}
	        }
			return null;
		}
		protected void onProgressUpdate(FileItem... files) {
	         adapter.addItem(files[0]);
	         container.setAdapter(adapter);
	     }
		@Override
		protected void onPostExecute(Integer result) {
			registerForContextMenu(container);
	        HideLoading();
		}
    	
    }
	
}
