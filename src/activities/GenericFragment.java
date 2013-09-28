package activities;

import com.example.vitocrypt.R;
import com.example.vitocrypt.Start;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;

public abstract class GenericFragment extends Fragment{
	
	Dialog caricamento;
	Start context;
	LinearLayout layout;
	LinearLayout container;
	
	public void onCreate(Context context){
		this.context = (Start) context;
		caricamento = new Dialog(context,R.style.caricamento);
		caricamento.setContentView(R.layout.caricamento);
		caricamento.setCancelable(false);
	}
	
	public void ShowLoading(){ this.caricamento.show(); }
	public void HideLoading(){ this.caricamento.dismiss(); }
	public abstract void onSelect();

}
