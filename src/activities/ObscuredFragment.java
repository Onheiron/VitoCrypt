package activities;

import com.example.vitocrypt.R;
import com.example.vitocrypt.Start;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
 
public class ObscuredFragment extends GenericFragment {
 
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
 
    public static Bundle createBundle( String title ) {
        Bundle bundle = new Bundle();
        bundle.putString( EXTRA_TITLE, title );
        return bundle;
    }

	@Override
	public void onSelect() {
		// TODO Auto-generated method stub
		
	}
 
}
