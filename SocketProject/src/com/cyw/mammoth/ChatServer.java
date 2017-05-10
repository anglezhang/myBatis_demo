package com.cyw.mammoth;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.json.JSONObject;

import com.cyw.common.Coder;
import com.cyw.common.Constant;

public class ChatServer {
	//服务器端口是否开启
	boolean started = false;
	//套接字服务
	ServerSocket ss = null;
	//客户端连接列表
//	List<Client> clients = new ArrayList<Client>();
	//客户端连接
	Map<String, Client> clientMap = new HashMap<String, ChatServer.Client>();
	

	//线程池
	ExecutorService service = Executors.newCachedThreadPool();
	
	//配置对象管理
	private Properties prop = null;
	//监听端口
	private Integer port = null;

	public static void main(String[] args) {
		new ChatServer().start();
	}
	
	private String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALG+sZ2Hr+Qo3kRxnGV5B3jjAYjUbved/s51FQ/aHD+zXsC7O10UCjB+GjnLgonqgeTDJpIDKbmycknDmVOILZbuvqN7n2jjZIgpoj9IEEBOU0x8FbrEPGvkBClHo2tfkH7MP1NqIifWpEFeIFvcVv8m43sPCvBKWr6RaYW3k/txAgMBAAECgYB/NmU2wtsvZ9SUld+CfJnNemMpxCjbjjfFGeUGIGuhZ8ZEGH0HRAV66XXpsW7vOGjZCpb7X8ooPsAvbQ+6yjcdoFT6iVu54LuSL68rDD0NJXSvCOwHbjvwNf6vcpVJwsFEM1aQKI9ssbF0YGv7iC4irWMttJJsIBt2afuV0wGshQJBAPJK/A0tvBm99ghgOK8vTh+9aJdcFagu6s215RyvrEZYdWnuuvLFsQWRB093gXoizWqrgKLUpmjLQan68Oj1VqcCQQC7zOPzml4ItUZRsb5nc7MliCk7ZTRs8mSJm88Rf4quYatXl2numdSofWMJEDbBtP06WwiN7g5Ehjkdf1Dp7vgnAkEAh2pJPMRW4Lw+iafdhmuV6j9d+VpJ8FloedgzWLkGO7qsxKvcaFr8+PN5dnyALNbOn9OCbX8UEnWnGW1av5h++QJBAJvekIvNYaKkofqIJdpDQzsxaddsRjjubddwxEpnqzjUw5tthnSMpN47Q4lwNaJMj6hxigYSwTDKnnkHBKdEuh0CQQCvajP0flFiUpg77EokK/SR5lVK7qPL0ozAkfTdGLz2wUEhgXENdAr9+9yPR34X9AwFU1xRyNM9+10PTpK2ahW9";
	
	/**
	 * 加载properties文件
	 */
	private void loadProperites(){
		try {
			prop = new Properties();
			prop.load(ChatServer.class.getClassLoader().getResourceAsStream("configure.properties"));
		} catch (IOException e1) {
			prop = null;
			e1.printStackTrace();
		}
		if(prop != null){
			port = Integer.parseInt(prop.getProperty("socket.port"));
		}else{
			port = 8888;
		}
	}

	/**
	 * 启动Socket服务
	 */
	public void start() {
		
		try {
			loadProperites();
			ss = new ServerSocket(port);
			started = true;
			service.execute(new TimmerTask());
			System.out.println("端口已开启,占用"+port+"端口号....");
		} catch (BindException e) {
			System.out.println("端口使用中....");
			System.out.println("请关掉相关程序并重新运行服务器！");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			while (started) {
				Socket s = ss.accept();
//				Client c = new Client(s);
//				System.out.println("a client connected!");
//				new Thread(c).start();
				service.execute(new Client(s));
//				clients.add(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 启动线程定时发送心跳
	 * @author Administrator
	 *
	 */
	class TimmerTask implements Runnable{

		@Override
		public void run() {
			while(true){
				Set<String> set = clientMap.keySet();
				Iterator<String> ite = set.iterator();
				while(ite.hasNext()){
					clientMap.get(ite.next()).sendUrgentData();
				}
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	/**
	 * 客户端连接处理对象（每个客户端连接新启动一个线程）
	 * @author Administrator
	 *
	 */
	class Client implements Runnable {
		//Socket连接对象
		private Socket s;
		private DataInputStream dis = null;
		private DataOutputStream dos = null;
		private boolean bConnected = false;
		private String token = "";
		

		public Client(Socket s) {
			this.s = s;
			try {
				this.s.setKeepAlive(true);
				this.s.setSoTimeout(0);
			} catch (SocketException e1) {
				e1.printStackTrace();
			}
			try {
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
				bConnected = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * 发送心跳包
		 */
		public void sendUrgentData(){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				s.sendUrgentData(0xFF);
				System.out.println(sdf.format(new Date())+"-检查结果：连接没问题~_~");
			} catch (IOException e) {
				e.printStackTrace();
				clientMap.remove(token);
				bConnected = false;
				System.out.println(sdf.format(new Date())+"-检查结果：连接挂了~^~");
			}
		}

		/**
		 * 发送数据
		 */
		public void send(String str) {
			try {
				dos.writeUTF(str);
			} catch (IOException e) {
				clientMap.remove(token);
				bConnected = false;
				System.out.println("对方退出了！我从List里面去掉了！");
			}
		}

		//JSONObject:{request_type:"",token:"",target:"",source:"",data:{}}
		//request_type:请求类型（Constant.RequestType）
		//token:令牌（客户端固定，服务端随机产生）
		//target:目标（数据发送目标的token）
		//source:源（数据发送源）
		//data:数据内容（可以是对象或列表）
		public void run() {
			try {
				while (bConnected) {
					String str = dis.readUTF();
					byte[] data = Coder.decryptBASE64(str);
					byte[] bitData = Coder.decryptByPrivateKey(data, privateKey);
					JSONObject json = JSONObject.fromObject(new String(bitData));
					System.out.println("data in:"+json.toString());
					String request_type = json.getString("request_type");
					if(request_type.equals(Constant.RequestType.REQUEST_TYPE_CONN)){
						token = json.getString("token");
						clientMap.put(token, this);
						System.out.println(clientMap.toString());
					}else if(request_type.equals(Constant.RequestType.REQUEST_TYPE_GET_ROOMSTAT) || request_type.equals(Constant.RequestType.REQUEST_TYPE_SEND_ORDER) || request_type.equals(Constant.RequestType.REQUEST_TYPE_CLIENT_RETURN)){
						String target = json.get("target").toString();
						Client c = clientMap.get(target);
						if(c == null){
							JSONObject obj = new JSONObject();
							obj.put("result", Constant.ResultCode.REQUEST_TARGET_ERR);
							send(obj.toString());
						}else{
							c.send(json.toString());
						}
					}
				}
			} catch (EOFException e) {
//				bConnected = false;
//				System.out.println("Client closed!");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (dis != null)
						dis.close();
					if (dos != null)
						dos.close();
					if (s != null) {
						s.close();
					}

				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		}
	}
}