package com.huayun.winvm.socket;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
/**
 * windows ��������������
 * @author huangpeng
 *
 */
public class WOSConfig {
	/**
	 * �޸�����
	 * @param user
	 * @param password
	 */
	public String changePasswd(String user, String password) {
		if(excuteCommand("cmd /c net user "+user+" "+password)){
			return "0x000";
		}
		return "0x001";
	}
	/**
	 * �����˿�
	 * @param serviceName
	 */
	public String startService(String serviceName) {
		if(excuteCommand("cmd /c sc config "+serviceName+" start= AUTO ")&&
		excuteCommand("cmd /c sc start "+serviceName)){
			return "0x010";
		}
		return "0x011";
	}
	/**
	 * �رն˿�
	 * @param serviceName
	 */
	public String stopService(String serviceName) {
		if(excuteCommand("cmd /c sc stop "+serviceName)&&
		excuteCommand("cmd /c sc config "+serviceName+" start= DISABLED")){
			return "0x020";
		}
		return "0x021";
	}
	
	public boolean viewErrLog() {
		return false;
	}
	/**
	 * ���̸�ʽ��
	 * @param diskName
	 * @throws IOException 
	 */
	public String diskFormat() throws IOException {
		String diskName = getDiskName();
		File path = new File("C:/"); // "C:/"Ŀ¼�������
		File dir = new File(path, "diskBat.bat");
		if (!dir.exists())
			dir.createNewFile();
		String filePath = "C:\\diskBat.bat";
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(
				filePath));
		
		out.write("diskpart /s C:\\disktest.txt");
		out.write("\n");
		out.close();
		
		File dir1 = new File(path, "disktxt.txt");
		if (!dir1.exists())
			dir1.createNewFile();
		String filePath1 = "C:\\disktxt.txt";
		OutputStreamWriter out1 = new OutputStreamWriter(new FileOutputStream(
				filePath1));
		
		out.write("select "+diskName+"\n"+
				  "create partition primary	\n"+
				  "format fs=ntfs label=\"Main Volume\" quick compress \n"+
				  "active \n"+ 
				  "assign letter=s \n"+
				  "create partition extended \n"+
				  "create partition logical \n"+
				  "format fs=ntfs label=\"Logical Volume\" quick compress \n"+
				  "assign letter=h \n"
				  );
		out.write("\n");
		out.close();
		
