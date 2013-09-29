package activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.TextView;
 
public class CryptedFragment extends GenericFragment {
 
    public static final String EXTRA_TITLE = "title";
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView txt = new TextView( inflater.getContext() );
        txt.setGravity( Gravity.CENTER );
        txt.setText( "Fragment" );
 
        if ( getArguments() != null && getArguments().containsKey( EXTRA_TITLE ) ) {
            txt.setText( getArguments().getString( EXTRA_TITLE ) );
        }
        return txt;
    }
 
    public static Bundle createBundle( String title ) {
        Bundle bundle = new Bundle();
        bundle.putString( EXTRA_TITLE, title );
        return bundle;
    }

	@Override
	public void onSelect() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    menu.getItem(3).setVisible(false);
	}
 
}
