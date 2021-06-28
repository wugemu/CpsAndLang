package com.lxkj.dmhw.activity.self;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.ButtonUtil;
import com.example.test.andlang.util.RegexUtil;
import com.example.test.andlang.util.ToastUtil;
import com.example.test.andlang.widget.editview.CleanableEditText;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.AddrBean;
import com.lxkj.dmhw.bean.self.ProvinceBean;
import com.lxkj.dmhw.model.SettingModel;
import com.lxkj.dmhw.presenter.SettingPresenter;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.widget.WheelAddrOnline;
import com.lxkj.dmhw.widget.wheel.SelectAddressInterface;

import java.util.List;
import java.util.Observable;

import butterknife.BindView;
import butterknife.OnClick;

public class AddrDetailActivity extends BaseLangActivity<SettingPresenter> {
    private final int PERMISSON_REQUESTCODE = 0;

    @BindView(R.id.et_real_name)
    CleanableEditText et_real_name;
    @BindView(R.id.et_phone_number)
    CleanableEditText et_phone_number;
    @BindView(R.id.et_address)
    CleanableEditText et_address;
    @BindView(R.id.tv_area)
    TextView tv_area;
    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private WheelAddrOnline wheelAddrOnline;
    private AddrBean addrBean;
    private String province, city, area;
//    private AMapLocationClient locationClient = null;
//    private AMapLocationClientOption locationOption = null;
    @Override
    public int getLayoutId() {
        return R.layout.activity_addr_detail;
    }

    @Override
    public void initView() {
        initLoading();
//        et_real_name.setFilters(new InputFilter[]{new MyInputFilter.LengthFilter(20),new MyInputFilter()});

        initLocation();//定位初始化
    }

    @Override
    public void initPresenter() {
        presenter=new SettingPresenter(AddrDetailActivity.this, SettingModel.class);
    }

