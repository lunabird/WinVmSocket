package com.huayun.winvm.operator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Win7Test {
	
	public static void main (String[] args) {
		//1)�޸�����.����ʹ�ù���Ա�������cmd������ָ���net user <�û���> <������>��
//		excuteCommand("cmd /c net user Administrator 5413");
		/*2)�޸İ�ȫ����
		�رն˿ڣ����ȴ򿪡�������塱��˫���������ߡ�����˫�������񡱡������ڴ򿪵ķ��񴰿����ҵ���˫����Simple Mail Transfer Protocol ��SMTP��������
			������ֹͣ����ť��ֹͣ�÷���Ȼ���ڡ��������͡���ѡ���ѽ��á�����󵥻���ȷ������ť���ɡ��������ر���SMTP������൱�� �ر��˶�Ӧ�Ķ˿ڡ�
			 ָ� sc stop servicename ��sc config servicename start= DISABLED
		�����˿ڣ����Ҫ�����ö˿�ֻҪ���ڡ��������͡�ѡ���Զ�����������ȷ������ť���ٴ򿪸÷����ڡ�����״̬���е�������������ť�������øö˿ڣ���󣬵�����ȷ������ť���ɡ�   
			ָ�sc config servicename start= AUTO��sc start servicename
		*/
//		excuteCommand("cmd /c sc config WcsPlugInService start= AUTO ");
//		excuteCommand("cmd /c sc start WcsPlugInService ");
		/*3)�鿴����ϵͳ������־
	            ����execCommmad��������ϵͳ������־���������������������������
	            eventvwr.msc�Ǵ�windows�¼��鿴����������Windows��־�����ݡ�
	    */
//		excuteCommand("cmd /c eventvwr.msc ");
		/*4)�Բ���ϵͳ���������еķ�������������رյȲ���
		��Ҫ�ù���Ա������������رշ��񣺡�sc stop <������>�����������񣺡�sc start<������>����
		*/
//		excuteCommand("cmd /c sc start WcsPlugInService ");
		/*5)���̸�ʽ��
	            ���ȡ���ʼ����>cmd�������롰diskpart��������Diskpart Shell���棻���롰list disk����ʾ����������̣���select disk 0��ѡ�д��̣�
	            ��convert Dynamic��ʹ�ô��̱�Ϊ��̬���̣�
	            ��Create Volume RAID Disk 1, 2, 3������һ��RAID 5��
	            ��Format FS=NTFS Label=Volume 1����MyNewVolume������ʼ����
		*/
//		excuteCommand("cmd /c diskpart");
		
		/*6)Զ�̴����ļ�
	           ����execCommmad��Զ�����ӵ�Ŀ��������Ȼ����Ҫ���ļ����䡣
	    */
//		excuteCommand("cmd /c ");
		/*7)�޸ľ�̬IP�͸���IP
		�޸ľ�̬IP����netsh interface ip set address name=���������ӡ� 
		static addr=192.168.1.2 mask=255.255.255.0 gateway=192.168.1.1 gwmetric=auto��
		����DNS����netsh interface ip set dns name=���������ӡ� addr=192.168.1.2 index=2��
		*/
		excuteCommand("cmd /c netsh ");
		excuteCommand("interface  ");
		excuteCommand("ip");
		excuteCommand("set address name=���������ӡ� static 192.168.0.201 255.255.255.0");
		
		/*8)��ʱ�������ر�������������û�Ҫ����������ϲ���Ӧ��
		��ʱ�ر��������vmrun stop D:\�����\App1\app1.vmx (�����ļ�Ŀ¼)
		��ʱ�����������vmrun start D:\�����\App1\app1.vmx
		ע�⣺C:\Program Files\VMware\VMware Workstation(vmrun��װĿ¼)�ڻ�������path�м����Ŀ¼��  (start/stop/suspend/reset/upgradevm)
		*/
//		excuteCommand("cmd /c ");

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