		boolean f = excuteCommand("cmd /c c:\\diskBat.bat");
		if (dir.isFile() && dir.exists()) {   
		    dir.delete();   
		}
		if (dir1.isFile() && dir1.exists()) {   
		    dir1.delete();   
		}
		if(f){
			return "0x030";
		}
		return "0x031";
	}
	
	/**
	 * �޸İ�ȫ����
	 * @param policyName
	 * @param protocol
	 * @param port
	 * @param IP
	 * @return
	 * @throws IOException
	 */
	public String changeSecRule(String policyName,String protocol, String port,String IP) throws IOException
	{
		try {
			File path = new File("C:/"); // "C:/"Ŀ¼�������
			File dir = new File(path, "changeSecRule.bat");
			if (!dir.exists())
				dir.createNewFile();
			String filePath = "C:\\changeSecRule.bat";
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(
					filePath));
			
			out.write("netsh ipsec static add policy name="+policyName +"\r\n"+
					  "netsh ipsec static add filteraction name=Permit action=permit "+"\r\n"+
					  "netsh ipsec static add filterlist name=SomeIPSomePort"+System.currentTimeMillis()+" "+"\r\n"+
					  "netsh ipsec static add filter filterlist=SomeIPSomePort srcaddr="+IP+" dstaddr=Me dstport="+port+" protocol="+protocol+"\r\n"+
					  "netsh ipsec static add rule name=AllowSomeIPSomePort policy="+policyName+" filterlist=SomeIPSomePort filteraction=Permit"+"\r\n"
					);
			out.write("\r\n");
			out.close();
			
			boolean f = excuteCommand("cmd /c c:\\changeSecRule.bat");
//			if (dir.isFile() && dir.exists()) {   
//			    dir.delete();   
//			}
			if(f){
				return "0x040";
			}
		} catch (Exception e) {
			System.out.println("C:\\changeSecRule.batʧ��");
		}		 		
		return "0x041";
	}
	
	
	/**
	 * �޸ľ�̬IP
	 * @param ip
	 * @throws IOException
	 */
	public String changeIP(String ip,String mask,String gateway,String[] dns) throws IOException {
		writeModIPBat_v2(ip,mask,gateway,dns);
		boolean f = excuteCommand("cmd /c C:\\setupscripts\\modIP.bat");
//		File file = new File("C:\\setupscripts\\modIP.bat");   
//		if (file.isFile() && file.exists()) {   
//		    file.delete();   
//		}
		if(f){
			return "0x050";
		}
		return "0x051";
	}
	/**
	 * �޸ĸ���IP
	 * @param affiIP
	 * @param affiMask
	 * @param affiGateway
	 * @return
	 * @throws IOException
	 */
	public String changeAffiIP(String[] affiIP,String[] affiMask,String[] affiGateway) throws IOException {
		writeModAffiIPBat_v2(affiIP,affiMask,affiGateway);
		boolean f = excuteCommand("cmd /c C:\\setupscripts\\modAffiIP.bat");
//		File file = new File("C:\\setupscripts\\modAffiIP.bat");   
//		if (file.isFile() && file.exists()) {   
//		    file.delete();   
//		}
		if(f){
			return "0x060";
		}
		return "0x061";
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
	 * �����޸�ip��bat�ļ�
	 * @param ip
	 * @throws IOException
	 */
	public static void writeModIPBat(String ip,String mask,String gateway,String[] dns,String[] affiIP,String[] affiMask,String[] affiGateway) throws IOException{
		try {
			File path = new File("C:/"); // "D:/"Ŀ¼�������
			File dir = new File(path, "modifyIP.bat");
			if (!dir.exists())
				dir.createNewFile();
		} catch (Exception e) {
			System.out.println("C:\\modifyIP.bat����ʧ��");
		}		 
		String filePath = "C:\\modifyIP.bat";
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(
				filePath));
		
		String addDNS = "";
		for(int i=1;i<dns.length;i++){
			addDNS = addDNS + "netsh interface ip add dns \"��������\" "+dns[i]+"\n";
		}
		
		String addIPStr = "";
		for(int i=0;i<affiIP.length;i++){
			addIPStr = addIPStr+"netsh interface ip add address \"��������\" "+affiIP[i]+" "+affiMask[i]+"\n";
		}
		
		String addGw = "";
		for(int i=0;i<affiGateway.length;i++){
			addGw = addGw + "add address \"��������\" gateway="+ affiGateway[i]+" gwmetric=1"+"\n";
		}
		//netsh interface ip add address name="��������" source=static addr=192.168.0.2 mask=255.255.255.0
		out.write("netsh interface ip set address \"��������\" static "+ip+" "+mask+" "+gateway+" 1"+"\n"+
				  addIPStr+
				  "netsh interface ip set dns \"��������\" static "+dns[0]+"\n"+
				  addDNS+
				  addGw);
		out.write("\n");
		out.close();
	}
	/**
	 * �����޸�ip��bat�ļ�version2
	 * @param ip
	 * @throws IOException
	 */
	public static void writeModIPBat_v2(String ip,String mask,String gateway,String[] dns) throws IOException{
		String netCardName = getWindowsNetCardName();
//		try {
//			File path = new File("C:/"); // "D:/"Ŀ¼�������
//			File dir = new File(path, "modifyIP.bat");
//			if (!dir.exists())
//				dir.createNewFile();
//		} catch (Exception e) {
//			System.out.println("C:\\modifyIP.bat����ʧ��");
//		}		 
//		String filePath = "C:\\modifyIP.bat";
//		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(
//				filePath));
		
		String addDNS = "";
		for(int i=1;i<dns.length;i++){
			addDNS = addDNS + "netsh interface ip add dns \""+netCardName+"\" "+dns[i]+"\r\n";
		}
		
		//netsh interface ip add address name="��������" source=static addr=192.168.0.2 mask=255.255.255.0
//		out.write("netsh interface ip set address \""+netCardName+"\" static "+ip+" "+mask+" "+gateway+" 1"+"\r\n"+
//				  "netsh interface ip set dns \""+netCardName+"\" static "+dns[0]+"\r\n"+
//				  addDNS);
//		out.write("\n");
//		out.close();
		
		File filepath = new File("C:\\setupscripts\\");
		if (!filepath.isDirectory()) {
			filepath.mkdir();
		}
		File batfile = new File("C:\\setupscripts\\modIP.bat");
		batfile.createNewFile();
		FileOutputStream outStr = new FileOutputStream(batfile);
		BufferedOutputStream buff = new BufferedOutputStream(outStr);
		buff.write(("netsh interface ip set address \""+netCardName+"\" static "+ip+" "+mask+" "+gateway+" 1"+"\r\n"+
				  "netsh interface ip set dns \""+netCardName+"\" static "+dns[0]+"\r\n"+
				  addDNS)
				.getBytes());
		buff.flush();
		buff.close();
		outStr.close();
		
	}
	
	public static void writeModAffiIPBat_v2(String[] affiIP,String[] affiMask,String[] affiGateway) throws IOException{
		String netCardName = getWindowsNetCardName();
//		try {
//			File path = new File("C:/"); // "D:/"Ŀ¼�������
//			File dir = new File(path, "modifyAffiIP.bat");
//			if (!dir.exists())
//				dir.createNewFile();
//		} catch (Exception e) {
//			System.out.println("C:\\modifyAffiIP.bat����ʧ��");
//		}		 
//		String filePath = "C:\\modifyAffiIP.bat";
//		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(
//				filePath));
		
		String addIPStr = "";
		for(int i=0;i<affiIP.length;i++){
			addIPStr = addIPStr+"netsh interface ip add address \""+netCardName+"\" "+affiIP[i]+" "+affiMask[i]+"\r\n";
		}
		
		String addGw = "";
		for(int i=0;i<affiGateway.length;i++){
			addGw = addGw + "netsh interface ip add address \""+netCardName+"\" gateway="+ affiGateway[i]+" gwmetric=1"+"\r\n";
		}
		//netsh interface ip add address name="��������" source=static addr=192.168.0.2 mask=255.255.255.0
//		out.write(addIPStr+
//				  addGw);
//		out.write("\n");
//		out.close();
		
		System.out.println(addIPStr+ addGw);
		File filepath = new File("C:\\setupscripts\\");
		if (!filepath.isDirectory()) {
			filepath.mkdir();
		}
		File batfile = new File("C:\\setupscripts\\modAffiIP.bat");
		batfile.createNewFile();
		FileOutputStream outStr = new FileOutputStream(batfile);
		BufferedOutputStream buff = new BufferedOutputStream(outStr);
		buff.write((addIPStr+
				  addGw)
				.getBytes());
		buff.flush();
		buff.close();
		outStr.close();

	}
	/**
	 * ��ȡҪ��ʽ���Ĵ�������
	 * @return
	 * @throws IOException 
	 */
	private String getDiskName() throws IOException {
		String diskName = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		
		File path = new File("C:/"); // "C:/"Ŀ¼�������
		File dir = new File(path, "diskBat.bat");
		if (!dir.exists())
			dir.createNewFile();
		String filePath = "C:\\diskBat.bat";
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(
				filePath));
		
		out.write("diskpart /s C:\\disktest.txt");
		out.write("\n");
		out.close();
		
		File dir1 = new File(path, "disktxt.txt");
		if (!dir1.exists())
			dir1.createNewFile();
		String filePath1 = "C:\\disktxt.txt";
		OutputStreamWriter out1 = new OutputStreamWriter(new FileOutputStream(
				filePath1));
		
		out.write("list disk \n");
		out.write("\n");
		out.close();
		
		
		try {
			process = Runtime.getRuntime().exec("C:\\diskBat.bat");
			bufferedReader = new BufferedReader(new InputStreamReader(process
					.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
//				System.out.println(line);
				index = line.toLowerCase().indexOf("offline");
				if (index >= 0) {// �ҵ���
//					index = line.indexOf(":");// Ѱ��":"��λ��
					if (index>=0) {
						diskName = line.substring(0,6).trim();//
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
						System.out.println("founnd net card name:"+netName);
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

		return netName;
	}
	
	
	
	public static void main(String[] args) throws IOException{
		WOSConfig c = new WOSConfig();
//		c.stopService("aaachange");
		String[] dns = new String[1];
		dns[0] = "8.8.8.8";
		String[] affiIP= new String[1];
		affiIP[0] = "192.168.0.77";
		String[] affiMask= new String[1];
		affiMask[0]="255.255.255.0";
		String[] affiGateway= new String[1];
		affiGateway[0]="192.168.0.2";
//		writeModIPBat_v2("192.168.0.96", "255.255.255.0", "192.168.0.1", dns);
		writeModAffiIPBat_v2(affiIP,affiMask,affiGateway);
	}
}
