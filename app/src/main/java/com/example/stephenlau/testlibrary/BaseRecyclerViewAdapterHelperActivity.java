package com.example.stephenlau.testlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class BaseRecyclerViewAdapterHelperActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_recycler_view_adapter_helper);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<DummyItem> dataSet = new ArrayList<>(20);
        dataSet.add(new DummyItem("a"));
        dataSet.add(new DummyItem("b"));
        dataSet.add(new DummyItem("c"));
        mRecyclerView.setAdapter(new QuickAdapter(dataSet));

        mRecyclerView.setAdapter(new MyItemRecyclerViewAdapter(dataSet,null));



    }

    public class QuickAdapter extends BaseQuickAdapter<DummyItem, BaseViewHolder> {
        public QuickAdapter(List<DummyItem> items) {
            super(R.layout.base_recycler_view_list_item, items);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, DummyItem item) {
            viewHolder.setImageResource(R.id.image_view, R.mipmap.headerandfooter_img1);
            viewHolder.setText(R.id.text_view, item.content);
        }
    }

    //继承ViewHolderAdapter
    public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.MyViewHolder> {

        private final List<DummyItem> mValues;//数据源
        private final OnInteractionListener mListener;

        public MyItemRecyclerViewAdapter(List<DummyItem> items, OnInteractionListener listener) {
            mValues = items;
            mListener = listener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.base_recycler_view_list_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mImageView.setImageResource(R.mipmap.headerandfooter_img1);
            holder.mTextView.setText(mValues.get(position).content);

            holder.mView.setOnClickListener(v -> {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);//为了回调回传Item而已
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        /**
         * 继承ViewHolder
         */
        public class MyViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView mImageView;
            public final AppCompatTextView mTextView;
            public DummyItem mItem;//为了回调回传Item而已

            public MyViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = view.findViewById(R.id.image_view);
                mTextView = view.findViewById(R.id.text_view);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText() + "'";
            }
        }

        /**
         * Callback
         */
        private class OnInteractionListener {
            public void onListFragmentInteraction(DummyItem mItem) {
            }
        }

    }


    private class DummyItem {
        public String content;

        public DummyItem(String content) {
            this.content = content;
        }
    }
}
