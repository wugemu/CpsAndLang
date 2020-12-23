package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.HomePage;
import com.lxkj.dmhw.utils.Utils;
import com.taobao.library.BaseBannerAdapter;
import com.taobao.library.VerticalBannerView;

import java.util.ArrayList;

public class OneFragmentVerticalBannerAdapter extends BaseBannerAdapter<HomePage.JGQAppIcon> {

    private Context context;
    private HeadlinesAdapter adapter;

    public OneFragmentVerticalBannerAdapter(Context context, ArrayList<HomePage.JGQAppIcon> list) {
        super(list);
        this.context = context;
    }

    @Override
    public View getView(VerticalBannerView verticalBannerView) {
        return LayoutInflater.from(verticalBannerView.getContext()).inflate(R.layout.adapter_fragment_one_vertical_banner,null);
    }

    @Override
    public void setItem(View view, HomePage.JGQAppIcon item) {
        Utils.displayImage(context, item.getImageurl(), view.findViewById(R.id.adapter_fragment_one_xmarquee_image));
    }
}
