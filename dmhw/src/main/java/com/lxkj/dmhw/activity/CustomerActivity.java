package com.lxkj.dmhw.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.bean.Service;
import com.lxkj.dmhw.defined.MultiImage.MultiImageSelector;
import com.lxkj.dmhw.defined.MultiImage.MultiImageSelectorActivity;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.Utils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * 客服设置
 */
public class CustomerActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.customer_save_btn)
    LinearLayout customerSaveBtn;
    @BindView(R.id.customer_qr_image)
    ImageView customerQrImage;
    @BindView(R.id.customer_qr_delete)
    LinearLayout customerQrDelete;
    @BindView(R.id.customer_we_chat_edit)
    EditText customerWeChatEdit;
    // 选择图片回调
    private int REQUEST_IMAGE = 2048;
    // 图片路径
    private String imgPath = "";
    // 微信号
    private String weChatId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        // 获取客服
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "GetService", HttpCommon.GetService);
        showDialog();
    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        dismissDialog();
        if (message.what == LogicActions.GetServiceSuccess) {
            Service service = (Service) message.obj;
            if (!service.getWxqrcode().equals("")) {
                Utils.displayImage(this, service.getWxqrcode(), customerQrImage);
            }
            customerWeChatEdit.setText(service.getWxcode());
            weChatId = service.getWxcode();
        }
        if (message.what == LogicActions.SetServiceSuccess) {
            toast(message.obj + "");
        }
    }

    @OnClick({R.id.back, R.id.customer_save_btn, R.id.customer_qr_image, R.id.customer_qr_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:// 返回
                isFinish();
                break;
            case R.id.customer_save_btn:// 保存
                if (customerWeChatEdit.getText().toString().equals(weChatId)) {
                    if (imgPath.equals("")) {
                        toast("没有修改，无需保存");
                    } else {
                        paramMap.clear();
                        paramMap.put("userid", login.getUserid());
                        paramMap.put("wxcode", customerWeChatEdit.getText().toString());
                        paramMap.put("wxqrcode", Utils.getImageStr(imgPath));
                        NetworkRequest.getInstance().POST(handler, paramMap, "SetService", HttpCommon.SetService);
                        showDialog();
                    }
                } else {
                    if (imgPath.equals("")) {
                        paramMap.clear();
                        paramMap.put("userid", login.getUserid());
                        paramMap.put("wxcode", customerWeChatEdit.getText().toString());
                        paramMap.put("wxqrcode", "");
                        NetworkRequest.getInstance().POST(handler, paramMap, "SetService", HttpCommon.SetService);
                        showDialog();
                    } else {
                        paramMap.clear();
                        paramMap.put("userid", login.getUserid());
                        paramMap.put("wxcode", customerWeChatEdit.getText().toString());
                        paramMap.put("wxqrcode", Utils.getImageStr(imgPath));
                        NetworkRequest.getInstance().POST(handler, paramMap, "SetService", HttpCommon.SetService);
                        showDialog();
                    }
                }
                break;
            case R.id.customer_qr_image:// 二维码图片
                if (customerQrDelete.getVisibility() == View.GONE) {
                    MultiImageSelector.create().showCamera(false).count(1).single().start(this, REQUEST_IMAGE);
                }
                break;
            case R.id.customer_qr_delete:// 删除图片
                customerQrImage.setImageResource(R.mipmap.service_add);
                customerQrDelete.setVisibility(View.GONE);
                break;
        }
    }

    @OnLongClick({R.id.customer_qr_image})
    public boolean onViewLongClicked(View view) {
        switch (view.getId()) {
            case R.id.customer_qr_image:// 添加图片长按
                Drawable.ConstantState state = customerQrImage.getDrawable().getCurrent().getConstantState();
                Drawable.ConstantState states = getResources().getDrawable(R.mipmap.service_add).getConstantState();
                if (!state.equals(states)) {
                    if (customerQrDelete.getVisibility() == View.GONE) {
                        customerQrDelete.setVisibility(View.VISIBLE);
                    } else {
                        customerQrDelete.setVisibility(View.GONE);
                    }
                }
                break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                // 处理你自己的逻辑 ....
                startActivityForResult(new Intent(this, ClipImageActivity.class).putExtra("image", "file://" + path.get(0)), Variable.CLIP_IMAGE);
            }
        }
        if (requestCode == Variable.CLIP_IMAGE) {
            if (data != null) {
                imgPath = data.getExtras().getString("ClipImage");
                Utils.displayImage(this, "file://" + imgPath, customerQrImage);
            }
        }
    }
}
