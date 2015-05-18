package com.huayun.winvm.socket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Administrator
 *
 */
public class SystemConfigurations {
	
	public static void main(String[] args){
		SystemConfigurations sc = new SystemConfigurations();
//		System.out.println(sc.getSystemServiceState());
//		System.out.println(sc.getSystemSecRule());
		System.out.println(sc.getSystemIP());
	}
	
	/**
	 * ��ȡϵͳ���з���,�ļ������sysServiceState.txt
	 */
	public String getSystemServiceState(){
		excuteCommand("cmd /c sc query state= all>C:\\sysServiceState.txt");
		String s = readFileByLines("C:\\sysServiceState.txt");
		return s.substring(4);
	}
	
	/**
	 * ��ȡϵͳ��ȫ����,�ļ������sysSecurity.txt
	 */
	public String getSystemSecRule(){
		excuteCommand("cmd /c netsh ipsec static show all>C:\\sysSecurity.txt");
		String s = readFileByLines("C:\\sysSecurity.txt");
		return s.substring(4);
	}
	/**
	 * ��ȡϵͳIP,�ļ������sysIP.txt
	 */
	public String getSystemIP(){
		excuteCommand("cmd /c ipconfig /all>C:\\sysIP.txt");
		String s = readFileByLines("C:\\sysIP.txt");
		return s.substring(4);
	}
	
	/**
	 * ִ��windows����
	 * @param command
	 */
	public static boolean  excuteCommand(String command)
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
            int res = p.waitFor();
            if(res==0){
            	return true;
            }
            p.destroy();
            p=null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return false;
	}
	
	 /**
     * ����Ϊ��λ��ȡ�ļ��������ڶ������еĸ�ʽ���ļ�
     */
    public static String readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        String returnStr = null;
        try {
//            System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
            while ((tempString = reader.readLine()) != null) {
                // ��ʾ�к�
//                System.out.println("line " + line + ": " + tempString);
                line++;
                returnStr += tempString+"\n"; 
            }
            reader.close();
            return returnStr;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return returnStr;
    }	
}
