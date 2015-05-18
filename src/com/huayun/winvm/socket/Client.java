package com.huayun.winvm.socket;
import java.io.BufferedReader;  
import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.net.Socket;  
  
public class Client {  
    public static final String IP_ADDR = "219.245.68.232";//��������ַ   
    public static final int PORT = 12345;//�������˿ں�    
      
    public static void main(String[] args) {    
        System.out.println("�ͻ�������...");    
        System.out.println("�����յ����������ַ�Ϊ \"OK\" ��ʱ��, �ͻ��˽���ֹ\n");   
        while (true) {    
            Socket socket = null;  
            try {  
                //����һ�����׽��ֲ��������ӵ�ָ�������ϵ�ָ���˿ں�  
                socket = new Socket(IP_ADDR, PORT);    
                    
                //��ȡ������������    
                DataInputStream input = new DataInputStream(socket.getInputStream());    
                //��������˷�������    
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());    
//                System.out.print("������: \t");    
//                String str = new BufferedReader(new InputStreamReader(System.in)).readLine();  
                /*1)�޸�����.����ʹ�ù���Ա�������cmd������ָ���net user <�û���> <������>��*/
//                String str = "cmd /c net user Administrator 5413";
                /*2)�޸İ�ȫ����
        		�رն˿ڣ�
        			 ָ� sc stop servicename ��sc config servicename start= DISABLED
        		�����˿ڣ� 
        			ָ�sc config servicename start= AUTO��sc start servicename
        		*/
//        		String str = "cmd /c sc stop MySQL56";
//        		String str1 = "cmd /c sc config MySQL56 start= DISABLED";
                /*3)�鿴����ϵͳ������־,������־�ļ�Ŀ¼*/
//                
                /*4)�Բ���ϵͳ���������еķ�������������رյȲ�����ͬ2��*/
                
                /*5)���̸�ʽ�������нű�disk.bat,disk.txt*/
                
                
                /*6)Զ�̴����ļ�
 	           	ftp
                */
                
                /*7)�޸ľ�̬IP�͸���IP ---ִ�нű��ļ�modip.txt
        		�޸ľ�̬IP����netsh interface ip set address name=���������ӡ� 
        		source=static addr=192.168.1.2 mask=255.255.255.0 gateway=192.168.1.1 gwmetric=auto��
        		����DNS����netsh interface ip set dns name=���������ӡ� addr=192.168.1.2 index=2��
        		*/
//                String str = "ִ�нű��ļ�modip.txt";
                  
                
                /*8)��ʱ�������ر�������������û�Ҫ����������ϲ���Ӧ��
        		��ʱ�ر��������at 10:11 Shutdown -s 
        		*/
                String str = "";
                
                out.writeUTF(str); 
                    
                String ret = input.readUTF();     
                System.out.println("�������˷��ع�������: " + ret);    
                // ����յ� "OK" ��Ͽ�����    
                if ("OK".equals(ret)) {    
                    System.out.println("�ͻ��˽��ر�����");    
                    Thread.sleep(500);    
                    break;    
                }    
                  
                out.close();  
                input.close();  
            } catch (Exception e) {  
                System.out.println("�ͻ����쳣:" + e.getMessage());   
            } finally {  
                if (socket != null) {  
                    try {  
                        socket.close();  
                    } catch (IOException e) {  
                        socket = null;   
                        System.out.println("�ͻ��� finally �쳣:" + e.getMessage());   
                    }  
                }  
            }  
        }    
    }    
}    
