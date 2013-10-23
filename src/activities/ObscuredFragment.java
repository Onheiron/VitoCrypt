package activities;

import com.example.vitocrypt.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.EditText;
 
public class ObscuredFragment extends FastFragment {
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View result = super.onCreateView(inflater, container, savedInstanceState);
		this.setDirectory(start.getShadeDirectory());
		return result;
	}
	
	@Override
	public void onSelect() {
    	super.onSelect();
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
    
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    menu.getItem(2).setVisible(false);
	}
 
}
