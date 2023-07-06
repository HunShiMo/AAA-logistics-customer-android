package xyz.rayisbest.userlogisticssystem.logic.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;


import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

import xyz.rayisbest.userlogisticssystem.logic.bean.ImageData;
import xyz.rayisbest.userlogisticssystem.logic.viewholder.ImageHolder;

/**
 * 自定义布局，图片
 */
public class ImageAdapter extends BannerAdapter<ImageData, ImageHolder> {

    public ImageAdapter(List<ImageData> mDatas) {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
        super(mDatas);
    }

    //更新数据
    public void updateData(List<ImageData> data) {
        mDatas.clear();
        mDatas.addAll(data);
        notifyDataSetChanged();
    }


    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new ImageHolder(imageView);
    }

    @Override
    public void onBindView(ImageHolder holder, ImageData data, int position, int size) {
        holder.imageView.setImageResource(data.getImageRes());
    }

}
