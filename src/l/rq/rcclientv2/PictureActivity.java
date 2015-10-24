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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class PictureActivity extends Activity{	
	final String FILE_NAME = "picture.png";
	private Handler messageHandler;
	private Button captureButton;
	ActionBar actionBar;
	private DatagramSocket socket; 
	byte[] sendData =  "ACK".getBytes(); 
    byte[] buff = new byte[8192];
	DatagramPacket inPacket = new DatagramPacket(buff , buff.length);
	DatagramPacket outPacket = null; 
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picture);
		actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(R.string.picture);
		
		//�õ���ǰ�̵߳�Looperʵ�������ڵ�ǰ�߳���UI�߳�Ҳ����ͨ��Looper.getMainLooper()�õ�
        Looper looper = Looper.myLooper();
        //�˴��������Բ���Ҫ����Looper����Ϊ HandlerĬ�Ͼ�ʹ�õ�ǰ�̵߳�Looper
        messageHandler = new MessageHandler(looper);        
		captureButton = (Button) findViewById(R.id.captureButton);
		
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		captureButton.setOnClickListener(new OnClickListener() {
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
      			sendMessage("picture:message," + s);
      		  }
			};     	     	

		});							
		
	}
	
	
	//���໯һ��Handler
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
		
		//����ж��߼�
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
	 * ��׽�˵��¼�
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
		new AlertDialog.Builder(PictureActivity.this).setTitle("ʹ�ð���")
		.setMessage("��ҳ��ʵʱ��ʵʱ���Ի����⹦�ܣ����һ�ν�����ť���᷵��һ�ε��Ի����ʵʱͼƬ��").setIcon(R.drawable.icon)
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
		 Intent intent = new Intent(PictureActivity.this,MainActivity.class);
		 PictureActivity.this.startActivity(intent);
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
		new AlertDialog.Builder(PictureActivity.this)
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
