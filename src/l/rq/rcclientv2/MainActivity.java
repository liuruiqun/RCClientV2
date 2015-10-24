package l.rq.rcclientv2;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
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
    ActionBar actionBar;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.main);
        
        images=new int[]{  
    			//�Ź���ͼƬ������  
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
        		//�Ź���ͼƬ�·����ֵ�����  
        		"������",  
        		"DOS",  
        		"��Ӱ",  
        		"PPT",  
        		"����",  
        		"����",
        		"¼��",
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
                lstImageItem,// ����Դ
                R.layout.item,// ��ʾ����
                new String[] { "itemImage", "itemText" }, 
                new int[] { R.id.MainActivityImage, R.id.MainActivityText }); 
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new ItemClickListener());
    }

    class ItemClickListener implements OnItemClickListener {
        /**
         * �����ʱ�����¼�
         * 
         * @param parent  �������������AdapterView
         * @param view ��AdapterView�б��������ͼ(������adapter�ṩ��һ����ͼ)��
         * @param position ��ͼ��adapter�е�λ�á�
         * @param rowid �����Ԫ�ص���id��
         */
        public void onItemClick(AdapterView<?> parent, View view, int position, long rowid) {
            HashMap<String, Object> item = (HashMap<String, Object>) parent.getItemAtPosition(position);
            //��ȡ����Դ������ֵ
            String itemText=(String)item.get("itemText");
            Object object=item.get("itemImage");
            Toast.makeText(MainActivity.this, itemText, Toast.LENGTH_LONG).show();
            
            //����ͼƬ������Ӧ����ת
            switch (images[position]) {
            case R.drawable.icon_mouse:
                startActivity(new Intent(MainActivity.this, MouseActivity.class));//������һ��Activity
                finish();//������Activity���ɻ���
                break;
            case R.drawable.icon_dos:
                startActivity(new Intent(MainActivity.this, DOSActivity.class));//������һ��Activity
                finish();//������Activity���ɻ���
                break;
            case R.drawable.icon_movie:
                startActivity(new Intent(MainActivity.this, MovieActivity.class));//������һ��Activity
                finish();//������Activity���ɻ���
                break;
            case R.drawable.icon_ppt:
                startActivity(new Intent(MainActivity.this, PPTActivity.class));//������һ��Activity
                finish();//������Activity���ɻ���
                break;
            case R.drawable.icon_screen:
                startActivity(new Intent(MainActivity.this, ScreenActivity.class));//������һ��Activity
                finish();//������Activity���ɻ���
                break;
            case R.drawable.icon_picture:
                startActivity(new Intent(MainActivity.this, PictureActivity.class));//������һ��Activity
                finish();//������Activity���ɻ���
                break;
            }
            
        }
    }
	
    private long exitTime = 0;
    public boolean onKeyDown(int keyCode, KeyEvent event) {

		if( keyCode== KeyEvent.KEYCODE_HOME){
			return true;
		} else if( keyCode== KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			if((System.currentTimeMillis()- exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	            System.exit(0);
	        }
			return true;
		} 	
		return super.onKeyDown(keyCode, event);
	}
    
    
    /**
	 * ��׽�˵��¼�
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			doBack();
			return true;
		}
		return false;
	}
    
    private void doBack(){
		 Intent intent = new Intent(MainActivity.this,ConnectActivity.class);
		 MainActivity.this.startActivity(intent);
		 this.finish();
	}
}
