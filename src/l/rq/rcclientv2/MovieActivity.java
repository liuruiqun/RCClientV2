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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MovieActivity extends Activity{	
	private Button startButton;
	private Button upButton;
	private Button downButton;
	private Button leftButton;
	private Button rightButton;
	ActionBar actionBar;
	//private Button titleBackButton;
	//private Button titleAddButton;
	
	private DatagramSocket socket;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie);
		actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.movie);
		startButton = (Button) findViewById(R.id.startbutton);
		upButton = (Button) findViewById(R.id.volumeupbutton);
		downButton = (Button) findViewById(R.id.volumedownbutton);
		leftButton = (Button) findViewById(R.id.backbutton);
		rightButton = (Button) findViewById(R.id.forbutton);
		//titleBackButton = (Button) findViewById(R.id.head_TitleBackBtn);
		//titleAddButton = (Button) findViewById(R.id.head_TitleAddBtn);
		
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		startButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
        			new Thread(runnable).start();
        			
        		} catch (Exception e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
				
			}
				
			Runnable runnable = new Runnable(){
      		  @Override
      		  public void run() {
      		    
      			sendMessage("movie:key,Start,click");
      		  }
			};
		});
		
		
		upButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
        			new Thread(runnable).start();
        			
        		} catch (Exception e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
				
			}
				
			Runnable runnable = new Runnable(){
      		  @Override
      		  public void run() {
      		    
      			sendMessage("movie:key,Up,click");
      		  }
			};

		});
		
		downButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
        			new Thread(runnable).start();
        			
        		} catch (Exception e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
				
			}
				
			Runnable runnable = new Runnable(){
      		  @Override
      		  public void run() {
      		    
      			sendMessage("movie:key,Down,click");
      		  }
			};

		});
		
		leftButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
        			new Thread(runnable).start();
        			
        		} catch (Exception e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
				
			}
				
			Runnable runnable = new Runnable(){
      		  @Override
      		  public void run() {
      		    
      			sendMessage("movie:key,Left,click");
      		  }
			};		

		});
		
		rightButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
        			new Thread(runnable).start();
        			
        		} catch (Exception e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
				
			}
				
			Runnable runnable = new Runnable(){
      		  @Override
      		  public void run() {
      		    
      			sendMessage("movie:key,Right,click");
      		  }
			};		

		});
		
		/*titleBackButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {	
				doBack();
			}

		});	
		
		titleAddButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {	
				doBack();
			}

		});	*/
	}
	
	private void sendMessage(String str) {
		try {			
			InetAddress serverAddress = InetAddress.getByName(Settings.ipnum);
			byte data[] = str.getBytes();
			DatagramPacket packet = new DatagramPacket(
					data, 
					data.length,
					serverAddress, 
					Settings.socketnum
					);
			socket.send(packet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.moviemenu, menu);
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
		new AlertDialog.Builder(MovieActivity.this)
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
		new AlertDialog.Builder(MovieActivity.this).setTitle("ʹ�ð���")
		.setMessage("��ҳ��ʵ���˵�Ӱң�ع��ܣ��м���ǿ�ʼ����ͣ����ť,����Ǻ��˰�ť���Ҽ���ǰ����ť���ϼ����������߰�ť���¼����������Ͱ�ť��").setIcon(R.drawable.icon)
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
		 Intent intent = new Intent(MovieActivity.this,MainActivity.class);
		 MovieActivity.this.startActivity(intent);
		 this.finish();
	}
	
}
