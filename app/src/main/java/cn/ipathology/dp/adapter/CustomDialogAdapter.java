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
import cn.ipathology.dp.entityClass.CustomDialog;

/**
 * Created by wdb on 2016/10/31.
 * 单选框  适配器
 */

public class CustomDialogAdapter extends BaseAdapter {
    private List<CustomDialog> newsList;
    private Context context;
    private final int type_1 = 0;
    private final int type_2 = 1;

    public CustomDialogAdapter() {
    }

    public CustomDialogAdapter(Context context, List<CustomDialog> list) {
        this.context = context;
        this.newsList = list;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == newsList.size() - 1) {
            return type_2;
            //数据加载完毕
        } else {
            //有数据
            return type_1;
        }
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold v1 = null;
        CancelViewHold cancelViewHold = null;
        LayoutInflater myIndlater = LayoutInflater.from(context);
        int type = getItemViewType(position);

        if (convertView == null) {
            switch (type) {
                case type_1:
                    convertView = myIndlater.inflate(R.layout.custom_dialog_layout, null);
                    v1 = new ViewHold();
                    v1.name = (TextView) convertView.findViewById(R.id.custom_dialog_txt);
                    v1.img = (ImageView) convertView.findViewById(R.id.custom_dialog_img);
                    convertView.setTag(v1);
                    break;
                case type_2:
                    convertView = myIndlater.inflate(R.layout.custom_dialog_no_layout, null);
                    cancelViewHold = new CancelViewHold();
                    cancelViewHold.cancel = (TextView) convertView.findViewById(R.id.custom_cancel_txt);
                    convertView.setTag(cancelViewHold);
                    break;
            }

        } else {
            switch (type) {
                case type_1:
                    v1 = (ViewHold) convertView.getTag();
                    break;
                case type_2:
                    cancelViewHold = (CancelViewHold) convertView.getTag();
                    break;
            }
        }
        switch (type) {
            case type_1:
                CustomDialog customDialog = newsList.get(position);
                v1.name.setText(customDialog.getName());
                if (customDialog.getCount()==0){
                    v1.img.setImageResource(R.mipmap.btn_choice_circular_selected);
                }else{
                    v1.img.setImageResource(R.mipmap.btn_choice_circular_nromal);
                }
                break;
            case type_2:
                cancelViewHold.cancel.setText("取消");
                break;

        }

        return convertView;
    }

    public class ViewHold {
        private ImageView img;      //
        private TextView name;    //中文标题
    }

    public class CancelViewHold {
        private TextView cancel;
    }
}
