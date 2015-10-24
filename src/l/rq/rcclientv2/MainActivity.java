package l.rq.rcclientv2;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity{
			
	/*public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        MainAdapter adapter = new MainAdapter(MainActivity.this);
        GridView gridView = (GridView) findViewById(R.id.MainActivityGrid);
        gridView.setAdapter(adapter);
	}*/
	
    private String texts[] = null;
    private int images[] = null;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        images=new int[]{  
    			//九宫格图片的设置  
    			R.drawable.icon_mouse,
    			R.drawable.icon_dos,
    			R.drawable.icon_movie,
    			R.drawable.icon_ppt,
    			R.drawable.icon_screen,
    			R.drawable.icon_picture,
    			R.drawable.icon_video,
    			R.drawable.icon_1,
    			R.drawable.icon_other,
    		};  
        texts = new String[]{  
        		//九宫格图片下方文字的设置  
        		"触摸板",  
        		"DOS",  
        		"电影",  
        		"PPT",  
        		"截屏",  
        		"拍照",
        		"录像",
        		"icon8",  
        		"icon9",  
        	};  

        GridView gridview = (GridView) findViewById(R.id.MainActivityGrid);
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < images.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", images[i]);
            map.put("itemText", texts[i]);
            lstImageItem.add(map);
        }
        
        SimpleAdapter adapter = new SimpleAdapter(this, 
                lstImageItem,// 数据源
                R.layout.item,// 显示布局
                new String[] { "itemImage", "itemText" }, 
                new int[] { R.id.MainActivityImage, R.id.MainActivityText }); 
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new ItemClickListener());
    }

    class ItemClickListener implements OnItemClickListener {
        /**
         * 点击项时触发事件
         * 
         * @param parent  发生点击动作的AdapterView
         * @param view 在AdapterView中被点击的视图(它是由adapter提供的一个视图)。
         * @param position 视图在adapter中的位置。
         * @param rowid 被点击元素的行id。
         */
        public void onItemClick(AdapterView<?> parent, View view, int position, long rowid) {
            HashMap<String, Object> item = (HashMap<String, Object>) parent.getItemAtPosition(position);
            //获取数据源的属性值
            String itemText=(String)item.get("itemText");
            Object object=item.get("itemImage");
            Toast.makeText(MainActivity.this, itemText, Toast.LENGTH_LONG).show();
            
            //根据图片进行相应的跳转
            switch (images[position]) {
            case R.drawable.icon_mouse:
                startActivity(new Intent(MainActivity.this, MouseActivity.class));//启动另一个Activity
                finish();//结束此Activity，可回收
                break;
            case R.drawable.icon_dos:
                startActivity(new Intent(MainActivity.this, DOSActivity.class));//启动另一个Activity
                finish();//结束此Activity，可回收
                break;
            case R.drawable.icon_movie:
                startActivity(new Intent(MainActivity.this, MovieActivity.class));//启动另一个Activity
                finish();//结束此Activity，可回收
                break;
            case R.drawable.icon_ppt:
                startActivity(new Intent(MainActivity.this, PPTActivity.class));//启动另一个Activity
                finish();//结束此Activity，可回收
                break;
            case R.drawable.icon_screen:
                startActivity(new Intent(MainActivity.this, ScreenActivity.class));//启动另一个Activity
                finish();//结束此Activity，可回收
                break;
            case R.drawable.icon_picture:
                startActivity(new Intent(MainActivity.this, MouseActivity.class));//启动另一个Activity
                finish();//结束此Activity，可回收
                break;
            }
            
        }
    }
	

}
