package activities;

import java.io.IOException;
import java.security.InvalidKeyException;

import support.FileItem;
import support.FileItemAdapter;

import chiper.Encrypter;
import chiper.Protector;

import com.example.vitocrypt.R;
import com.example.vitocrypt.Start;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public abstract class GenericFragment extends Fragment{
	
	Dialog caricamento;
	Start start;
	LinearLayout layout;
	ListView container;
	FileItemAdapter adapter;
	Fragment thisFragment = this;
	
	public void onCreate(Context context, ListView container){
		this.start = (Start) context;
		caricamento = new Dialog(context,R.style.caricamento);
		caricamento.setContentView(R.layout.caricamento);
		caricamento.setCancelable(false);
		this.container = container;
		adapter = new FileItemAdapter();
		container.setAdapter(adapter);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = start.getMenuInflater();
	    inflater.inflate(R.menu.file_item_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    final FileItem fileItem = (FileItem)info.targetView;
	    
	    // get prompts.xml view
		LayoutInflater li = LayoutInflater.from(start);
		View promptsView = li.inflate(R.layout.single_password_input_dialog, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(start);
		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);
		final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
		// set dialog message
	    
	    switch (item.getItemId()) {
	        case R.id.view:
	        	Intent intent = new Intent();
	    		intent.setAction(android.content.Intent.ACTION_VIEW);
	    		intent.setDataAndType(Uri.fromFile(fileItem.getFile()), fileItem.getFileType() + "/*");
	    		start.startActivity(intent);
	            return true;
	        case R.id.protect:
				try {
					fileItem.ShowLoading();
					Protector protector = new Protector(start.getIMSI());
			    	protector.Protect(fileItem,(float) 0.1);
				} catch (IOException e) {
					e.printStackTrace();
				}
	            return true;
	        case R.id.obscure:
				alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
				    	// get user input and set it to result
				    	// edit text
				    	try {
							fileItem.ShowLoading();
							Protector protector = new Protector(start.getIMSI() + userInput.getText().toString());
					    	protector.Obscure(fileItem,(float) 0.1);
						} catch (IOException e) {
							e.printStackTrace();
						}
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
	            return true;
	        case R.id.crypt:
				alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
				    	// get user input and set it to result
				    	// edit text
				    	try {
							fileItem.ShowLoading();
							Encrypter protector = new Encrypter(start.getIMSI() + userInput.getText().toString());
					    	protector.Encrypt(fileItem);
						} catch (InvalidKeyException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    }
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				    }
				});
				// create alert dialog
				AlertDialog alertDialog2 = alertDialogBuilder.create();
				// show it
				alertDialog2.show();
	            return true;
	        
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
	
	public void ShowLoading(){ this.caricamento.show(); }
	public void HideLoading(){ this.caricamento.dismiss(); }
	public Start getContext(){ return this.start; }
	public void refreshAdapter() { adapter.notifyDataSetChanged(); }
	public abstract void onSelect();

}
