package activities;

import java.io.File;
import java.io.IOException;

import support.ProtectFileItem;

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
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
 
public class ProtectedFragment extends GenericFragment {
 
    public static final String EXTRA_TITLE = "title";
    private LayoutInflater inflater;
    LinearLayout layout;
    LinearLayout fileItemContainer;
    Fragment currentFragment = this;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	super.onCreate(inflater.getContext());
        layout = (LinearLayout) inflater.inflate(R.layout.protected_layout, null);
        fileItemContainer = (LinearLayout)layout.findViewById(R.id.fileItemContainer);
        this.inflater = inflater;
        return layout;
    }
    
	@Override
	public void onSelect() {
    	fileItemContainer.removeAllViews();
        LoadProtected fileLoader = new LoadProtected();
        ShowLoading();
        fileLoader.execute();
	}
    
    private class LoadProtected extends AsyncTask<Integer,Integer,Integer>{

		@Override
		protected Integer doInBackground(Integer... params) {
			File[] protectedFiles = new File(Environment.getExternalStorageDirectory() + "/VitoCrypt/PRT/").listFiles();
			TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
	    	String imsi = mTelephonyMgr.getSubscriberId();
	    	Protector protector = new Protector(imsi);
	        for(File file : protectedFiles){
	        	try {
					protector.UnProtect(file,(float) 0.1);
				} catch (IOException e) {
					continue;
				}
	        }
			return null;
		}
		
		protected void onPostExecute(Integer result) {
	        File[] tempFiles = new File(Environment.getExternalStorageDirectory() + "/VitoCrypt/TMP/").listFiles();
	        for(File file : tempFiles){
	        	ProtectFileItem fileItem = new ProtectFileItem(inflater.getContext(),file, currentFragment);
	        	fileItemContainer.addView(fileItem);
	        }
	        HideLoading();
		}
    	
    }
 
}