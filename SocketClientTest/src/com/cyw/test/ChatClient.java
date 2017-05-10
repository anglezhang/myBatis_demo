package com.cyw.test;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import com.cyw.common.Coder;
import com.cyw.common.DigestUtil;

import net.sf.json.JSONObject;

/**
 * @author Michael Huang
 * 
 */
public class ChatClient extends Frame {
	Socket s = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	private boolean bConnected = false;

	TextField tfTxt = new TextField();
	TextArea taContent = new TextArea();

	Thread tRecv = new Thread(new RecvThread());

	private String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxvrGdh6/kKN5EcZxleQd44wGI1G73nf7OdRUP2hw/s17AuztdFAowfho5y4KJ6oHkwyaSAym5snJJw5lTiC2W7r6je59o42SIKaI/SBBATlNMfBW6xDxr5AQpR6NrX5B+zD9TaiIn1qRBXiBb3Fb/JuN7DwrwSlq+kWmFt5P7cQIDAQAB";

	public static void main(String[] args) {
		new ChatClient().launchFrame(8888);
	}

	public void launchFrame(int port) {
		setLocation(400, 300);
		this.setSize(300, 300);
		add(tfTxt, BorderLayout.SOUTH);
		add(taContent, BorderLayout.NORTH);
		pack();
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				disconnect();
				System.exit(0);
			}

		});
		tfTxt.addActionListener(new TFListener());
		setVisible(true);
		connect(port);

		tRecv.start();
	}

	public void connect(int port) {
		try {
			s = new Socket("120.26.241.0", port);

			s.setKeepAlive(true);
			s.setOOBInline(true);
			s.setSoTimeout(3000);
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());

			JSONObject json = new JSONObject();
			json.put("request_type", "10");
			json.put("token", "client_ota_1");
			dos.writeUTF(Coder.encryptBASE64(Coder.encryptByPublicKey(json
					.toString().getBytes(), publicKey)));
			dos.flush();
			System.out.println("~~~~~~~~连接成功~~~~~~~~!");
			bConnected = true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void disconnect() {
		try {
			dos.close();
			dis.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private class TFListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String str = tfTxt.getText().trim();
			tfTxt.setText("");

			JSONObject json = new JSONObject();
			json.put("request_type", "20");
			json.put("token", "client_ota_1");
			json.put("target", "client_hotel_1");
			json.put("data", str);

			try {
				dos.writeUTF(Coder.encryptBASE64(Coder.encryptByPublicKey(json
						.toString().getBytes(), publicKey)));
				dos.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (Exception ee) {
				ee.printStackTrace();
			}

		}

	}

	private class RecvThread implements Runnable {

		public void run() {
			while (bConnected) {
				try {
					String str = dis.readUTF();
					JSONObject json = JSONObject.fromObject(str);
					taContent.setText(taContent.getText() + str + '\n');
				} catch (SocketException e) {
					System.out.println("退出了，bye!");
				} catch (EOFException e) {
					System.out.println("退出了，bye!");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}
}