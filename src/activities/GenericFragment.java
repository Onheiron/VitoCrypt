package activities;

import java.io.IOException;

import support.FileItem;
import support.FileItemAdapter;

import chiper.Protector;

import com.example.vitocrypt.R;
import com.example.vitocrypt.Start;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
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
	    FileItem fileItem = (FileItem)info.targetView;
	    switch (item.getItemId()) {
	        case R.id.view:
	        	Intent intent = new Intent();
	    		intent.setAction(android.content.Intent.ACTION_VIEW);
	    		intent.setDataAndType(Uri.fromFile(fileItem.getFile()), fileItem.getFileType() + "/*");
	    		start.startActivity(intent);
	            return true;
	        case R.id.protect:
				try {
					Protector protector = new Protector(start.getIMSI());
			    	protector.Protect(fileItem,(float) 0.1);
				} catch (IOException e) {
					e.printStackTrace();
				}
	            return true;
	        case R.id.obscure:
				try {
					Protector protector = new Protector(start.getIMSI());
			    	protector.Protect(fileItem,(float) 0.1);
				} catch (IOException e) {
					e.printStackTrace();
				}
	            return true;
	        case R.id.crypt:
				try {
					Protector protector = new Protector(start.getIMSI());
			    	protector.Protect(fileItem,(float) 0.1);
				} catch (IOException e) {
					e.printStackTrace();
				}
	            return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
	
	public void ShowLoading(){ this.caricamento.show(); }
	public void HideLoading(){ this.caricamento.dismiss(); }
	public Start getContext(){ return this.start; }
	public abstract void onSelect();

}
