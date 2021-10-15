package com.srija.lab_a1_a2_android_srija_c0813274.adapters;

public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
    void onItemEdit(int position);
}