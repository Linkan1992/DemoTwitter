package com.demotwitter.linkan.demotwitter.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.demotwitter.linkan.demotwitter.databinding.EmptyFeedRowLayoutBinding;
import com.demotwitter.linkan.demotwitter.databinding.PagingProgressLoaderBinding;

import java.util.List;


public abstract class BaseRecyclerViewAdapter<T, K extends BaseRecyclerViewAdapter.BaseViewHolder> extends RecyclerView.Adapter<K> {

    private EmptyFeedViewModel emptyFeedViewModel;

    private static final int VIEW_TYPE_EMPTY = 0;

    private static final int VIEW_TYPE_NORMAL = 1;

    private final int VIEW_TYPE_PAGING_PROGRESS = 2;

    // Allows to remember the last item shown on screen
    protected int lastPosition = -1;

    private BaseRecyclerViewAdapterListener mListener;

    protected List<T> data;

    public boolean loading = false;

    public BaseRecyclerViewAdapter(List<T> data) {
        this.data = data;
    }

    public interface BaseRecyclerViewAdapterListener {
        void onRetryClick();
    }

    public void setListener(BaseRecyclerViewAdapterListener listener) {
        this.mListener = listener;
    }

    @Override
    public K onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {

            case VIEW_TYPE_NORMAL:

                return mOnCreateViewHolder(parent, viewType);

            case VIEW_TYPE_PAGING_PROGRESS:
                PagingProgressLoaderBinding pagingLoaderBinding = PagingProgressLoaderBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return (K) new PagingProgressViewHolder(pagingLoaderBinding);

            case VIEW_TYPE_EMPTY:
                EmptyFeedRowLayoutBinding emptyFeedRowLayoutBinding = EmptyFeedRowLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return (K) new EmptyFeedViewHolder(emptyFeedRowLayoutBinding);

            default:
                return mOnCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(K holder, int position) {
        if (holder != null && !data.isEmpty())
            holder.onBind(data.get(position));
        else if (data.isEmpty())
            holder.onBind(null);
    }

    @Override
    public int getItemCount() {
        if (!data.isEmpty()) {
            return data.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (data != null
                && data.size() > 0
                && data.get(position) == null) {
            return VIEW_TYPE_PAGING_PROGRESS;
        } else if (!data.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else
            return VIEW_TYPE_EMPTY;
    }

    public void addItems(List<T> repoList) {

/*        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(
                new MyDiffCallback(data, repoList));
        data = repoList;
        Observable.create((ObservableOnSubscribe<DiffUtil.DiffResult>)
                emitter -> emitter.onNext(result))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(diffResult -> diffResult.dispatchUpdatesTo(this));*/

        data.addAll(repoList);
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return this.data;
    }

    public void clearItems() {
        data.clear();
    }


    public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        protected abstract void onBind(T object);

        protected abstract void viewDetachedFromWindow();
    }


    @Override
    public void onViewDetachedFromWindow(@NonNull K holder) {
        holder.viewDetachedFromWindow();
        super.onViewDetachedFromWindow(holder);
    }

    protected abstract K mOnCreateViewHolder(ViewGroup parent, int viewType);


    public void setLoading(boolean loading) {
        this.loading = loading;
    }


    public void addMoreData(List<T> job_info, boolean noDataFlag) {

        if (!noDataFlag) {
            if (removeData()) {
                int start = getItemCount();
                data.addAll(job_info);
                notifyItemRangeInserted(start, getItemCount());
            }
        } else
            removeData();
    }

    /**
     * adding null item to show paging progress loader
     */
    public void setProgressItem() {
        int start = getItemCount();
        this.data.add(null);
        notifyItemRangeInserted(start, getItemCount());
    }


    /**
     * on response received
     * removing null item to hide paging progress loader
     */
    public boolean removeData() {
        if (data.get(data.size() - 1) == null) {
            data.remove(data.size() - 1);
            notifyItemRemoved(data.size());

            return true;
        }

        return false;
    }

    // Paging Progress ViewHolder
    private class PagingProgressViewHolder extends BaseRecyclerViewAdapter<T, K>.BaseViewHolder {

        private PagingProgressLoaderBinding mBinding;

        public PagingProgressViewHolder(PagingProgressLoaderBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        @Override
        protected void onBind(T object) {
            PagingProgressViewModel pagingProgressViewModel = new PagingProgressViewModel();
            // make progress visible and invisible
            pagingProgressViewModel.setIsVisible(loading);

            if (data != null && data.size() > 0 && data.get(data.size() - 1) != null)
                pagingProgressViewModel.setIsVisible(false);

            mBinding.setViewModel(pagingProgressViewModel);
        }

        @Override
        protected void viewDetachedFromWindow() {

        }
    }


    // Paging Progress ViewHolder
    private class EmptyFeedViewHolder extends BaseRecyclerViewAdapter<T, K>.BaseViewHolder implements RetryNavigator {

        private EmptyFeedRowLayoutBinding mBinding;

        public EmptyFeedViewHolder(EmptyFeedRowLayoutBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        @Override
        protected void onBind(T object) {
            emptyFeedViewModel = new EmptyFeedViewModel(this);

            if ((data != null && data.size() != 0))
                emptyFeedViewModel.setIsVisible(false);

            mBinding.setViewModel(emptyFeedViewModel);
        }

        @Override
        protected void viewDetachedFromWindow() {

        }

        @Override
        public void onRetryClick() {
            mListener.onRetryClick();
        }
    }

    public void setRetryVisibility(boolean visibility) {
        if (emptyFeedViewModel != null)
            emptyFeedViewModel.setIsVisible(visibility);
    }


    public static class MyDiffCallback<T> extends DiffUtil.Callback {

        List<T> oldList;
        List<T> newList;

        public MyDiffCallback(List<T> newList, List<T> oldList) {
            this.newList = newList;
            this.oldList = oldList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            //you can return particular field for changed item.
            return super.getChangePayload(oldItemPosition, newItemPosition);
        }
    }


}
