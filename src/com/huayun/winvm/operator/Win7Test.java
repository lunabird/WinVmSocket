package com.huayun.winvm.operator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Win7Test {
	
	public static void main (String[] args) {
		//1)修改密码.首先使用管理员身份运行cmd，输入指令：“net user <用户名> <新密码>”
//		excuteCommand("cmd /c net user Administrator 5413");
		/*2)修改安全规则
		关闭端口：首先打开“控制面板”，双击“管理工具”，再双击“服务”。接着在打开的服务窗口中找到并双击“Simple Mail Transfer Protocol （SMTP）”服务，
			单击“停止”按钮来停止该服务，然后在“启动类型”中选择“已禁用”，最后单击“确定”按钮即可。这样，关闭了SMTP服务就相当于 关闭了对应的端口。
			 指令： sc stop servicename ，sc config servicename start= DISABLED
		开启端口：如果要开启该端口只要先在“启动类型”选择“自动”，单击“确定”按钮，再打开该服务，在“服务状态”中单击“启动”按钮即可启用该端口，最后，单击“确定”按钮即可。   
			指令：sc config servicename start= AUTO，sc start servicename
		*/
//		excuteCommand("cmd /c sc config WcsPlugInService start= AUTO ");
//		excuteCommand("cmd /c sc start WcsPlugInService ");
		/*3)查看操作系统错误日志
	            运行execCommmad，将操作系统错误日志输出。？？？？？？？？？？？
	            eventvwr.msc是打开windows事件查看器，里面有Windows日志的内容。
	    */
//		excuteCommand("cmd /c eventvwr.msc ");
		/*4)对操作系统中正在运行的服务进行启动、关闭等操作
		需要用管理员身份来操作，关闭服务：“sc stop <服务名>”，启动服务：“sc start<服务名>”。
		*/
//		excuteCommand("cmd /c sc start WcsPlugInService ");
		/*5)磁盘格式化
	            首先“开始——>cmd”，输入“diskpart”，进入Diskpart Shell界面；输入“list disk”显示所有物理磁盘；“select disk 0”选中磁盘；
	            “convert Dynamic”使得磁盘变为动态磁盘；
	            “Create Volume RAID Disk 1, 2, 3”创建一个RAID 5卷；
	            “Format FS=NTFS Label=Volume 1”将MyNewVolume这个卷初始化。
		*/
//		excuteCommand("cmd /c diskpart");
		
		/*6)远程传输文件
	           运行execCommmad，远程连接到目的主机，然后将需要的文件传输。
	    */
//		excuteCommand("cmd /c ");
		/*7)修改静态IP和附属IP
		修改静态IP：“netsh interface ip set address name=”本地连接” 
		static addr=192.168.1.2 mask=255.255.255.0 gateway=192.168.1.1 gwmetric=auto”
		配置DNS：“netsh interface ip set dns name=”本地连接” addr=192.168.1.2 index=2”
		*/
		excuteCommand("cmd /c netsh ");
		excuteCommand("interface  ");
		excuteCommand("ip");
		excuteCommand("set address name=”本地连接” static 192.168.0.201 255.255.255.0");
		
		/*8)定时启动、关闭虚拟机并根据用户要求在虚拟机上部署应用
		定时关闭虚拟机：vmrun stop D:\虚拟机\App1\app1.vmx (虚拟文件目录)
		定时开启虚拟机：vmrun start D:\虚拟机\App1\app1.vmx
		注意：C:\Program Files\VMware\VMware Workstation(vmrun安装目录)在环境变量path中加入此目录。  (start/stop/suspend/reset/upgradevm)
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
