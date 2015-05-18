package com.huayun.winvm.socket;
import java.io.BufferedReader;  
import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.net.Socket;  
  
public class Client {  
    public static final String IP_ADDR = "219.245.68.232";//服务器地址   
    public static final int PORT = 12345;//服务器端口号    
      
    public static void main(String[] args) {    
        System.out.println("客户端启动...");    
        System.out.println("当接收到服务器端字符为 \"OK\" 的时候, 客户端将终止\n");   
        while (true) {    
            Socket socket = null;  
            try {  
                //创建一个流套接字并将其连接到指定主机上的指定端口号  
                socket = new Socket(IP_ADDR, PORT);    
                    
                //读取服务器端数据    
                DataInputStream input = new DataInputStream(socket.getInputStream());    
                //向服务器端发送数据    
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());    
//                System.out.print("请输入: \t");    
//                String str = new BufferedReader(new InputStreamReader(System.in)).readLine();  
                /*1)修改密码.首先使用管理员身份运行cmd，输入指令：“net user <用户名> <新密码>”*/
//                String str = "cmd /c net user Administrator 5413";
                /*2)修改安全规则
        		关闭端口：
        			 指令： sc stop servicename ，sc config servicename start= DISABLED
        		开启端口： 
        			指令：sc config servicename start= AUTO，sc start servicename
        		*/
//        		String str = "cmd /c sc stop MySQL56";
//        		String str1 = "cmd /c sc config MySQL56 start= DISABLED";
                /*3)查看操作系统错误日志,返回日志文件目录*/
//                
                /*4)对操作系统中正在运行的服务进行启动、关闭等操作，同2）*/
                
                /*5)磁盘格式化。运行脚本disk.bat,disk.txt*/
                
                
                /*6)远程传输文件
 	           	ftp
                */
                
                /*7)修改静态IP和附属IP ---执行脚本文件modip.txt
        		修改静态IP：“netsh interface ip set address name=”本地连接” 
        		source=static addr=192.168.1.2 mask=255.255.255.0 gateway=192.168.1.1 gwmetric=auto”
        		配置DNS：“netsh interface ip set dns name=”本地连接” addr=192.168.1.2 index=2”
        		*/
//                String str = "执行脚本文件modip.txt";
                  
                
                /*8)定时启动、关闭虚拟机并根据用户要求在虚拟机上部署应用
        		定时关闭虚拟机：at 10:11 Shutdown -s 
        		*/
                String str = "";
                
                out.writeUTF(str); 
                    
                String ret = input.readUTF();     
                System.out.println("服务器端返回过来的是: " + ret);    
                // 如接收到 "OK" 则断开连接    
                if ("OK".equals(ret)) {    
                    System.out.println("客户端将关闭连接");    
                    Thread.sleep(500);    
                    break;    
                }    
                  
                out.close();  
                input.close();  
            } catch (Exception e) {  
                System.out.println("客户端异常:" + e.getMessage());   
            } finally {  
                if (socket != null) {  
                    try {  
                        socket.close();  
                    } catch (IOException e) {  
                        socket = null;   
                        System.out.println("客户端 finally 异常:" + e.getMessage());   
                    }  
                }  
            }  
        }    
    }    
}    
