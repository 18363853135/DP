package cn.ipathology.dp.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.ipathology.dp.R;
import cn.ipathology.dp.adapter.CustomDialogAdapter;
import cn.ipathology.dp.entityClass.CustomDialog;

/**
 * Created by wdb on 2016/10/31.
 * 自定义ios样式的单选框
 * custom_dialog_listview_layout   样式文件
 * List<CustomDialog>  显示的文字集合
 * TextView  选中时候需要传值的Vie
 *
 */

public class DialogUtil {

    //单选框式的选择对话框
    public void customListViewDialog(Activity context, final List<CustomDialog> customDialogs, final TextView textView) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.custom_dialog_listview_layout, null);
        final CustomDialogAdapter customDialogAdapter = new CustomDialogAdapter(context, customDialogs);

        ListView listView = (ListView) layout.findViewById(R.id.custom_listView);
        listView.setAdapter(customDialogAdapter);

        final AlertDialog builder = new AlertDialog.Builder(context).create();
        builder.setView(layout);
        builder.setCancelable(true);
        builder.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                builder.dismiss();
                if (position != customDialogs.size() - 1) {
                    textView.setText(customDialogs.get(position).getName());
                    unFocus(customDialogs,position);
                    customDialogAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    //点击时先把所有的dialog都显示为未选中状态
    public void unFocus(List<CustomDialog> list,int position){
        for (int i =0;i<list.size();i++){
            if (i == position){
                list.get(i).setCount(1);
            }else{
                list.get(i).setCount(0);
            }
        }

    }

}
