package activities;

import java.io.File;
import java.io.IOException;

import support.FileItem;

import chiper.Protector;

import com.example.vitocrypt.R;
import com.example.vitocrypt.Start;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
 
public class ProtectedFragment extends Fragment {
 
    public static final String EXTRA_TITLE = "title";
    private LayoutInflater inflater;
    LinearLayout layout;
    LinearLayout fileItemContainer;
    Context context;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//    	File scrap = new File(Environment.getExternalStorageDirectory() + "/VitoCrypt/TMP/");
//    	for(File file: scrap.listFiles()) file.delete();
    	this.context = inflater.getContext();
        layout = (LinearLayout) inflater.inflate(R.layout.protected_layout, null);
        fileItemContainer = (LinearLayout)layout.findViewById(R.id.fileItemContainer);
//        caricamento.show();
//        LoadProtected fileLoader = new LoadProtected();
//        fileLoader.execute();
        this.inflater = inflater;
        return layout;
    }
    
    public void onPause(){
    	super.onPause();
    }
    public void onResume(){
    	super.onResume();
    	File scrap = new File(Environment.getExternalStorageDirectory() + "/VitoCrypt/TMP/");
    	for(File file: scrap.listFiles()) file.delete();
    	fileItemContainer.removeAllViews();
        LoadProtected fileLoader = new LoadProtected();
        fileLoader.execute();
    }
    
    public static Bundle createBundle( String title ) {
        Bundle bundle = new Bundle();
        bundle.putString( EXTRA_TITLE, title );
        return bundle;
    }
    
    private class LoadProtected extends AsyncTask<Integer,Integer,Integer>{

		@Override
		protected Integer doInBackground(Integer... params) {
			File[] protectedFiles = new File(Environment.getExternalStorageDirectory() + "/VitoCrypt/PRT/").listFiles();
	        SharedPreferences IMSIPref = inflater.getContext().getSharedPreferences("VitoCrypt", 0);
	        Protector protector = new Protector(IMSIPref.getString("userImsi", "123456789"));
	        for(File file : protectedFiles){
	        	try {
					protector.UnProtect(file);
				} catch (IOException e) {
					continue;
				}
	        }
			return null;
		}
		
		protected void onPostExecute(Integer result) {
	        File[] tempFiles = new File(Environment.getExternalStorageDirectory() + "/VitoCrypt/TMP/").listFiles();
	        for(File file : tempFiles){
	        	FileItem fileItem = new FileItem(inflater.getContext(),file);
	        	fileItemContainer.addView(fileItem);
	        }
		}
    	
    }
 
}