package com.lxkj.dmhw.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.CommodityActivity290;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.activity.MainActivity;
import com.lxkj.dmhw.activity.AliAuthWebViewActivity;
import com.lxkj.dmhw.activity.NewActivity;
import com.lxkj.dmhw.bean.Advertising;
import com.lxkj.dmhw.bean.JDBanner;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.SimpleDialog;
import com.lxkj.dmhw.utils.Utils;


public class AdvertisingDialog extends SimpleDialog<Advertising> {

    private ImageView dialogAdvertisingImage;
    /**
     * 初始化
     * @param context  上下文
     * @param data     数据
     */
    public AdvertisingDialog(Context context, Advertising data) {
        super(context, R.layout.dialog_advertising, data, true, true);
    }

    @Override
    protected void convert(ViewHolder helper) {
        dialogAdvertisingImage = helper.getView(R.id.dialog_advertising_image);
        int imageWidth=data.getWidth();
        int imageHidth=data.getHeight();
        float bili=(float)imageWidth/(float) imageHidth;
        //宽高比例
        if (bili>=1){
            //以屏幕宽为基准
            RelativeLayout.LayoutParams linearParams =(RelativeLayout.LayoutParams) dialogAdvertisingImage.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            float realbili=(float)imageWidth/(float)100;
            int width= (int) (float)((float)(Variable.ScreenWidth)*(float)realbili);
            float rbili=(float)imageHidth/(float) imageWidth;
            int height=(int)(float)((float)width*rbili);
            linearParams.height = height;
            linearParams.width=width;
            dialogAdvertisingImage.setLayoutParams(linearParams);
        }else{
            //以屏幕高为基准
            RelativeLayout.LayoutParams linearParams =(RelativeLayout.LayoutParams) dialogAdvertisingImage.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            float realVbili=(float)( imageHidth)/(float)100;
            int height = (int) ((float)Variable.ScreenwHeight*(float)realVbili);
            float vbili=(float)imageWidth/(float) imageHidth;
            int width=(int)((float)height*vbili);
            linearParams.height = height;
            linearParams.width=width;
            dialogAdvertisingImage.setLayoutParams(linearParams);
        }
        Glide.with(context).load(data.getAdvimg()).into(dialogAdvertisingImage);
        dialogAdvertisingImage.setOnClickListener(this);
        helper.setOnClickListener(R.id.dialog_advertising_cancel, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_advertising_image:
                if (!DateStorage.getLoginStatus()){
                  Intent  intent = new Intent(context, LoginActivity.class);
                  startActivity(intent);
                    return;
                }
                switch (data.getJumptype()) {
                    case "1":
                        startActivity(new Intent(context, CommodityActivity290.class).putExtra("shopId", data.getJumpvaue()).putExtra("source", "all").putExtra("sourceId",""));
                        break;
                    case "2":
                        startActivity(new Intent(context, AliAuthWebViewActivity.class).putExtra(Variable.webUrl, data.getJumpvaue()));
                        break;
                    case "3":
                         startActivity(new Intent(context, AliAuthWebViewActivity.class).putExtra(Variable.webUrl, data.getJumpvaue()).putExtra("isTitle" , true));
                        break;
                    case "4":
                        startActivity(new Intent(context, NewActivity.class).putExtra("topicId", data.getJumpvaue()).putExtra("labelType" , "09"));
                        break;
                    //官方活动
                    case "11":
                        // 请求活动链接
                        if (DateStorage.getLoginStatus()){
                            UserInfo login = DateStorage.getInformation();
                            if ( MainActivity.mainActivity!=null) {
                                MainActivity.mainActivity.getActivityChain(login.getUserid(), data.getJumpvaue());
                            }
                        }else{
                            Intent intent =new Intent(context,LoginActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case "12":
                        Intent intent = new Intent(context, AliAuthWebViewActivity.class);
                        intent.putExtra(Variable.webUrl, data.getJumpvaue());
                        intent.putExtra("isTitle" , false);
                        intent.putExtra("noGoTaoBaoH5",12);
                        startActivity(intent);
                        break;
                    case "14":
                        if (data!=null&&data.getJumpvaue()!=null) {
                            Object bannerObject = JSON.parseObject(data.getJumpvaue(), JDBanner.class);
                            JDBanner banner = (JDBanner) bannerObject;
                            int platformType=1;
                            switch (banner.getCpsType()){
                                case "pdd":
                                    platformType=1;
                                    break;
                                case "jd":
                                    platformType=2;
                                    break;
                                case "wph":
                                    platformType=3;
                                    break;
                                case "sn":
                                    platformType=5;
                                    break;
                                case "kl":
                                    platformType=6;
                                    break;
                            }
                            Utils.getJumpTypeForMorePl(context,banner.getClickType(),banner.getClickVaule(),platformType,banner.getNeedLogin(),banner.getName(),banner.getSysParam());
                        }
                        break;
                }
                hideDialog();
                break;
            case R.id.dialog_advertising_cancel:
                hideDialog();
                break;
        }
    }

}
