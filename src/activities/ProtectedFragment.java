package activities;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
 
public class ProtectedFragment extends FastFragment {
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View result = super.onCreateView(inflater, container, savedInstanceState);
		this.setDirectory(start.getProtectDirectory());
		return result;
	}
	
	@Override
	public void onSelect() {
    	super.onSelect();
        LoadProtected fileLoader = new LoadProtected();
        ShowLoading();
        fileLoader.execute();
	}
    
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    menu.getItem(1).setVisible(false);
	}
}