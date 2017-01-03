package cn.ipathology.dp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.ipathology.dp.R;
import cn.ipathology.dp.entityClass.Slide;

/**
 * Created by wdb on 2016/10/31.
 * 切图浏览  侧滑栏切换内容
 */

public class AcdSlideBarAdapter extends BaseAdapter {
    private List<Slide> newsList;
    private Context context;
    public int select = 0xFF22FD8E;
    public int unSelect = 0xFFE8EFF8;


    public AcdSlideBarAdapter(Context context, List<Slide> list) {
        this.context = context;
        this.newsList = list;

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;

    }

    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myIndlater = LayoutInflater.from(context);
        ViewHold v1 = null;
        if (convertView == null) {
            convertView = myIndlater.inflate(R.layout.layout_slide_bar, null);
            v1 = new ViewHold();
            v1.title = (TextView) convertView.findViewById(R.id.slide_bar_title);
            v1.message = (TextView) convertView.findViewById(R.id.slide_bar_message);
            v1.img = (ImageView) convertView.findViewById(R.id.slide_bar_img);

            convertView.setTag(v1);
        } else {
            v1 = (ViewHold) convertView.getTag();
        }

        Slide slide = newsList.get(position);
        v1.title.setText(slide.getPathology_no()+slide.getSlide_text());
        v1.message.setText(slide.getWaxblock_memo());
        v1.img.setVisibility(View.GONE);

        if (slide.getCount() == 1){
            v1.title.setTextColor(select);
            v1.message.setTextColor(select);
            v1.img.setVisibility(View.VISIBLE);
        }else{
            v1.title.setTextColor(unSelect);
            v1.message.setTextColor(unSelect);
        }

        return convertView;
    }

    public class ViewHold {
        private ImageView img;      //是否选中
        private TextView title;    //中文标题
        private TextView message;    //中文标题

    }



}
