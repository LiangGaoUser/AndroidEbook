package com.lianggao.whut.androidebook.Utils;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Util {

	/**
	 * 根据一个网络连接(String)获取bitmap图像
	 *
	 * @param imageUri
	 * @return
	 * @throws MalformedURLException
	 */
	public static Bitmap getbitmap(String imageUri) {
		Log.v(TAG, "getbitmap:" + imageUri);
		// 显示网络上的图片
		Bitmap bitmap = null;
		try {
			URL myFileUrl = new URL(imageUri);
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.setConnectTimeout(200);
			conn.setReadTimeout(200);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();

			Log.v(TAG, "image download finished." + imageUri);
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			bitmap = null;
		} catch (IOException e) {
			e.printStackTrace();
			Log.v(TAG, "getbitmap bmp fail---");
			bitmap = null;
		}
		return bitmap;
	}

	/**
	 * @description:    批量请求网络图片放入LinkedList集合返回
	 * @param:          LinkedList<String>
	 * @return:         LinkedList<Bitmap>
	 * @author:         梁高
	 * @time:           2020/4/16 
	 */
	public static List<Bitmap> getMultiBitMap(List<String> mulitiBitMapList){
		Bitmap bitmap=null;
		URL url=null;
		InputStream is =null;
		HttpURLConnection conn=null;
		List<Bitmap> bitmapLinkedList=new LinkedList<>();
		Log.i("获取图片",mulitiBitMapList.size()+"");
		for(int i=0;i<mulitiBitMapList.size();i++){
			try {
				url=new URL(mulitiBitMapList.get(i));
				conn=(HttpURLConnection)url.openConnection();
				conn.setDoInput(true);
				conn.setConnectTimeout(1000);
				conn.setReadTimeout(1000);
				conn.connect();
				is = conn.getInputStream();
				bitmap = BitmapFactory.decodeStream(is);
				bitmapLinkedList.add(bitmap);
				Log.i("获取图片","获取图片成功");
				is.close();

			} catch (Exception e) {
				e.printStackTrace();
			}


		}
		Log.i("获取图片","获取图片结束");
		return bitmapLinkedList;
	}

	//根据本地的封面图片名列表获取bitmap集合返回
	public static List<Bitmap> getMultiLocalBitMap(List<String> mulitiBitMapList) {

		List<Bitmap> bitmapList;
		bitmapList=new LinkedList<>();
		try{
			for(int i=0;i<mulitiBitMapList.size();i++){
				FileInputStream fileInputStream=new FileInputStream(mulitiBitMapList.get(i));

				bitmapList.add(BitmapFactory.decodeStream(fileInputStream));
			}
			return bitmapList;
		}catch(Exception e){
			return null;
		}
	}
    //根据路径，复制到另一个路径
    public static boolean copyFile(String oldPath,String newPath){
	    try{
            System.out.println("开始复制文件到本地文件夹");
	        InputStream in=new FileInputStream(new File(oldPath));
	        OutputStream out=new FileOutputStream(new File(newPath));
	        byte[]buff=new byte[1024];
	        int read;
	        while((read=in.read(buff))!=-1){
	            out.write(buff,0,read);
            }
	        in.close();
	        out.flush();
	        out.close();
            System.out.println("完成复制文件到本地文件夹");
	        return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("失败复制文件到本地文件夹");
            return false;
        }
    };

	public static boolean copyFilePost(AssetManager assetManager,String newPath){
		try{
			System.out.println("开始复制封面到本地文件夹");

			InputStream in=assetManager.open("defaultTxtCover.jpg");
			OutputStream out=new FileOutputStream(new File(newPath));
			byte[]buff=new byte[1024];
			int read;
			while((read=in.read(buff))!=-1){
				out.write(buff,0,read);
			}
			in.close();
			out.flush();
			out.close();
			System.out.println("完成复制封面到本地文件夹");
			return true;
		}catch (Exception e){
			System.out.println(e.getMessage());
			System.out.println("失败复制封面到本地文件夹");
			return false;
		}
	};



	public static boolean copyFilePdfPost(AssetManager assetManager,String newPath){
		try{
			System.out.println("开始复制封面到本地文件夹");

			InputStream in=assetManager.open("defaultPdfCover.jpg");
			OutputStream out=new FileOutputStream(new File(newPath));
			byte[]buff=new byte[1024];
			int read;
			while((read=in.read(buff))!=-1){
				out.write(buff,0,read);
			}
			in.close();
			out.flush();
			out.close();
			System.out.println("完成复制封面到本地文件夹");
			return true;
		}catch (Exception e){
			System.out.println(e.getMessage());
			System.out.println("失败复制封面到本地文件夹");
			return false;
		}
	};

	public static boolean deleteAllBook(List<String>pathList,List<String>coverList){
		try {
			System.out.println("开始删除书架文件");
			for(int i=0;i<pathList.size();i++){
				File file=new File(pathList.get(i));
				System.out.println("##"+pathList.get(i));
				file.delete();
			}
			for(int i=0;i<coverList.size();i++){
				File file=new File(coverList.get(i));
				System.out.println("##"+coverList.get(i));
				file.delete();
			}
			System.out.println("完成删除书架文件");
			return  true;

		}catch(Exception e){
			System.out.println("删除书架文件失败");
			return false;
		}
	}


	//书架历史中全部删除封面文件
	public static boolean deleteAllCover(List<String>coverList){
		try {
			System.out.println("开始删除封面文件");

			for(int i=0;i<coverList.size();i++){
				File file=new File(coverList.get(i));
				System.out.println("##"+coverList.get(i));
				file.delete();
			}
			System.out.println("完成删除封面文件");
			return  true;

		}catch(Exception e){
			System.out.println("删除封面文件失败");
			return false;
		}
	}


	public static boolean isPdf(String path){
		File file=new File(path);
		String fileName=file.getName();
		if(fileName.endsWith("txt")){
			return false;
		}else{
			return true;
		}

	}





	private static final String HEX_STRING = "0123456789ABCDEF";

	/**
	 * 把中文字符转换为带百分号的浏览器编码
	 *
	 * @param word
	 * @return
	 */
	public static String toBrowserCode(String word) {
		byte[] bytes = word.getBytes();

		//不包含中文，不做处理
		if (bytes.length == word.length())
			return word;

		StringBuilder browserUrl = new StringBuilder();
		String tempStr = "";

		for (int i = 0; i < word.length(); i++) {
			char currentChar = word.charAt(i);

			//不需要处理
			if ((int) currentChar <= 256) {

				if (tempStr.length() > 0) {
					byte[] cBytes = tempStr.getBytes();

					for (int j = 0; j < cBytes.length; j++) {
						browserUrl.append('%');
						browserUrl.append(HEX_STRING.charAt((cBytes[j] & 0xf0) >> 4));
						browserUrl.append(HEX_STRING.charAt((cBytes[j] & 0x0f) >> 0));
					}
					tempStr = "";
				}

				browserUrl.append(currentChar);
			} else {
				//把要处理的字符，添加到队列中
				tempStr += currentChar;
			}
		}
		return browserUrl.toString();
	}














	public static int calculateInSampleSize(BitmapFactory.Options options,
											int reqWidth, int reqHeight) {
		// 源图片的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			// 计算出实际宽高和目标宽高的比率
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
			// 一定都会大于等于目标的宽和高。
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	public static Bitmap decodeSampledBitmapFromFilePath(String imagePath,
														 int reqWidth, int reqHeight) {
		// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap;
		BitmapFactory.decodeFile(imagePath, options);
		// 调用上面定义的方法计算inSampleSize值
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		// 使用获取到的inSampleSize值再次解析图片
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(imagePath,options);
	}


	/**
	 * @description:   压缩图片让其显示，解决Bitmap too large to be uploaded into a texture 的问题
	 * @param:
	 * @return:
	 * @author:         梁高
	 * @time:           2020/4/16
	 */
/*	public List<Bitmap> bitmapDensity(List<Bitmap>bitmapList){
		for(int i=0;i<bitmapList.size();i++){
			if(bitmapList.get(i)!=null){

			}


		}
	}*/

}
