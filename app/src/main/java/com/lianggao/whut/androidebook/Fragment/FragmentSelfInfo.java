package com.lianggao.whut.androidebook.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lianggao.whut.androidebook.Activity_General_Thought;
import com.lianggao.whut.androidebook.Activity_History;
import com.lianggao.whut.androidebook.Activity_Star;
import com.lianggao.whut.androidebook.Model.QQLoginManager;
import com.lianggao.whut.androidebook.Model.Result;
import com.lianggao.whut.androidebook.Net.HttpCaller;
import com.lianggao.whut.androidebook.Net.NameValuePair;
import com.lianggao.whut.androidebook.R;
import com.lianggao.whut.androidebook.Utils.Util;
import com.lianggao.whut.androidebook.View.DrawableTextView;
/*import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;*/
import com.shehuan.niv.NiceImageView;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class FragmentSelfInfo extends ViewPageFragment implements QQLoginManager.QQLoginListener {
    private TextView textViewYueLi;
    private TextView textViewNote;
    private TextView textViewThought;
    private TextView textViewStar;
    private TextView textViewHistory;
    private TextView textViewRemind;
    private TextView textViewPlan;
    private TextView textViewSetting;
    private NiceImageView niceImageView;
    private PopupWindow pop;




    private QQLoginManager.QQLoginListener qqLoginListener;
    private QQLoginManager qqLoginManager;
    private final int  MSG_LOGIN_SUCCESS=1;
    private final int MSG_LOGIN_CANCLE=0;
    private final int MSG_LOGIN_ERROR=-1;
    private final int MSG_GET_SHAREPREFERENCE=2;
    private TextView textView;//存放姓名
    private String nickname;
    private String flag;
    private Bitmap picture;


    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case MSG_LOGIN_SUCCESS:
                    System.out.println("登录成功"+nickname+"  "+flag);
                    Bitmap bitmap=(Bitmap)msg.obj;
                    niceImageView.setImageBitmap(bitmap);
                    textView.setText(nickname);
                    break;
                case MSG_GET_SHAREPREFERENCE:
                    niceImageView.setImageBitmap(picture);
                    textView.setText(nickname);
                    System.out.println("得到sharePreference");
                    break;

            }
        }
    };




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView=inflater.inflate(R.layout.fragment_selfinfo,null);
        }










        qqLoginManager=new QQLoginManager("1110404432",getContext(),this);

        niceImageView=(NiceImageView)rootView.findViewById(R.id.id_niv);
        textViewYueLi=(TextView)rootView.findViewById(R.id.id_book_yeuli) ;
        textViewNote=(TextView)rootView.findViewById(R.id.id_book_note) ;
        textViewThought=(TextView)rootView.findViewById(R.id.id_book_thought) ;
        textViewStar=(TextView)rootView.findViewById(R.id.id_book_star) ;
        textViewHistory=(TextView)rootView.findViewById(R.id.id_book_history) ;
        textViewRemind=(TextView)rootView.findViewById(R.id.id_book_remind) ;
        textViewSetting=(TextView)rootView.findViewById(R.id.id_book_setting) ;
        textViewPlan=(TextView)rootView.findViewById(R.id.id_book_plan);

        textView=(TextView)rootView.findViewById(R.id.id_tv_name) ;

        Drawable back= getResources().getDrawable(R.drawable.icon_book_search2);
        Drawable yueli= getResources().getDrawable(R.drawable.icon_book_yueli);
        Drawable note= getResources().getDrawable(R.drawable.icon_book_note);
        Drawable thought= getResources().getDrawable(R.drawable.icon_book_thought);
        Drawable star= getResources().getDrawable(R.drawable.icon_book_star);
        Drawable history= getResources().getDrawable(R.drawable.icon_book_history);
        Drawable plan= getResources().getDrawable(R.drawable.icon_book_plan);
        Drawable remind= getResources().getDrawable(R.drawable.icon_book_remind);
        Drawable setting= getResources().getDrawable(R.drawable.icon_book_setting);


        back.setBounds(0, 0, 50, 50);
        yueli.setBounds(0, 0, 50, 50);
        note.setBounds(0, 0, 50, 50);
        thought.setBounds(0, 0, 50, 50);
        history.setBounds(0, 0, 50, 50);
        star.setBounds(0, 0, 50, 50);
        plan.setBounds(0, 0, 50, 50);
        remind.setBounds(0, 0, 50, 50);
        setting.setBounds(0, 0, 50, 50);


        textViewYueLi.setCompoundDrawables(null, yueli, null, null);
        textViewNote.setCompoundDrawables(null, note, null, null);
        textViewThought.setCompoundDrawables(null, thought, null, null);
        textViewStar.setCompoundDrawables(star, null, back, null);
        textViewHistory.setCompoundDrawables(history, null, back, null);
        textViewRemind.setCompoundDrawables(remind, null, back, null);
        textViewSetting.setCompoundDrawables(setting, null, back, null);
        textViewPlan.setCompoundDrawables(plan, null, back, null);

        getSharePreference();
        niceImageView.setOnClickListener(new View.OnClickListener() {//点击头像进行登录
            @Override
            public void onClick(View v) {
                //showPop();
                Log.i("登录","开始登录");
                qqLoginManager.launchQQLogin();
                Log.i("登录","结束登录");
            }
        });

        textViewThought.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Activity_General_Thought.class);
                startActivity(intent);
            }
        });
        textViewStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Activity_Star.class);
                startActivity(intent);
            }
        });

        textViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Activity_History.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
   /* @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser){
            //Log.i("MainActivityBook","MainActivity出现时这里可以加载数据");
        }else{
            //Log.i("MainActivityBook","退出时执行操作");
        }
        super.setUserVisibleHint(isVisibleToUser);
    }*/
    @Override
    public void onStart() {
        super.onStart();
        //mVp.setCurrentItem(number);
        Log.i("Fragment","FragmentSelfInfo onStart");
        // Toast.makeText(getContext(),"点击了按钮"+number,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i("Fragment","FragmentSelfInfo onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Fragment","FragmentSelfInfo onRause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("Fragment","FragmentSelfInfo onStop");
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if(isVisible){
            Log.i("Fragment","FragmentSelfInfo is visible");
        }else{
            Log.i("FragmentSelfInfo","FragmentSelfInfo is not visible");
        }
    }

    @Override
    public void onQQLoginSuccess(final JSONObject jsonObject) {
        new Thread(){
            @Override
            public void run() {
                Message msg=new Message();
                msg.what=MSG_LOGIN_SUCCESS;
                Bitmap bitmap= null;
                try {
                    Log.i("登录",jsonObject.getString("figureurl_qq_2"));
                    bitmap = Util.getbitmap(jsonObject.getString("figureurl_qq_2"));
                    nickname=jsonObject.getString("nickname");
                    flag=jsonObject.getString("open_id");
                    picture=bitmap;
                    //存储放入SharePreference
                    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
                    String picture=new String(Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT));
                    SharedPreferences sp=getContext().getSharedPreferences("QQFile", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("picture",picture);
                    editor.putString("nickname",nickname);
                    editor.putString("flag",flag);
                    editor.commit();

                    List<NameValuePair> postParam = new ArrayList<>();
                    postParam.add(new NameValuePair("user_id",flag));
                    HttpCaller.getInstance().postSyncResult(Result.class,"http://192.168.1.4:8080/com.lianggao.whut/Post_User_Account_Servlet",postParam);



                    msg.obj=bitmap;
                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onQQLoginCancel() {
        System.out.println("取消登录");
    }

    @Override
    public void onQQLoginError(UiError uiError) {
        System.out.println("登录失败");
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 回调
        qqLoginManager.onActivityResultData(requestCode, resultCode, data);
    }

    public void getSharePreference(){
        new Thread(){
            @Override
            public void run() {
                picture=null;
                SharedPreferences sp=getContext().getSharedPreferences("QQFile", Context.MODE_PRIVATE);
                String img=sp.getString("picture","");
                nickname=sp.getString("nickname","");
                flag=sp.getString("flag","");

                if(img!=""){

                    byte[] decode=Base64.decode(img.getBytes(),1);
                    picture= BitmapFactory.decodeByteArray(decode,0,decode.length);
                    Message message=new Message();
                    message.what=MSG_GET_SHAREPREFERENCE;
                    handler.sendMessage(message);
                }
            }
        }.start();
    }





/*

    private void showPop() {
        View bottomView = View.inflate(getContext(), R.layout.part_layout_bottom_dialog, null);
        TextView mAlbum = bottomView.findViewById(R.id.tv_album);
        TextView mCamera = bottomView.findViewById(R.id.tv_camera);
        TextView mCancel = bottomView.findViewById(R.id.tv_cancel);

        pop = new PopupWindow(bottomView, -1, -2);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.5f;
        getActivity().getWindow().setAttributes(lp);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        pop.setAnimationStyle(R.style.main_menu_photo_anim);
        pop.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.tv_album:
                        //相册
                        PictureSelector.create(FragmentSelfInfo.this)
                                .openGallery(PictureMimeType.ofImage())
                                .maxSelectNum(1)
                                .minSelectNum(1)
                                .imageSpanCount(4)
                                .selectionMode(PictureConfig.MULTIPLE)
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.tv_camera:
                        //拍照
                        PictureSelector.create(FragmentSelfInfo.this)
                                .openCamera(PictureMimeType.ofImage())
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.tv_cancel:
                        //取消
                        //closePopupWindow();
                        break;
                }
                closePopupWindow();
            }
        };

        mAlbum.setOnClickListener(clickListener);
        mCamera.setOnClickListener(clickListener);
        mCancel.setOnClickListener(clickListener);
    }

    public void closePopupWindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            pop = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> images;
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {// 图片选择结果回调

                images = PictureSelector.obtainMultipleResult(data);
                LocalMedia media = images.get(0);
                int mimeType = media.getMimeType();
                String path;
                path = media.getPath();

                Log.i("原图地址::", media.getPath());
                int pictureType = PictureMimeType.isPictureType(media.getPictureType());

                long duration = media.getDuration();


                niceImageView.setDrawingCacheEnabled(true);
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.color.colorLiteGray)
                        .diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(this.getContext())
                        .load(path)
                        .apply(options)
                        .into(niceImageView);
                niceImageView.setDrawingCacheEnabled(false);//这里一定要清楚图片的缓存


            }

            }
        }
*/










}
