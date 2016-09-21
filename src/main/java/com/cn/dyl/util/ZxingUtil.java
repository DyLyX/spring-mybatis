package com.cn.dyl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/*
 * 
 */
public class ZxingUtil {

	private static final Map<EncodeHintType, Integer> encodeMap = new HashMap<EncodeHintType, Integer>();

	private static final Map<DecodeHintType, ErrorCorrectionLevel> decodeMap=new HashMap<DecodeHintType, ErrorCorrectionLevel>();
	private static final String charset="UTF-8",format="png";
	private static final int size=150;
	static BitMatrix byteMatrix;    
	private ZxingUtil(){};
	
	
	public static void main(String [] arg){
		try{
			//把文本数据存放到二维码图片中去
			createQRCode("http://blog.csdn.net/xmtblog", new File("D:\\111.png"));
			System.out.println(readQRCode("D:\\111.png"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成
	 * @param data // 二维码内容
	 * @param file  文件位置
	 * @throws IOException
	 * @throws WriterException
	 */
	public static void createQRCode(String data,File file) throws IOException, WriterException{
		//设置二维码空白边框的大小 1-4，1是最小 4是默认的国标
		encodeMap.put(EncodeHintType.MARGIN, 1);
		byteMatrix=new MultiFormatWriter().encode(
				new String(data.getBytes(charset),charset), BarcodeFormat.QR_CODE, size, size,encodeMap);
		MatrixToImageWriter.writeToFile(byteMatrix, format, file);
	}

	
	public static String readQRCode(String filepath) throws NotFoundException, IOException, NotFoundException{
		return new MultiFormatReader().decode(
				new BinaryBitmap(
						new HybridBinarizer(
								new BufferedImageLuminanceSource(
										ImageIO.read(
												new FileInputStream(filepath))))) ,
				decodeMap).getText();
	}

}
