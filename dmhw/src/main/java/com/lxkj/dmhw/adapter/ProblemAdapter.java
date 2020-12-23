package com.lxkj.dmhw.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.Help;

/**
 * 分类问题列表
 * Created by zyhant on 18-2-28.
 */

public class ProblemAdapter extends BaseQuickAdapter<Help, BaseViewHolder> {

    public ProblemAdapter() {
        super(R.layout.adapter_problem);
    }

    @Override
    protected void convert(BaseViewHolder helper, Help item) {
        helper.setText(R.id.adapter_problem_text, item.getProblemname());
    }
}
