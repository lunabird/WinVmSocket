package com.huayun.winvm.operator;
/*
 * Created on 2005-6-5
 * Author stephen
 * Email zhoujianqiang AT gmail DOT com
 * CopyRight(C)2005-2008 , All rights reserved.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * ��ϵͳ��ص�һЩ���ù��߷���.
 * 
 * @author stephen
 * @version 1.0.0
 */
public class SystemTool {

	/**
	 * ��ȡ��ǰ����ϵͳ����.
	 * return ����ϵͳ���� ����:windows xp,linux ��.
	 */
	public static String getOSName() {
		return System.getProperty("os.name").toLowerCase();
	}
	
	/**
	 * ��ȡunix������mac��ַ.
	 * ��windows��ϵͳĬ�ϵ��ñ�������ȡ.���������ϵͳ����������µ�ȡmac��ַ����.
	 * @return mac��ַ
	 */
	public static String getUnixMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("ifconfig eth0");// linux�µ����һ��ȡeth0��Ϊ���������� ��ʾ��Ϣ�а�����mac��ַ��Ϣ
			bufferedReader = new BufferedReader(new InputStreamReader(process
					.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				index = line.toLowerCase().indexOf("hwaddr");// Ѱ�ұ�ʾ�ַ���[hwaddr]
				if (index >= 0) {// �ҵ���
					mac = line.substring(index +"hwaddr".length()+ 1).trim();//  ȡ��mac��ַ��ȥ��2�߿ո�
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

	/**
	 * ��ȡwidnows�������Ƶ�mac��ַ.
	 * @return ��������
	 */
	public static String getWindowsNetCardName() {
		String mac = null;
		String netName = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("ipconfig /all");// windows�µ������ʾ��Ϣ�а�����mac��ַ��Ϣ
			bufferedReader = new BufferedReader(new InputStreamReader(process
					.getInputStream()));
			String line = null;
			int index = -1;
			int index1 = -1;
			while ((line = bufferedReader.readLine()) != null) {
				index1 = line.toLowerCase().indexOf("��̫��������");
				index = line.toLowerCase().indexOf("�����ַ");// Ѱ�ұ�ʾ�ַ���[physical address]
				if (index1 >= 0) {// �ҵ���
					index1 = line.indexOf(" ");// Ѱ��":"��λ��
					if (index1>=0) {
						netName = line.substring(index1+1).trim();//  ȡ���������ֲ�ȥ��2�߿ո�
						netName = netName.substring(0, netName.length()-1);
						System.out.println(netName);
					}
//					break;
				}
				
				if (index >= 0) {// �ҵ���
					index = line.indexOf(":");// Ѱ��":"��λ��
					if (index>=0) {
						mac = line.substring(index + 1).trim();//  ȡ��mac��ַ��ȥ��2�߿ո�
						System.out.println(mac);
					}
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}
	/**
	 * �����õ�main����.
	 * 
	 * @param argc
	 *            ���в���.
	 */
	public static void main(String[] argc) {
		String os = getOSName();
		System.out.println(os);
		if(os.startsWith("windows")){
			//������windows
//			String mac = getWindowsMACAddress();
//			System.out.println(mac);
		}else{
			//�����Ƿ�windowsϵͳ һ�����unix
			String mac = getUnixMACAddress();
			System.out.println(mac);
		}
	}
}