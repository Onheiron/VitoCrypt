package activities;

import com.example.vitocrypt.R;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;

public abstract class GenericFragment extends Fragment{
	
	boolean created = false;
	Dialog caricamento;
	Context context;
	LinearLayout layout;
	LinearLayout container;
	
	public void onCreate(Context context){
		this.created = true;
		this.context = context;
		caricamento = new Dialog(context,R.style.caricamento);//costruttore
		caricamento.setContentView(R.layout.caricamento);//assegno il layout
		//impongo che l'utente non possa chiudere la dialog di caricamento con il back button
		caricamento.setCancelable(false);
	}
	
	public void ShowLoading(){
		this.caricamento.show();
	}
	public void HideLoading(){
		this.caricamento.dismiss();
	}
	
	public abstract void onSelect();

}
