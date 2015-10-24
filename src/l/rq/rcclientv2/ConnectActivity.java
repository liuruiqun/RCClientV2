package l.rq.rcclientv2;

import android.os.Bundle;
import android.os.Looper;
import android.app.Activity;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConnectActivity extends Activity {
    EditText ipET;
    EditText socketET;
    Button button;
    DatagramSocket socket;
    String ipnum;
    int socketnum;
    //DatagramSocket socket = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect);
        ipET = (EditText)findViewById(R.id.IpEditText);
        socketET = (EditText)findViewById(R.id.SocketEditText);
        button = (Button)findViewById(R.id.ConnectButton);
                
        button.setOnClickListener(new OnClickListener() { 
        	@Override 
        	public void onClick(View v) { 
        		
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
        			  sendMessage("connect:hello" );
        		  }
        	};
        	
        	private void sendMessage(String str) {
        		try {   
        			ipnum = ipET.getText().toString(); 
        			socketnum = Integer.parseInt(socketET.getText().toString());
        			Settings.ipnum =ipnum;
        			Settings.socketnum = socketnum;
        			
        			socket =new DatagramSocket();          			
        			InetAddress serverAddress = InetAddress.getByName(Settings.ipnum);
        			byte data[] = str.getBytes();
        		
        			DatagramPacket outPacket = new DatagramPacket(data, data.length,
        					serverAddress, Settings.socketnum);
        			socket.send(outPacket);	
        			System.out.println(new String (outPacket.getData()));
        			byte[] buffer =  "ACK".getBytes();
        			DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);  
        		    try {  
        		        //设置超时时间,3秒  
        		    	socket.setSoTimeout(3000);  
        		    	socket.receive(inPacket);
        		    	System.out.println(new String (inPacket.getData()));
        		    } catch (Exception e) {  
        		    	Looper.prepare();
        		    	Toast.makeText(ConnectActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
        		    	Looper.loop();
        		    	throw e;  
        		    } finally{
        		    	socket.close();
        		    }
        		    Intent intent = new Intent(ConnectActivity.this,MainActivity.class);
        			ConnectActivity.this.startActivity(intent);
        			ConnectActivity.this.finish();
        			Looper.prepare();
        			Toast.makeText(ConnectActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
        			Looper.loop();
        		    
        		} catch (Exception e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        	}
        	
        	
        }); 
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
