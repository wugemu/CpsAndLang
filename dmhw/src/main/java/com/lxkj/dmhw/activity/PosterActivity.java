package com.lxkj.dmhw.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.UltraPagerAdapter;
import com.lxkj.dmhw.bean.Poster;
import com.lxkj.dmhw.bean.ShareParams;
import com.lxkj.dmhw.bean.TeamUpdataInfo;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.Share;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraDepthScaleTransformer;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PosterActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.poster_invitation)
    TextView posterInvitation;
    @BindView(R.id.poster_copy)
    LinearLayout posterCopy;
    @BindView(R.id.poster_share_one)
    LinearLayout posterShareOne;
    @BindView(R.id.poster_share_two)
    LinearLayout posterShareTwo;
    @BindView(R.id.poster_pager_ultra)
    UltraViewPager posterPagerUltra;
    private UltraPagerAdapter adapter;
    private Poster poster = new Poster();
    private Bitmap QrBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        posterShareOne.setEnabled(false);
        posterInvitation.setText(login.getExtensionid());
        posterPagerUltra.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        // 获取分享地址
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "AppShare", HttpCommon.AppShare);
        showDialog();
    }

    @Override
    public void mainMessage(Message message) {
        //分享海报 只能是邀请任务
        if (message.what == LogicActions.ShareStatusSuccess&&message.arg1==0) {
            paramMap.clear();
            paramMap.put("type", "invite");
            NetworkRequest.getInstance().POST(handler, paramMap, "DayBuy", HttpCommon.finishTask);
        }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        // 获取APP分享地址
        if (message.what == LogicActions.AppShareSuccess) {
            dismissDialog();
            poster = (Poster) message.obj;
            QrBitmap = Utils.generateBitmap(poster.getAppsharelink(), 300, 300);
            adapter = new UltraPagerAdapter(this, poster.getPosterdata(),QrBitmap,login.getExtensionid());
            posterPagerUltra.setAdapter(adapter);
            posterPagerUltra.setMultiScreen(0.8f);
            posterPagerUltra.setPageTransformer(false, new UltraDepthScaleTransformer());
            posterPagerUltra.setInfiniteLoop(true);
            posterShareOne.setEnabled(true);
        }

        //完成任务
        if (message.what == LogicActions.DayBuySuccess) {
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareFinish"), false, 0);
        }
    }

    @OnClick({R.id.back, R.id.poster_copy, R.id.poster_share_one, R.id.poster_share_two,R.id.invite_rule_txt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.invite_rule_txt:
              showRuleDialog();
                break;
            case R.id.poster_copy:
                Utils.copyText(posterInvitation.getText().toString());
                ToastUtil.showImageToast(this,"复制成功",R.mipmap.toast_img);
                break;
            case R.id.poster_share_one:
                Variable.isVisible=0;
                if(poster.getPosterdata()!=null&&poster.getPosterdata().size()>0) {
                    Share.getInstance(0).poster(poster.getPosterdata().get(posterPagerUltra.getCurrentItem()), QrBitmap, login.getExtensionid());
                }
                break;
            case R.id.poster_share_two:
                ShareParams params = new ShareParams();
                params.setContent(Variable.shareContent);
                params.setShareTag(0);
                params.setUrl(poster.getAppsharelink());
                Share.getInstance(0).initialize(params,1);
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("RefreshTaskList"), "", 0);
    }

    //邀请说明弹窗
    private void showRuleDialog(){
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this,R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this,R.layout.dialog_invite_rule,null);
        TextView text1=view.findViewById(R.id.invite_01);
        TextView text2=view.findViewById(R.id.invite_02);
        TextView text3=view.findViewById(R.id.invite_03);
        text1.setText(Html.fromHtml(getResources().getString(R.string.str_invite_01,"邀请信息")));
        text2.setText(Html.fromHtml(getResources().getString(R.string.str_invite_02,"下载APP")));
        text3.setText(Html.fromHtml(getResources().getString(R.string.str_invite_03,"订单佣金")));
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.CENTER);
        //设置弹出动画
        window.setWindowAnimations(R.style.search_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawableResource(R.color.colorTra99);

        dialog.show();
        dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog

        dialog.findViewById(R.id.dis_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.layout_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissDialog();
            }
        });

    }
}
