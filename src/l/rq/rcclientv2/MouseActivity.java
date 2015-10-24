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

public class MouseActivity extends Activity {
	private static float mx = 0; // ���͵�����ƶ��Ĳ�ֵ
	private static float my = 0;
	private static float lx; // ��¼�ϴ�����λ��
	private static float ly;
	private static float fx; // ��ָ��һ�νӴ���Ļʱ������
	private static float fy;
	private static float lbx = 0; // �������ƶ���ʼ������
	private static float lby = 0;
	ActionBar actionBar;
	Handler handler = new Handler();
	Runnable leftButtonDown;
	Runnable leftButtonRealease;
	Runnable rightButtonDown;
	Runnable rightButtonRealease;
	//private MotionEvent event ;

	private FrameLayout leftButton;
	private FrameLayout rightButton;
		
	//private Button titleBackButton;
	//private Button titleAddButton;

	//private boolean flag= true;//����home��
	private DatagramSocket socket;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mouse);
		actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.mouse);
		//titleBackButton = (Button) findViewById(R.id.head_TitleBackBtn);
		//titleAddButton = (Button) findViewById(R.id.head_TitleAddBtn);
		
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

		});	
		
		titleAddButton.setOnClickListener(new OnClickListener() {
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
		FrameLayout touch = (FrameLayout) this.findViewById(R.id.touch);
		// let's set up a touch listener
		touch.setOnTouchListener(new View.OnTouchListener() {
			@Override		
			public boolean onTouch(View v, final MotionEvent ev) {
				Runnable runnable = new Runnable(){
		      		  @Override
		      		  public void run() {
		      			if (ev.getAction() == MotionEvent.ACTION_MOVE)
							onMouseMove(ev);
						if (ev.getAction() == MotionEvent.ACTION_DOWN)
							onMouseDown(ev);
						if (ev.getAction() == MotionEvent.ACTION_UP)
							onMouseUp(ev);
		      		  }
				};
				
				try {
						new Thread(runnable).start();    			
				} catch (Exception e) {
      			// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return true;
							
			}
		});

		leftButton = (FrameLayout) this.findViewById(R.id.leftButton);
		leftButton.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent ev) {
				if (ev.getAction() == MotionEvent.ACTION_DOWN) {
					try {
						Runnable runnable = new Runnable(){
			        		  @Override
			        		  public void run() {
			        		    
			        			  onLeftButton("down");
			        		  }
			        	};
	        			new Thread(runnable).start();
	        			
	        		} catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
					
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
					
					lbx = 0;
					lby = 0;
					handler.post(leftButtonRealease);
				}
				if (ev.getAction() == MotionEvent.ACTION_MOVE)
					moveMouseWithSecondFinger(ev);
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
					
					handler.post(rightButtonRealease);
				}
				return true;
			}
		});

		FrameLayout middleButton = (FrameLayout) this.findViewById(R.id.middleButton);
		middleButton.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, final MotionEvent ev) {
				Runnable runnable = new Runnable(){
		      		  @Override
		      		  public void run() {
		      			if (ev.getAction() == MotionEvent.ACTION_DOWN)
							onMiddleButtonDown(ev);
						if (ev.getAction() == MotionEvent.ACTION_MOVE)
							onMiddleButtonMove(ev);
		      		  }
				};
				
				try {
        			new Thread(runnable).start();
        			
        		} catch (Exception e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
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
		getMenuInflater().inflate(R.menu.mousemenu, menu);
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
			
		case R.id.movie:
			toMovie();
			return true;
			
		case R.id.PPT:
			toPPT();
			return true;
		
		case R.id.screen:
			toScreen();
			return true;
			
		case R.id.keyboard:
			toKeyboard();
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
	
	private void toMovie(){
		Intent intent = new Intent(MouseActivity.this,MovieActivity.class);
		 MouseActivity.this.startActivity(intent);
		 this.finish();
	}

	private void toPPT(){
		Intent intent = new Intent(MouseActivity.this,PPTActivity.class);
		 MouseActivity.this.startActivity(intent);
		 this.finish();
	}
	
	private void toScreen(){
		Intent intent = new Intent(MouseActivity.this,ScreenActivity.class);
		 MouseActivity.this.startActivity(intent);
		 this.finish();
	}
	
	private void toKeyboard(){
		Intent intent = new Intent(MouseActivity.this,DOSActivity.class);
		 MouseActivity.this.startActivity(intent);
		 this.finish();
	}

	private void moveMouseWithSecondFinger(MotionEvent event) {
		int count = event.getPointerCount();
		if (count == 2) {
			if (lbx == 0 && lby == 0) {
				lbx = event.getX(1);
				lby = event.getY(1);
				return;
			}
			float x = event.getX(1);
			float y = event.getY(1);
			sendMouseEvent("mouse", x - lbx, y - lby);
			lbx = x;
			lby = y;
		}
		if (count == 1) {
			lbx = 0;
			lby = 0;
		}

	}

	private void onMouseDown(MotionEvent ev) {
		lx = ev.getX(); // ���ֻ���һ����ʱ �ѵ�ǰ���긶��lx
		ly = ev.getY();
		fx = ev.getX();
		fy = ev.getY();
	}

	private void onMouseMove(MotionEvent ev) {
		float x = ev.getX();
		mx = x - lx; // ��ǰ���λ�� - �ϴ�����λ��
		lx = x; // �ѵ�ǰ����λ�ø���lx �Ա��´�ʹ��
		float y = ev.getY();
		my = y - ly;
		ly = y;
		if (mx != 0 && my != 0)
			this.sendMouseEvent("mouse", mx, my);

	}

	private void onMouseUp(MotionEvent ev) {
		if (fx == ev.getX() && fy == ev.getY()) {
			sendMessage("leftButton:down");
			sendMessage("leftButton:release");
		}

	}

	private void sendMouseEvent(String type, float x, float y) {
		String str = type + ":" + x + "," + y;
		sendMessage(str);
	}

	private void onLeftButton(String type) {
		String str = "leftButton" + ":" + type;
		sendMessage(str);

	}

	private void onRightButton(String type) {
		String str = "rightButton" + ":" + type;
		sendMessage(str);
	}

	private void sendMessage(String str) {
		try {
			// ���ȴ���һ��DatagramSocket����
			
			// ����һ��InetAddree
			InetAddress serverAddress = InetAddress.getByName(Settings.ipnum);
			byte data[] = str.getBytes();
			// ����һ��DatagramPacket���󣬲�ָ���Է���ַ���Լ��˿ں�
			DatagramPacket packet = new DatagramPacket(
					data, 
					data.length,
					serverAddress, 
					Settings.socketnum);
			// ����socket�����send��������������
			socket.send(packet);
			//System.out.println("send");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void onMiddleButtonDown(MotionEvent ev) {
		ly = ev.getY();

	}

	private void onMiddleButtonMove(MotionEvent ev) {
		// count++;

		float y = ev.getY();
		my = y - ly;
		ly = y;
		if (my > 3 || my < -3) { // ���ٷ��ʹ��� �����ƶ�����
			String str = "mousewheel" + ":" + my;
			sendMessage(str);
		}

	}

	
	/**
	 * ��ʾ��������
	 */
	public void about() {
		new AlertDialog.Builder(MouseActivity.this)
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
		new AlertDialog.Builder(MouseActivity.this).setTitle("ʹ�ð���")
		.setMessage("��ҳ��ʵ�������ʹ�������ƣ��·��ֱ�������������Ҽ������֣��м䲿���Ǵ����� ��").setIcon(R.drawable.icon)
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
		 Intent intent = new Intent(MouseActivity.this,MainActivity.class);
		 MouseActivity.this.startActivity(intent);
		 this.finish();
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

}

