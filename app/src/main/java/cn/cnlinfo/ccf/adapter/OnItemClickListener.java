package cn.cnlinfo.ccf.adapter;

import android.view.View;

abstract class OnItemClickListener<E> implements View.OnClickListener {
    private E model;
    private int position;

    OnItemClickListener(int p, E m) {
        this.model = m;
        this.position = p;
    }


    @Override
    public void onClick(View view) {
        onItemClick(position, model);
    }

    public abstract void onItemClick(int position, E e);
}
