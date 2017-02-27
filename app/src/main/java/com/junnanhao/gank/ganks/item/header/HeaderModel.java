package com.junnanhao.gank.ganks.item.header;

import android.support.annotation.DrawableRes;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.junnanhao.gank.R;
import com.junnanhao.gank.base.BaseEpoxyHolder;

import butterknife.BindView;

/**
 * Created by Jonas on 2017/2/25.
 * Header
 */
@EpoxyModelClass(layout = R.layout.ganks_header_content)
public abstract class HeaderModel extends EpoxyModelWithHolder<HeaderModel.HeaderHolder> {

    @EpoxyAttribute
    String text;
    @EpoxyAttribute
    @DrawableRes
    int icon;

    @Override
    protected HeaderHolder createNewHolder() {
        return new HeaderHolder();
    }

    @Override
    public void bind(HeaderHolder holder) {
        holder.icon.setImageResource(icon);
        holder.text.setText(text);
    }

    static class HeaderHolder extends BaseEpoxyHolder {
        @BindView(R.id.header_ic) ImageView icon;
        @BindView(R.id.header_text) TextView text;
    }


}
