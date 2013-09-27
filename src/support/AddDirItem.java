package support;

import java.io.File;

import com.example.vitocrypt.R;

import activities.AddFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddDirItem extends FileItem{
	public AddDirItem(Context context, Fragment caller) { super(context, caller); }
	public AddDirItem(Context context, File file, Fragment caller) { super(context, file, caller); }
	@Override
	public void fileClick() {
		openDirectory();
	}
	public void openDirectory(){
    	((AddFragment) caller).showFiles(file);
	}
}
