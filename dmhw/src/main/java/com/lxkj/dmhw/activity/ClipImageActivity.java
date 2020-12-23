package com.lxkj.dmhw.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.defined.ClipImageView;
import com.lxkj.dmhw.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClipImageActivity extends BaseActivity {

    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.clip_image_btn)
    LinearLayout clipImageBtn;
    @BindView(R.id.clip_image_pic)
    ClipImageView clipImagePic;
    @BindView(R.id.bar)
    View bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_image);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        Utils.displayImage(this, getIntent().getExtras().getString("image"), clipImagePic);
    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {

    }

    @OnClick({R.id.back, R.id.clip_image_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.clip_image_btn:
                Bitmap bitmap = clipImagePic.clip();
                String content = Utils.saveFile(Variable.clipImagePath, bitmap, 100, false);
                Intent intent = new Intent();
                intent.putExtra("ClipImage", content);
                setResult(Variable.CLIP_IMAGE, intent);
                isFinish();
                break;
        }
    }
}
