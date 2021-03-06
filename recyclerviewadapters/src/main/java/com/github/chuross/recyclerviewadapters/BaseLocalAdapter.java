package com.github.chuross.recyclerviewadapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.lang.ref.WeakReference;

public abstract class BaseLocalAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements LocalAdapter<VH> {

    private Context context;
    private WeakReference<CompositeRecyclerAdapter> parentAdapter;
    private boolean visible = true;

    public BaseLocalAdapter(@NonNull Context context) {
        this.context = context;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        notifyDataSetChanged();
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
    }

    @Override
    public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
        return false;
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
    }

    /**
     * @return 0 or R.layout
     */
    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public boolean hasStableItemViewType() {
        try {
            return getItemViewType(0) == 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public CompositeRecyclerAdapter getParentAdapter() {
        return hasParentAdapter() ? parentAdapter.get() : null;
    }

    @Override
    public void bindParentAdapter(@Nullable CompositeRecyclerAdapter adapter, @Nullable RecyclerView.AdapterDataObserver dataObserver) {
        if (hasParentAdapter()) {
            throw new IllegalStateException("Adapter already has parentAdapter.");
        }
        parentAdapter = new WeakReference<>(adapter);
        registerAdapterDataObserver(dataObserver);
    }

    @Override
    public void unBindParentAdapter() {
        parentAdapter.clear();
        parentAdapter = null;
    }

    @Override
    public boolean hasParentAdapter() {
        return parentAdapter != null && parentAdapter.get() != null;
    }

    public Context getContext() {
        return context;
    }
}