package com.lianggao.whut.androidebook;


import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bifan.txtreaderlib.main.TxtConfig;
import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;

import java.io.File;

public class Activity_Read extends AppCompatActivity {
    private String tag = "MainActivity";
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0x01;
    private String FilePath = Environment.getExternalStorageDirectory() + "/test4.txt";
    private Boolean Permit = false;
    private EditText mEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        mEditText=(EditText)findViewById(R.id.editText);
        if (CheckPermission()) {
            Permit = true;
            //HwTxtPlayActivity.loadTxtFile(this, "/storage/emulated/0/一剑封仙.txt");
        } else {

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
            Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
            String path="";
            try {

                if(Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT){//4.4以后
                    System.out.println("****************************"+Build.VERSION.SDK_INT+"@@");
                    path=getPath(this,uri);
                    mEditText.setText(path);
                    Toast.makeText(this,path,Toast.LENGTH_SHORT).show();
                }else{
                    System.out.println("##############################");

                    path=getRealPathFromURI(uri);
                    mEditText.setText(path);
                    Toast.makeText(this,path,Toast.LENGTH_SHORT).show();
                }
                loadFile();
            } catch (Exception e) {
                toast("选择出错了");
            }
        }
    }
    public String getRealPathFromURI(Uri contentUri){
        String res=null;
        String[] pros = {MediaStore.Files.FileColumns.DATA};
        Cursor cursor=getContentResolver().query(contentUri,pros,null,null,null);
        String path="";
        if(null!=cursor&&cursor.moveToFirst()) {

            int actual_txt_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            path = cursor.getString(actual_txt_column_index);
            cursor.close();
        }
        return path;

    }

    public String getPath(final Context context, final Uri uri){
        final boolean isKitKat=Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT;
        //DocumentProvider
        if(isKitKat&& DocumentsContract.isDocumentUri(context,uri)){
            if(isExternalStorageDocument(uri)){
                final String docId=DocumentsContract.getDocumentId(uri);
                final String []split=docId.split(":");
                final String type=split[0];
                if("primary".equals(type)){
                    return Environment.getExternalStorageDirectory()+"/"+split[1];
                }
            }
            else if(isDownloadsDocument(uri)){
                final String id=DocumentsContract.getDocumentId(uri);
                final Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(id));
                return getDataColumn(context,contentUri,null,null);
            }
            else if(isMediaDocument(uri)){
                final String docId=DocumentsContract.getDocumentId(uri);
                final String []split=docId.split(":");
                final String type=split[0];
                Uri contentUri=null;

                if("image".equals(type)){
                    contentUri=MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                }else if("video".equals(type)){
                    contentUri=MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                }else if("audio".equals(type)){
                    contentUri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection="_id=?";
                final String []selectionArgs=new String[]{split[1]};
                return getDataColumn(context,contentUri,selection,selectionArgs);
            }

        }
        else if("content".equalsIgnoreCase(uri.getScheme())){
            return getDataColumn(context,uri,null,null);
        }
        else if("file".equalsIgnoreCase(uri.getScheme())){
            return uri.getPath();
        }
        return null;
    }

    public String getDataColumn(Context context,Uri uri,String selection,String []selectionArgs){
        Cursor cursor=null;
        final String column="_data";
        final String[]projection={column};
        try{
            cursor=context.getContentResolver().query(uri,projection,selection,selectionArgs,null);
            if(cursor!=null&&cursor.moveToFirst()){
                final int column_index=cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        }finally {
            if(cursor!=null){
                cursor.close();
            }
            return null;
        }
    }
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }





    public void chooseFile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/plain");//设置类型
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 3);
    }

    public void loadFile() {
        if (Permit) {
            TxtConfig.saveIsOnVerticalPageMode(this,false);
            FilePath = mEditText.getText().toString().trim();
            //FilePath="/storage/emulated/0/一剑封仙.txt";
            Log.i("输出","*********************************"+FilePath+"&&");
            if (TextUtils.isEmpty(FilePath) || !(new File(FilePath)).exists()) {
                toast("文件不存在");
                HwTxtPlayActivity.loadTxtFile(this, FilePath);
            } else {
                HwTxtPlayActivity.loadTxtFile(this, FilePath);
        }
        }
    }


    private Boolean CheckPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Permit = true;
                loadFile();
            } else {
                // Permission Denied
                Toast.makeText(Activity_Read.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private Toast t;

    private void toast(String msg) {
        if (t != null) {
            t.cancel();
        }
        t = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        t.show();
    }
}