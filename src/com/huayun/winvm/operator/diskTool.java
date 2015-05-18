package com.huayun.winvm.operator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class diskTool {
	/*
	select disk 1
	create partition primary	
	format fs=ntfs label="Main Volume" quick compress
	active 
	assign letter=s
	create partition extended 
	create partition logical 
	format fs=ntfs label="Logical Volume" quick compress
	assign letter=h
	*/
	
	public static String getDiskName() {
		String diskName = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("D:\\diskbat.bat");
			bufferedReader = new BufferedReader(new InputStreamReader(process
					.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
//				System.out.println(line);
				index = line.toLowerCase().indexOf("����");
				if (index >= 0) {// �ҵ���
//					index = line.indexOf(":");// Ѱ��":"��λ��
					if (index>=0) {
						diskName = line.substring(0,6).trim();//  ȡ��mac��ַ��ȥ��2�߿ո�
						System.out.println(diskName);
					}
//					break;
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

		return null;
	}
	
	public static void main(String[] args){
		getDiskName();
	}

}
