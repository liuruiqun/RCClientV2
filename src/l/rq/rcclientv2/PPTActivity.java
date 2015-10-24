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
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class PPTActivity extends Activity {	
	Handler handler = new Handler();
	ActionBar actionBar;
	Runnable leftButtonDown;
	Runnable leftButtonRealease;
	Runnable rightButtonDown;
	Runnable rightButtonRealease;

	private FrameLayout leftButton;
	private FrameLayout rightButton;
		
	//private Button titleBackButton;
	//private boolean flag= true;//屏蔽home键
	private DatagramSocket socket;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ppt);
		actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.PPT);
		//titleBackButton = (Button) findViewById(R.id.head_TitleBackBtn);
		
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initTouch();		
		
		/*titleBackButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {	
				doBack();
			}

		});	*/
	}
	
	/*@Override  
	public void onAttachedToWindow() {  
	    if(flag) {  
	        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);  
	    }  
	    super.onAttachedToWindow();  
	} */

	private void initTouch() {
		leftButton = (FrameLayout) this.findViewById(R.id.leftButton);
		leftButton.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent ev) {
				if (ev.getAction() == MotionEvent.ACTION_DOWN) {
					try {
						Runnable runnable = new Runnable(){
			        		  @Override
			        		  public void run() {
			        		    //
			        		    // TODO: http request.
			        		    //
			        			  onLeftButton("down");	
			        		  }
			        	};
	        			new Thread(runnable).start();
	        			
	        		} catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
					//onLeftButton("down");					
					handler.post(leftButtonDown);
				}
				if (ev.getAction() == MotionEvent.ACTION_UP) {
					try {
						Runnable runnable = new Runnable(){
			        		  @Override
			        		  public void run() {
			        		    //
			        		    // TODO: http request.
			        		    //
			        			  onLeftButton("release");
			        		  }
			        	};
	        			new Thread(runnable).start();
	        			
	        		} catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
					//onLeftButton("release");
					handler.post(leftButtonRealease);
				}
				return true;
			}
		});

		rightButton = (FrameLayout) this.findViewById(R.id.rightButton);
		rightButton.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent ev) {
				if (ev.getAction() == MotionEvent.ACTION_DOWN) {
					try {
						Runnable runnable = new Runnable(){
			        		  @Override
			        		  public void run() {
			        		    //
			        		    // TODO: http request.
			        		    //
			        			  onRightButton("down");
			        		  }
			        	};
	        			new Thread(runnable).start();
	        			
	        		} catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
					//onRightButton("down");
					handler.post(rightButtonDown);
				}
				if (ev.getAction() == MotionEvent.ACTION_UP) {
					try {
						Runnable runnable = new Runnable(){
			        		  @Override
			        		  public void run() {
			        		    //
			        		    // TODO: http request.
			        		    //
			        			  onRightButton("release");
			        		  }
			        	};
	        			new Thread(runnable).start();
	        			
	        		} catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
					//onRightButton("release");
					handler.post(rightButtonRealease);
				}
				return true;
			}
		});


		this.leftButtonDown = new Runnable() {
			@Override
			public void run() {
				drawLeftButtonDown(leftButton);
			}

			private void drawLeftButtonDown(FrameLayout fl) {
				fl.setBackgroundResource(R.drawable.zuoc);
			}
		};

		this.rightButtonDown = new Runnable() {
			@Override
			public void run() {
				drawButtonDown(rightButton);
			}

			private void drawButtonDown(FrameLayout fl) {
				fl.setBackgroundResource(R.drawable.youc);
			}
		};

		this.leftButtonRealease = new Runnable() {
			@Override
			public void run() {
				drawLeftButtonRealease(leftButton);
			}

			private void drawLeftButtonRealease(FrameLayout fl) {
				fl.setBackgroundResource(R.drawable.zuo);

			}
		};

		this.rightButtonRealease = new Runnable() {
			@Override
			public void run() {
				drawButtonRealease(rightButton);
			}

			private void drawButtonRealease(FrameLayout fl) {
				fl.setBackgroundResource(R.drawable.you);

			}
		};

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.pptmenu, menu);
		return true;
	}

	/**
	 * 捕捉菜单事件
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

	
	private void onLeftButton(String type) {
		String str = "lastPage" + ":" + type;
		sendMessage(str);

	}

	private void onRightButton(String type) {
		String str = "nextPage" + ":" + type;
		sendMessage(str);
	}

	private void sendMessage(String str) {
		try {
			// 首先创建一个DatagramSocket对象
			
			// 创建一个InetAddree
			InetAddress serverAddress = InetAddress.getByName(Settings.ipnum);
			byte data[] = str.getBytes();
			// 创建一个DatagramPacket对象，并指定对方地址，以及端口号
			DatagramPacket packet = new DatagramPacket(
					data, 
					data.length,
					serverAddress, 
					Settings.socketnum);
			// 调用socket对象的send方法，发送数据
			socket.send(packet);
			//System.out.println("send");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 显示关于我们
	 */
	public void about() {
		new AlertDialog.Builder(PPTActivity.this)
				.setTitle("关于我们")
				.setMessage("欢迎使用 遥控小精灵  \n" + 
						"开发者：刘锐群 \n" +
						"交流邮箱：445647390@qq.com \n" +
						"感谢您的支持")
				.setIcon(R.drawable.icon)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						// finish();
					}
				})
				.setNegativeButton("返回", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}

				}).show();
	}

	private void help(){
		new AlertDialog.Builder(PPTActivity.this).setTitle("使用帮助")
		.setMessage("本页面实现了PPT的翻页控制，左键代表向后翻页，右键代表向前翻页。").setIcon(R.drawable.icon)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// finish();
			}
		}).setNegativeButton("返回",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						// TODO Auto-generated method stub

					}

				}).show();
	}
	
	
	private void doBack(){
		 Intent intent = new Intent(PPTActivity.this,MainActivity.class);
		 PPTActivity.this.startActivity(intent);
		 this.finish();
	}
	
	private long exitTime = 0;
    public boolean onKeyDown(int keyCode, KeyEvent event) {

		if( keyCode== KeyEvent.KEYCODE_HOME){
			return true;
		} else if( keyCode== KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			if((System.currentTimeMillis()- exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	            System.exit(0);
	        }
			return true;
		} 	
		return super.onKeyDown(keyCode, event);
	}

}
