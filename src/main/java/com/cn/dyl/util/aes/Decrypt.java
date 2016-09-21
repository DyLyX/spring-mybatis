package com.cn.dyl.util.aes;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

public class Decrypt extends AES{
	//文件解密的实现
	public boolean fileDecrypt(File ori,String key,File cur){
		//读取长度
		long flag=ori.length();
		//读取组数
		long by=flag/16;
		//判断是否可解密（加密完成后会在末尾添加一个字节用于描述前一组明文的填充数，所以分组数mod16必为1）
		if(flag%16==1){
			allkey=key(kformat(key));
			try{
				//包装流
				BufferedOutputStream bufout=new BufferedOutputStream(new FileOutputStream(cur,true));
				BufferedInputStream bufin=new BufferedInputStream(new FileInputStream(ori));							
				while(by>1){
					//读取一个密文组到debyte[16]
					bufin.read(debyte);
					de();
					//写入一个解密组
					bufout.write(enbyte);
					by--;
				}
				bufin.read(debyte);
				de();
				int fill=bufin.read();
				//判断是否有填充
				if(fill>16)
					bufout.write(enbyte);
				else{
					byte[]last=new byte[fill];
					for(byte i=0;i<last.length;i++)
						last[i]=enbyte[i];
					bufout.write(last);
				}
				bufin.close();
				bufout.close();
				return true;
			}catch(Exception e){return false;}
		}else return false;
	}
	//文本解密的实现
	public String textDecrypt(String content,String key){
		int flag=0;char[] bufchar=new char[1];String ret="";
		try{
			File temp_Dd=new File("Aes_tempE.txt");
			File temp_D=new File("Aes_tempD.txt");
			temp_Dd.createNewFile();
			temp_D.createNewFile();
			BufferedOutputStream bufw=new BufferedOutputStream(new FileOutputStream(temp_Dd));
			int index=content.length()/2;
			//文本十六进制转换为文件流
			byte[] b=new byte[index];
			for(int i=0;i<index;i++){
				b[i]=(byte)Integer.parseInt(content.substring(i*2,i*2+2),16);
				
			}
			bufw.write(b);
			bufw.close();
			//调用文件解密算法
			if(fileDecrypt(temp_Dd, key, temp_D)){
				BufferedReader bufr=new BufferedReader(new FileReader(temp_D));
				flag=bufr.read(bufchar);				
				while(flag!=-1){
					ret+=String.valueOf(bufchar);
					flag=bufr.read(bufchar);}
				bufr.close();
			}
			temp_Dd.delete();
			temp_D.delete();
			return ret;
		}catch(Exception e){return null;}
	}
	//一个明文组的加密实现
	public void de(){
		byte[][] temp=new byte[4][4];
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
				temp[i][j]=debyte[j*4+i];	
		temp=add(temp,allkey[10]);
		for(int i=1;i<10;i++)
			temp=mix(add(subbyte(shift(temp,-1),-1),allkey[10-i]),-1);
		temp=add(subbyte(shift(temp, -1), -1), allkey[0]);
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
				enbyte[i*4+j]=temp[j][i];
	}
}
