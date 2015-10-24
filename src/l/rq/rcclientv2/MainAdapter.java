package l.rq.rcclientv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainAdapter extends BaseAdapter {
	private Context context;  
	   
	public MainAdapter(Context context) {  
		this.context=context;  
	}  

	/*private Integer[] images = {  
		//�Ź���ͼƬ������  
		R.drawable.iconfont_music,  
		R.drawable.iconfont_edit,  
		R.drawable.iconfont_play,  
		R.drawable.iconfont_browser,  
		R.drawable.iconfont_camera,  
		R.drawable.iconfont_computer,  
		R.drawable.iconfont_video,  
		R.drawable.iconfont_pic,  
		R.drawable.iconfont_folder,  
	};  */
	private Integer[] images = {  
			//�Ź���ͼƬ������  
			R.drawable.icon,  
			R.drawable.icon,   
			R.drawable.icon,   
			R.drawable.icon,  
			R.drawable.icon,  
			R.drawable.icon,   
			R.drawable.icon,   
			R.drawable.icon,  
			R.drawable.icon,  
		};  
			   
	private String[] texts = {  
		//�Ź���ͼƬ�·����ֵ�����  
		"icon1",  
		"icon2",  
		"icon3",  
		"icon4",  
		"icon5",  
		"icon6",  
		"icon7",  
		"icon8",  
		"icon9",  
	};  
	
	@Override  
	public int getCount() {  
		return images.length;  
	}  
	 
	 @Override  
	public Object getItem(int position) {  
		 return position;  
	}  
	 
	//get the current selector's id number  
	@Override  
	public long getItemId(int position) {  
		return position;  
	}  
	
	@Override  
	public View getView(int position, View view, ViewGroup viewgroup) {  
		ImgTextWrapper wrapper;  
		if(view==null) {  
			wrapper = new ImgTextWrapper();  
		LayoutInflater inflater = LayoutInflater.from(context);  
		view = inflater.inflate(R.layout.item, null);  
		view.setTag(wrapper);  
		view.setPadding(15, 15, 15, 15);  //ÿ��ļ��  
		} else {  
			wrapper = (ImgTextWrapper)view.getTag();  
		}  
	    
		wrapper.imageView = (ImageView)view.findViewById(R.id.MainActivityImage);  
		wrapper.imageView.setBackgroundResource(images[position]);  
		wrapper.textView = (TextView)view.findViewById(R.id.MainActivityText);  
		wrapper.textView.setText(texts[position]);  
	    
		return view;  
	}  
}

class ImgTextWrapper {  
	ImageView imageView;  
	TextView textView;  	   
}  