    @Override
    public void initData() {
        Intent intent=getIntent();
        if(intent!=null){
            addrBean=(AddrBean) intent.getSerializableExtra("addrBean");
        }
        if(addrBean!=null) {
            initTitleBar(true, "修改地址", "保存", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //保存地址
                    save();
                }
            });
            et_real_name.setText(addrBean.getPersonName());
            try {
                et_real_name.setSelection(et_real_name.getText().toString().length());//将光标移至文字末尾
            }catch (Exception e){

            }
            et_phone_number.setText(addrBean.getServNum());
            province=addrBean.getProvince();
            city=addrBean.getCity();
            area=addrBean.getArea();
            tv_area.setText(province+" "+city+" "+area);
            tv_area.setTextColor(getResources().getColor(R.color.colorBlackText));
            et_address.setText(addrBean.getAddress());
        }else {
            initTitleBar(true, "添加地址", "保存", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    save();
                }
            });
        }
    }


    public void clickAddrLocation(){
        //点击定位
        // 启动定位
//        if(ButtonUtil.isFastDoubleClick(R.id.iv_addr_location)){
//            ToastUtil.show(AddrDetailActivity.this, R.string.tip_btn_fast);
//            return;
//        }
//        //权限申请
//        PackageManager pkgManager = getPackageManager();
//        boolean locationPermission =
//                pkgManager.checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, getPackageName())
//                        == PackageManager.PERMISSION_GRANTED;
//        boolean fineLocalPermission =
//                pkgManager.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, getPackageName())
//                        == PackageManager.PERMISSION_GRANTED;
//
//        if (Build.VERSION.SDK_INT >= 23&&(!locationPermission||!fineLocalPermission)) {
//            ActivityCompat.requestPermissions(this, needPermissions,
//                    PERMISSON_REQUESTCODE);
//        }else if(locationClient!=null) {
//            locationClient.startLocation();
//        }
    }

    @OnClick({R.id.ll_select_area,R.id.iv_addr_location})
    public void selectArea(){
        List<ProvinceBean> provinceBeanList=presenter.model.getProvinceBeanList();
        if(provinceBeanList!=null){
            if(wheelAddrOnline==null){
                wheelAddrOnline=new WheelAddrOnline(AddrDetailActivity.this, provinceBeanList,new SelectAddressInterface() {
                    @Override
                    public void setAreaString(String area) {
                        updateAddr(area);
                    }

                    @Override
                    public void setArea(String prov, String cit, String are) {
                        province=prov;
                        city=cit;
                        area=are;
                    }
                });
            }
            wheelAddrOnline.showDialog();
        }else {
            showWaitDialog();
            presenter.reqAddrOnlineList();
        }
    }

    public void save(){
        if (checkInput()) {
            if (ButtonUtil.isFastDoubleClick(R.id.btn_submit)) {
                ToastUtil.show(this, R.string.tip_btn_fast);
                return;
            }
            if(addrBean==null) {
                //添加新地址
                String personName = et_real_name.getText().toString().trim();
                String servNum = et_phone_number.getText().toString().trim();
                String address = et_address.getText().toString().trim();
                showWaitDialog();
                presenter.reqAddAddress(personName, servNum, province, city, area, address, 0);
            }else {
                //修改地址
                String personName = et_real_name.getText().toString().trim();
                String servNum = et_phone_number.getText().toString().trim();
                String address = et_address.getText().toString().trim();
                showWaitDialog();
                presenter.reqEditAddress(addrBean.getId(),personName, servNum, province, city, area, address, addrBean.getFlag());
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if("reqAddrOnlineList".equals(arg)){
            List<ProvinceBean> provinceBeanList=presenter.model.getProvinceBeanList();
            if(provinceBeanList!=null){
                wheelAddrOnline=new WheelAddrOnline(AddrDetailActivity.this,provinceBeanList,new SelectAddressInterface() {
                    @Override
                    public void setAreaString(String area) {
                        updateAddr(area);
                    }

                    @Override
                    public void setArea(String prov, String cit, String are) {
                        province=prov;
                        city=cit;
                        area=are;
                    }
                });
                wheelAddrOnline.showDialog();
            }
        }else if("reqAddAddress".equals(arg)){
            ToastUtil.show(this,"保存成功");
            //添加地址成功
            if(wheelAddrOnline!=null){
                wheelAddrOnline=null;
            }
            ActivityUtil.getInstance().exitResult(AddrDetailActivity.this,null,RESULT_OK);
        }else if("reqEditAddress".equals(arg)){
            ToastUtil.show(this,"保存成功");
            //修改成功
            if(wheelAddrOnline!=null){
                wheelAddrOnline=null;
            }
            ActivityUtil.getInstance().exitResult(AddrDetailActivity.this,null,RESULT_OK);
        }
    }

    public void updateAddr(String addr){
        tv_area.setText(addr);
        tv_area.setTextColor(getResources().getColor(R.color.colorBlackText));
    }

    private boolean checkInput() {
        if(BBCUtil.isEmpty(et_real_name.getText().toString().trim())){
            ToastUtil.show(this,"请输⼊收件⼈姓名");
            return false;
        }
        if(et_real_name.getText().toString().trim().length()<2){
            ToastUtil.show(this,"姓名不能少于两个字");
            return false;
        }
        if(BBCUtil.isEmpty(et_phone_number.getText().toString().trim())){
            ToastUtil.show(this,"请输入手机号码");
            return false;
        }
        if(!RegexUtil.isPhoneNumber(et_phone_number.getText().toString().trim())){
            ToastUtil.show(this, "请输入正确的手机号");
            return false;
        }
        if(BBCUtil.isEmpty(province)){
            ToastUtil.show(this,"请选择地址");
            return false;
        }
        if(BBCUtil.isEmpty(et_address.getText().toString().trim())){
            ToastUtil.show(this,"请填写详细地址");
            return false;
        }
        if(et_address.getText().toString().trim().length()<6){
            ToastUtil.show(this,"地址不能少于6个字");
            return false;
        }
//        if(et_address.getText().toString().trim().length()>40){
//            ToastUtil.show(this,"地址过长不正确");
//            return false;
//        }
        return true;
    }

    /**
     * 初始化定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void initLocation(){
        //初始化client
//        locationClient = new AMapLocationClient(this.getApplicationContext());
//        locationOption = getDefaultOption();
//        //设置定位参数
//        locationClient.setLocationOption(locationOption);
//        // 设置定位监听
//        locationClient.setLocationListener(locationListener);
    }
    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author hongming.wang
     *
     */
//    private AMapLocationClientOption getDefaultOption(){
//        AMapLocationClientOption mOption = new AMapLocationClientOption();
//        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
//        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
//        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
//        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
//        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
//        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
//        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
//        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
//        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
//        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
//        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
//        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
//        return mOption;
//    }

    /**
     * 定位监听
     */
//    private AMapLocationListener locationListener = new AMapLocationListener() {
//        @Override
//        public void onLocationChanged(AMapLocation location) {
//            if (null != location) {
//
//                StringBuffer sb = new StringBuffer();
//                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
//                if(location.getErrorCode() == 0){
//                    sb.append("定位成功" + "\n");
//                    sb.append("定位类型: " + location.getLocationType() + "\n");
//                    sb.append("经    度    : " + location.getLongitude() + "\n");
//                    sb.append("纬    度    : " + location.getLatitude() + "\n");
//                    sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
//                    sb.append("提供者    : " + location.getProvider() + "\n");
//
//                    sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
//                    sb.append("角    度    : " + location.getBearing() + "\n");
//                    // 获取当前提供定位服务的卫星个数
//                    sb.append("星    数    : " + location.getSatellites() + "\n");
//                    sb.append("国    家    : " + location.getCountry() + "\n");
//                    sb.append("省            : " + location.getProvince() + "\n");
//                    sb.append("市            : " + location.getCity() + "\n");
//                    sb.append("城市编码 : " + location.getCityCode() + "\n");
//                    sb.append("区            : " + location.getDistrict() + "\n");
//                    sb.append("区域 码   : " + location.getAdCode() + "\n");
//                    sb.append("地    址    : " + location.getAddress() + "\n");
//                    sb.append("兴趣点    : " + location.getPoiName() + "\n");
//                } else {
//                    //定位失败
//                    sb.append("定位失败" + "\n");
//                    sb.append("错误码:" + location.getErrorCode() + "\n");
//                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
//                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
//                }
//                sb.append("***定位质量报告***").append("\n");
//                sb.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启":"关闭").append("\n");
//                sb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n");
//                sb.append("* 网络类型：" + location.getLocationQualityReport().getNetworkType()).append("\n");
//                sb.append("* 网络耗时：" + location.getLocationQualityReport().getNetUseTime()).append("\n");
//                sb.append("****************").append("\n");
//
//                //解析定位结果，
//                String result = sb.toString();
//                LogUtil.d("0.0",result);
//
//                province=location.getProvince();
//                city=location.getCity();
//                area=location.getDistrict();
//                updateAddr(province + " " + city + " " + area);
//            }
//        }
//    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLocation();
    }

    /**
     * 销毁定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void destroyLocation(){
//        if (null != locationClient) {
//            /**
//             * 如果AMapLocationClient是在当前Activity实例化的，
//             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
//             */
//            locationClient.onDestroy();
//            locationClient = null;
//            locationOption = null;
//        }
    }

    //用户选择允许或拒绝后，会回调onRequestPermissionsResult方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }
    //根据requestCode和grantResults(授权结果)做相应的后续处理
    private void doNext(int requestCode, int[] grantResults) {

        if (requestCode == BBCUtil.REQUEST_PERMISSION) {
            if (grantResults == null || grantResults.length <= 1) {
                return;
            }
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // 开启读写权限
//                if(locationClient!=null) {
//                    locationClient.startLocation();
//                }
            } else {
                ToastUtil.show(this, "请开启定位权限");
            }
        }
    }
}