package com.cn.dyl.util.aes;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Arrays;

public class Encrypt extends AES{
	//文件加密
	public boolean fileEncrypt(File ori,String key,File cur){
		int flag=16;
		allkey=key(this.kformat(key));
		try{
			BufferedOutputStream bufout=new BufferedOutputStream(new FileOutputStream(cur,true));
			BufferedInputStream bufin=new BufferedInputStream(new FileInputStream(ori));
			flag=bufin.read(enbyte);
			//判断是否明文分组足够
			while(flag>15){
				en();
				bufout.write(debyte);
				System.out.println(debyte);
				System.out.println(Arrays.toString(debyte));
				flag=bufin.read(enbyte);
			}
			//判断明文是否剩余
			if(flag>0){
				en();
				bufout.write(debyte);
				System.out.println(debyte);
				System.out.println(Arrays.toString(debyte));
				bufout.write(flag);
			}
			else bufout.write(17);
			bufin.close();
			bufout.close();
			return true;
		}catch(Exception e){cur.delete();return false;}
	}
	//文本加密
	public String textEncrypt(String content,String key){
		int flag=0;String ret="";
		try{
			File temp_Ed=new File("Aes_tempEd.txt");
			File temp_E=new File ("Aes_tempE.txt");
			temp_Ed.createNewFile();
			temp_E.createNewFile();
			//文本流转换文件流
			BufferedWriter bufw=new BufferedWriter(new FileWriter(temp_Ed));
			bufw.write(content);
			bufw.close();
			//调用文件加密算法
			if(fileEncrypt(temp_Ed, key,temp_E)){
				BufferedInputStream bufr=new BufferedInputStream(new FileInputStream(temp_E));
				flag=bufr.read();
				//文件流转换十六进制流
				while(flag!=-1){
					if(flag<16)
						ret+="0";
					ret+=Integer.toHexString(flag);
					flag=bufr.read();}
				bufr.close();
			}
			temp_Ed.delete();
			temp_E.delete();
			return ret;
		}catch(Exception e){return null;}
	}
	//一个明文组加密的实现
	public void en(){
		byte[][] temp=new byte[4][4];
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
				temp[i][j]=enbyte[j*4+i];
		temp=add(temp, allkey[0]);
		for(int i=1;i<10;i++)
			temp=this.add(mix(shift(subbyte(temp))),allkey[i]);
		temp=this.add(this.shift(this.subbyte(temp)), allkey[10]);
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
				debyte[i*4+j]=temp[j][i];
	}
}
