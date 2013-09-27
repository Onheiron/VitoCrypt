package support;

import java.io.File;
import java.io.IOException;

import com.example.vitocrypt.R;

import activities.AddFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddFileItem extends FileItem{
	public AddFileItem(Context context, AddFragment caller) { super(context, caller);}
	public AddFileItem(Context context, File file, AddFragment caller){ super(context, file, caller);}
	@Override
	public void fileClick() {
		try {
			protectFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void protectFile() throws IOException{
    	((AddFragment) caller).protectFile(file,this);
	}
}
