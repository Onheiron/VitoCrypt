package support;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

public class ProtectFileItem extends FileItem{
	public ProtectFileItem(Context context, Fragment caller) { super(context, caller); }
	public ProtectFileItem(Context context,File file, Fragment caller) { super(context, file, caller); }
	@Override
	public void fileClick() {
		openFile();
	}
	public void openFile(){
    	Intent intent = new Intent();
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), fileType + "/*");
		context.startActivity(intent);
	}
}
