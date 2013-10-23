package activities;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;

import support.FileItem;

import com.example.vitocrypt.R;

import cypher.Encrypter;
import cypher.Protector;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
 
public class CryptedFragment extends GenericFragment {
 
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = (LinearLayout) inflater.inflate(R.layout.protected_layout, null);
        super.onCreate(inflater.getContext(), (ListView)layout.findViewById(R.id.protectedListView));
        this.container.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			public void onItemClick(AdapterView<?> AdapterView, View SelectedView, int position, long id) {
				FileItem item = (FileItem) SelectedView;
				if(!item.isLoading()) start.openContextMenu(item);
			}
		});
        this.setDirectory(start.getCryptoDirectory());
        return layout;
    }
    
	@Override
	public void onSelect() {
    	adapter.clear();
    	container.setAdapter(adapter);
    	
    	// get prompts.xml view
		LayoutInflater li = LayoutInflater.from(start);
		View promptsView = li.inflate(R.layout.single_password_input_dialog, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(start);
		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);
		final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
		// set dialog message
		alertDialogBuilder
		.setCancelable(false)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog,int id) {
		    	// get user input and set it to result
		    	// edit text
		    	LoadProtected fileLoader = new LoadProtected();
		        ShowLoading();
		        fileLoader.execute(userInput.getText().toString());
		    }
		})
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog,int id) {
			dialog.cancel();
		    }
		});
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		// show it
		alertDialog.show();
    	
	}
    
    private class LoadProtected extends AsyncTask<String,FileItem,Integer>{

		@Override
		protected Integer doInBackground(String... password) {
			File[] protectedFiles = start.getCryptoDirectory().listFiles();
	    	Encrypter protector;
			try {
				protector = new Encrypter(start.getIMSI()+password[0]);
				for(File file : protectedFiles){
		        	FileItem newFileItem = new FileItem(start,new File("sdcard/VitoCrypt/TMP/" + file.getName()),thisFragment);
					protector.Decrypt(file, newFileItem);
					publishProgress(newFileItem);
		        }
			} catch (InvalidKeyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
    
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    menu.getItem(3).setVisible(false);
	}
 
}
