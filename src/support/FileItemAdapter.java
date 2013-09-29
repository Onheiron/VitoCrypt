package support;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class FileItemAdapter extends BaseAdapter{

	List<FileItem> items = new ArrayList<FileItem>();
	
	public int getCount() {
		return items.size();
	}

	public Object getItem(int position) {
		return items.get(position);
	}

	public long getItemId(int position) {
		return items.get(position).getId();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		return items.get(position);
	}
	
	public void addItem(FileItem item){
		this.items.add(item);
	}
	
	public void clear(){
		this.items.clear();
	}

}
