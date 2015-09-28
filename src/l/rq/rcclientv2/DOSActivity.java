package l.rq.rcclientv2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DOSActivity extends Activity {
	private EditText inputET;
	private Button dosButton;
	ActionBar actionBar;
	//private Button titleBackButton;
	private String volumedownkey =  "leftButton";
	private String volumeupkey =  "leftButton";
	int [] images = new int [] {
			R.drawable.windows,
			R.drawable.screen
	};
	int currentImg = 0;
	private DatagramSocket socket;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dos);
		inputET = (EditText) findViewById(R.id.InputEditText);
		dosButton = (Button) findViewById(R.id.dosButton);
		actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.keyboard);
		//titleBackButton = (Button) findViewById(R.id.head_TitleBackBtn);
		
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		dosButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String s = inputET.getText().toString();
				if (s == null || s.equals("")) {
					Toast.makeText(DOSActivity.this, "��ϢΪ��",
							Toast.LENGTH_SHORT).show();
					return;
				}				
				new Thread(runnable).start();        									       
			}
			
			Runnable runnable = new Runnable(){
      		  @Override
      		  public void run() {
      			  String s = inputET.getText().toString();
      			  sendMessage("keyboard:dosmessage," + s);
      		  }
			};

		});		
		
		/*titleBackButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {	
				doBack();
			}

		});	*/
	}

	/*
	 * public void onclick(View v){ switch(v.getId()){ case R.id.UP:
	 * 
	 * break;
	 * 
	 * case R.id.DOWN: if(isUSLR) sendMessage("keyboard:key,Down"); else
	 * sendMessage("keyboard:key,S"); break;
	 * 
	 * case R.id.LEFT: if(isUSLR) sendMessage("keyboard:key,Left"); else
	 * sendMessage("keyboard:key,A"); break; case R.id.RIGHT: if(isUSLR)
	 * sendMessage("keyboard:key,Right"); else sendMessage("keyboard:key,D");
	 * break; }
	 * 
	 * }
	 */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			if(volumedownkey.equals("leftButton"))
				sendMessage("leftButton:down");
			else if(volumedownkey.equals("rightButton"))
				sendMessage("rightButton:down");
			else
				sendMessage("keyboard:key,"+volumedownkey+",down");
			return true;

		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {

			if(volumeupkey.equals("leftButton"))
				sendMessage("leftButton:down");
			else if(volumeupkey.equals("rightButton"))
				sendMessage("rightButton:down");
			else
				sendMessage("keyboard:key,"+volumeupkey+",down");
			return true;

		} else if( keyCode== KeyEvent.KEYCODE_HOME){
			return true;
		} else if( keyCode== KeyEvent.KEYCODE_BACK){
			return true;
		} 
		
		return super.onKeyDown(keyCode, event);

	}
	
	
	 /*@Override  
	 public void onAttachedToWindow() {  
	        
	     this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);  
	        
	     super.onAttachedToWindow();  
	 } */
	
	

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			if(volumedownkey.equals("leftButton"))
				sendMessage("leftButton:release");
			else if(volumedownkey.equals("rightButton"))
				sendMessage("rightButton:release");
			else
				sendMessage("keyboard:key,"+volumedownkey+",up");
			return true;

		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {

			if(volumeupkey.equals("leftButton"))
				sendMessage("leftButton:release");
			else if(volumeupkey.equals("rightButton"))
				sendMessage("rightButton:release");
			else
				sendMessage("keyboard:key,"+volumeupkey+",up");
			return true;

		} 
		
		return super.onKeyUp(keyCode, event);
	}

	private void sendMessage(String str) {
		try {
			// ���ȴ���һ��DatagramSocket����
			
			// ����һ��InetAddree
			InetAddress serverAddress = InetAddress.getByName(Settings.ipnum);
			byte data[] = str.getBytes();
			// ����һ��DatagramPacket���󣬲�ָ��Ҫ��������ݰ����͵����統�е��ĸ���ַ���Լ��˿ں�
			DatagramPacket packet = new DatagramPacket(data, data.length,
					serverAddress, Settings.socketnum);
			// ����socket�����send��������������
			socket.send(packet);
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.dosmenu, menu);
		return true;
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
			
		case R.id.about:
			about();
			return true;		
		case R.id.help:
			help();
			return true;		
		case R.id.reback:
			doBack();
			return true;
		}
		return false;
	}
	
	/**
	 * ��ʾ��������
	 */
	public void about() {
		new AlertDialog.Builder(DOSActivity.this)
				.setTitle("��������")
				.setMessage("��ӭʹ�� ң��С����  \n" + 
						"�����ߣ�����Ⱥ \n" +
						"�������䣺445647390@qq.com \n" +
						"��л����֧��")
				.setIcon(R.drawable.icon)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						// finish();
					}
				})
				.setNegativeButton("����", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}

				}).show();
	}

	private void help(){
		new AlertDialog.Builder(DOSActivity.this).setTitle("ʹ�ð���")
		.setMessage("��ҳ��ʵ��������Է���DOS����Ĺ��ܣ�������������ӦDOS������ţ�������ͼ��ɡ�\n " +
				"��������0��ע�� \n" +
				"��������1���ػ� \n" +
				"��������2������ \n" +
				"��������3����ֹϵͳ�ر�").setIcon(R.drawable.icon)
		.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// finish();
			}
		}).setNegativeButton("����",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						// TODO Auto-generated method stub

					}

				}).show();
	}
			
	
	private void doBack(){
		 Intent intent = new Intent(DOSActivity.this,MouseActivity.class);
		 DOSActivity.this.startActivity(intent);
		 this.finish();
	}
	
	
}

