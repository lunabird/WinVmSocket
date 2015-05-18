package com.huayun.winvm.socket;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static final int PORT = 12345;// �����Ķ˿ں�

	public static void main(String[] args) {
		
		Server server = new Server();
		server.init();
	}

	public void init() {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			System.out.println("���������������˿ڣ�"+PORT+"...\n");
			while (true) {
				// һ���ж���, ���ʾ��������ͻ��˻��������
				Socket client = serverSocket.accept();
				// �����������
				new HandlerThread(client);
			}
		} catch (Exception e) {
			System.out.println("�������쳣: " + e.getMessage());
		}
	}

	private class HandlerThread implements Runnable {
		private Socket socket;

		public HandlerThread(Socket client) {
			socket = client;
			new Thread(this).start();
		}

		public void run() {
			try {
				// ��ȡ�ͻ�������
				DataInputStream input = new DataInputStream(
						socket.getInputStream());
				String clientInputStr = input.readUTF();// ����Ҫע��Ϳͻ����������д������Ӧ,�������
														// EOFException
				// ����ͻ�������
				System.out.println("���յ��ͻ���"+socket.getInetAddress()+"������������:" + clientInputStr);
				excuteCommand(clientInputStr);
				
				
				// ��ͻ��˻ظ���Ϣ
				DataOutputStream out = new DataOutputStream(
						socket.getOutputStream());
				// System.out.print("������:\t");
				// ���ͼ��������һ��
				// String s = new BufferedReader(new
				// InputStreamReader(System.in)).readLine();
				out.writeUTF("OK");

				out.close();
				input.close();
			} catch (Exception e) {
				System.out.println("������ run �쳣: " + e.getMessage());
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (Exception e) {
						socket = null;
						System.out.println("����� finally �쳣:" + e.getMessage());
					}
				}
			}
		}
	}
	public static void  excuteCommand(String command)
	{
	
	    Runtime r = Runtime.getRuntime();
	    Process p = null;
	    
        try {

            p = r.exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String inline;
            while ((inline = br.readLine()) != null) {
                System.out.println(inline);
                
            }
            br.close();
            p.destroy();
            p=null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	
	}
	
	
	
	
	
}
