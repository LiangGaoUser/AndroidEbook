package com.lianggao.whut.androidebook.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
