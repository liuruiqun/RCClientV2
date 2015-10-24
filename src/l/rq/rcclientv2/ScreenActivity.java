package l.rq.rcclientv2;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
//import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;


public class ScreenActivity extends Activity{	
	final String FILE_NAME = "screen1.png";
	private Handler messageHandler;
	private Button startButton;
	ActionBar actionBar;
	private DatagramSocket socket; 
	byte[] sendData =  "ACK".getBytes(); 
    byte[] buff = new byte[8192];
	DatagramPacket inPacket = new DatagramPacket(buff , buff.length);
	DatagramPacket outPacket = null; 
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen);
		actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.screen);
		
		//得到当前线程的Looper实例，由于当前线程是UI线程也可以通过Looper.getMainLooper()得到
        Looper looper = Looper.myLooper();
        //此处甚至可以不需要设置Looper，因为 Handler默认就使用当前线程的Looper
        messageHandler = new MessageHandler(looper);        
		startButton = (Button) findViewById(R.id.startButton);
		
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		startButton.setOnClickListener(new OnClickListener() {
			String s = "Start";
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
      			sendMessage("screen:message," + s);
      		  }
			};     	     	

		});							
		
	}
	
	
	//子类化一个Handler
    class MessageHandler  extends Handler {
        public MessageHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
        	showView();
        }        

    }
	
	private void sendMessage(String str) {
		try {
			InetAddress serverAddress = InetAddress.getByName(Settings.ipnum);
			byte data[] = str.getBytes();
			outPacket = new DatagramPacket(data, data.length,
					serverAddress, Settings.socketnum);
			socket.send(outPacket);
			receivePic();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void receivePic() throws IOException {
		InetAddress serverAddress = InetAddress.getByName(Settings.ipnum);
		int getLength = 8192;
		byte data[] = "ACK".getBytes();
		DatagramPacket ackPacket = new DatagramPacket(data, data.length,
				serverAddress, Settings.socketnum - 1);
		
		//添加判断逻辑
                deleteFile(FILE_NAME); 
                //System.out.println("delete old file !\n"); 
         
		
		FileOutputStream fos = openFileOutput(FILE_NAME, MODE_APPEND);
		//System.out.println(getFilesDir());
        BufferedOutputStream bos = new BufferedOutputStream(fos);        
		while(getLength == 8192){
        	//System.out.println("recv ...\n");           
            socket.receive(inPacket);
            //System.out.println(new String (inPacket.getData()));
            //System.out.println("getLength" + inPacket.getLength());
            getLength = inPacket.getLength();
            bos.write(buff, 0, getLength);
            
            //System.out.println("send ACK\n");
            socket.send(ackPacket);
        }   
		bos.close();
        fos.close();
        //System.out.println("recv done!\n");        
        messageHandler.sendEmptyMessage(0x1233);
        //socket.close();  
	}

    
	
	private void showView() {	
		//RelativeLayout screen = (RelativeLayout) findViewById(R.id.screen);
		//final ImageView image = new ImageView(this);
		final ImageView image = (ImageView) findViewById(R.id.image); 
		//screen.addView(image);
		//image.setImageResource(images[0]);

		String fileName = getFilesDir().getPath() + "/" + FILE_NAME;
		//System.out.println(fileName);
		Bitmap bm = BitmapFactory.decodeFile(fileName); 
		
		image.setImageBitmap(bm); 
		
		//System.out.println("show done!\n");		
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.screenmenu, menu);
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
							
		case R.id.help:
			help();
			return true;
		
		case R.id.reback:
			doBack();
			return true;
			
		case R.id.about:
			about();
			return true;
		}
		return false;
	}

	
	
	
	private void help(){
		new AlertDialog.Builder(ScreenActivity.this).setTitle("使用帮助")
		.setMessage("本页面实时了实时电脑画面检测功能：点击一次截屏按钮将会返回一次电脑画面的实时图片。").setIcon(R.drawable.icon)
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
		 Intent intent = new Intent(ScreenActivity.this,MainActivity.class);
		 ScreenActivity.this.startActivity(intent);
		 this.finish();
	}
	
//	protected void doExit() {
//		new AlertDialog.Builder(this)
//				.setMessage(getString(R.string.exit_message))
//				.setPositiveButton(getString(R.string.confirm),
//						new DialogInterface.OnClickListener() {
//							public void onClick(
//								DialogInterface dialoginterface, int i) {
//								finish();
//							}
//						})
//				.setNeutralButton(getString(R.string.cancel),
//						new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface arg0, int arg1) {
//							}
//
//						}).show();
//
//	}
	
	public void about() {
		new AlertDialog.Builder(ScreenActivity.this)
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
}
