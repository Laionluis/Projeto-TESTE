package com.example.laion.esparta;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    private ArrayList dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView txtName;
        CheckBox checkBox;
    }

    public CustomAdapter(ArrayList data, Context context) {
        super(context, R.layout.item_lista, data);
        this.dataSet = data;
        this.mContext = context;

    }
    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Estrutura_lista getItem(int position) {
        return (Estrutura_lista) dataSet.get(position);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);

            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Estrutura_lista item = getItem(position);
        viewHolder.txtName.setText(item.tarefa);
        viewHolder.checkBox.setChecked(item.checked);
        return result;
    }
}