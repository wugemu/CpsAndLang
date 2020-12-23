package com.luck.picture.lib.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luck.picture.lib.R;
import com.luck.picture.lib.anim.OptAnimationLoader;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;
import com.luck.picture.lib.tools.StringUtils;
import com.luck.picture.lib.tools.ToastManage;
import com.luck.picture.lib.tools.VoiceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * author：luck
 * project：PictureSelector
 * package：com.luck.picture.lib.adapter
 * email：893855882@qq.com
 * data：2016/12/30
 */
public class PictureImageGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int DURATION = 450;
    public static int min_live_duration;//上传视频的允许最小时长
    public static int max_live_duration;//上传视频的允许最大时长
    public static String extension;//
    private Context context;
    private boolean showCamera = true;
    private OnPhotoSelectChangedListener imageSelectChangedListener;
    private int maxSelectNum;
    private List<LocalMedia> images = new ArrayList<LocalMedia>();
    private List<LocalMedia> selectImages = new ArrayList<LocalMedia>();
    private boolean enablePreview;
    private int selectMode = PictureConfig.MULTIPLE;
    private boolean enablePreviewVideo = false;
    private boolean enablePreviewAudio = false;
    private boolean is_checked_num;
    private boolean enableVoice;
    private int overrideWidth, overrideHeight;
    private float sizeMultiplier;
    private Animation animation;
    private PictureSelectionConfig config;
    private int mimeType;
    private boolean zoomAnim;
    /**
     * 单选图片
     */
    private boolean isGo;

    public PictureImageGridAdapter(Context context, PictureSelectionConfig config) {
        this.context = context;
        this.config = config;
        this.selectMode = config.selectionMode;
        this.showCamera = config.isCamera;
        this.maxSelectNum = config.maxSelectNum;
        this.enablePreview = config.enablePreview;
        this.enablePreviewVideo = config.enPreviewVideo;
        this.enablePreviewAudio = config.enablePreviewAudio;
        this.is_checked_num = config.checkNumMode;
        this.overrideWidth = config.overrideWidth;
        this.overrideHeight = config.overrideHeight;
        this.enableVoice = config.openClickSound;
        this.sizeMultiplier = config.sizeMultiplier;
        this.mimeType = config.mimeType;
        this.zoomAnim = config.zoomAnim;
        animation = OptAnimationLoader.loadAnimation(context, R.anim.modal_in);
    }

    public void setShowCamera(boolean showCamera) {
        this.showCamera = showCamera;
    }

    public void bindImagesData(List<LocalMedia> images) {
        List<LocalMedia> newimages = new ArrayList<LocalMedia>();
        if (images != null) {
            for (LocalMedia media : images) {
                if (!new File(media.getPath()).exists()) {
                    continue;
                }
                newimages.add(media);
            }
        }
        this.images = newimages;
        notifyDataSetChanged();
    }

    public void bindSelectImages(List<LocalMedia> images) {
        // 这里重新构构造一个新集合，不然会产生已选集合一变，结果集合也会添加的问题
        List<LocalMedia> selection = new ArrayList<>();
        for (LocalMedia media : images) {
            selection.add(media);
        }
        this.selectImages = selection;
        subSelectPosition();
        if (imageSelectChangedListener != null) {
            imageSelectChangedListener.onChange(selectImages);
        }
    }

    public List<LocalMedia> getSelectedImages() {
        if (selectImages == null) {
            selectImages = new ArrayList<>();
        }
        return selectImages;
    }

    public List<LocalMedia> getImages() {
        if (images == null) {
            images = new ArrayList<>();
        }
        return images;
    }

    @Override
    public int getItemViewType(int position) {
        if (showCamera && position == 0) {
            return PictureConfig.TYPE_CAMERA;
        } else {
            return PictureConfig.TYPE_PICTURE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == PictureConfig.TYPE_CAMERA) {
            View view = LayoutInflater.from(context).inflate(R.layout.picture_item_camera, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.picture_image_grid_item, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == PictureConfig.TYPE_CAMERA) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.headerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imageSelectChangedListener != null) {
                        imageSelectChangedListener.onTakePhoto();
                    }
                }
            });
        } else {
            final ViewHolder contentHolder = (ViewHolder) holder;
            final LocalMedia image = images.get(showCamera ? position - 1 : position);
            image.position = contentHolder.getAdapterPosition();
            final String path = image.getPath();
            final String pictureType = image.getPictureType();
            if (is_checked_num) {
                notifyCheckChanged(contentHolder, image);
            }
            selectImage(contentHolder, isSelected(image), false);

            final int mediaMimeType = PictureMimeType.isPictureType(pictureType);
            boolean gif = PictureMimeType.isGif(pictureType);

            contentHolder.tv_isGif.setVisibility(gif ? View.VISIBLE : View.GONE);
            if (mimeType == PictureMimeType.ofAudio()) {
                contentHolder.tv_duration.setVisibility(View.VISIBLE);
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.picture_audio);
                StringUtils.modifyTextViewDrawable(contentHolder.tv_duration, drawable, 0);
            } else {
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.video_icon);
                StringUtils.modifyTextViewDrawable(contentHolder.tv_duration, drawable, 0);
                contentHolder.tv_duration.setVisibility(mediaMimeType == PictureConfig.TYPE_VIDEO
                        ? View.VISIBLE : View.GONE);
            }
            boolean eqLongImg = PictureMimeType.isLongImg(image);
            contentHolder.tv_long_chart.setVisibility(eqLongImg ? View.VISIBLE : View.GONE);
            long duration = image.getDuration();
            contentHolder.tv_duration.setText(DateUtils.timeParse(duration));
            if (mediaMimeType == PictureMimeType.ofAudio()) {
                contentHolder.iv_picture.setImageResource(R.drawable.audio_placeholder);
            } else if (mediaMimeType == PictureMimeType.ofVideo()) {
                File file = new File(path);
                Uri uri = Uri.fromFile(file);
                if (overrideWidth <= 0 && overrideHeight <= 0) {
                    Glide.with(context)
                            .load(uri)
//                            .diskCacheStrategy(DiskCacheStrategy.ALL)//视频展示图不能使用
                            .into(contentHolder.iv_picture);
                } else {
                    Glide.with(context)
                            .load(uri)
//                            .diskCacheStrategy(DiskCacheStrategy.ALL)//视频展示图不能使用
                            .into(contentHolder.iv_picture);
                }
            } else {
                if (overrideWidth <= 0 && overrideHeight <= 0) {
                    Glide.with(context)
                            .load(path)
                            .sizeMultiplier(sizeMultiplier)
                            .placeholder(R.drawable.image_placeholder)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(contentHolder.iv_picture);
                } else {
                    Glide.with(context)
                            .load(path)
                            .override(overrideWidth, overrideHeight)
                            .placeholder(R.drawable.image_placeholder)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(contentHolder.iv_picture);
                }
            }
            if (enablePreview || enablePreviewVideo || enablePreviewAudio) {
                contentHolder.ll_check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 如原图路径不存在或者路径存在但文件不存在
                        if (!new File(path).exists()) {
                            ToastManage.s(context, PictureMimeType.s(context, mediaMimeType));
                            return;
                        }
                        changeCheckboxState(contentHolder, image);
                    }
                });
            }
            contentHolder.contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 如原图路径不存在或者路径存在但文件不存在
                    if (!new File(path).exists()) {
                        ToastManage.s(context, PictureMimeType.s(context, mediaMimeType));
                        return;
                    }
                    int index = showCamera ? position - 1 : position;
                    boolean eqResult =
                            mediaMimeType == PictureConfig.TYPE_IMAGE && enablePreview
                                    || mediaMimeType == PictureConfig.TYPE_VIDEO && (enablePreviewVideo
                                    || selectMode == PictureConfig.SINGLE)
                                    || mediaMimeType == PictureConfig.TYPE_AUDIO && (enablePreviewAudio
                                    || selectMode == PictureConfig.SINGLE);
                    if (eqResult) {
                        imageSelectChangedListener.onPictureClick(image, index);
                    } else {
                        changeCheckboxState(contentHolder, image);
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return showCamera ? images.size() + 1 : images.size();
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        View headerView;
        TextView tv_title_camera;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerView = itemView;
            tv_title_camera = (TextView) itemView.findViewById(R.id.tv_title_camera);
            String title = mimeType == PictureMimeType.ofAudio() ?
                    context.getString(R.string.picture_tape)
                    : context.getString(R.string.picture_take_picture);
            tv_title_camera.setText(title);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_picture;
        TextView check;
        TextView tv_duration, tv_isGif, tv_long_chart;
        View contentView;
        LinearLayout ll_check;

        public ViewHolder(View itemView) {
            super(itemView);
            contentView = itemView;
            iv_picture = (ImageView) itemView.findViewById(R.id.iv_picture);
            check = (TextView) itemView.findViewById(R.id.check);
            ll_check = (LinearLayout) itemView.findViewById(R.id.ll_check);
            tv_duration = (TextView) itemView.findViewById(R.id.tv_duration);
            tv_isGif = (TextView) itemView.findViewById(R.id.tv_isGif);
            tv_long_chart = (TextView) itemView.findViewById(R.id.tv_long_chart);
        }
    }

    public boolean isSelected(LocalMedia image) {
        for (LocalMedia media : selectImages) {
            if (media.getPath().equals(image.getPath())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 选择按钮更新
     */
    private void notifyCheckChanged(ViewHolder viewHolder, LocalMedia imageBean) {
        viewHolder.check.setText("");
        for (LocalMedia media : selectImages) {
            if (media.getPath().equals(imageBean.getPath())) {
                imageBean.setNum(media.getNum());
                media.setPosition(imageBean.getPosition());
                viewHolder.check.setText(String.valueOf(imageBean.getNum()));
            }
        }
    }

    /**
     * 改变图片选中状态
     *
     * @param contentHolder
     * @param image
     */

    private void changeCheckboxState(ViewHolder contentHolder, LocalMedia image) {
        boolean isChecked = contentHolder.check.isSelected();

        if (!isChecked) {//选中
            String  pictureType = image.getPictureType();
            if (selectImages.size() > 0) {//不是第一个
                boolean toEqual = PictureMimeType.mimeToEqual(selectImages.get(0).getPictureType(), image.getPictureType());
                if (isVideo() && selectImages.size() == 2) {//已经选择视频和一个封面
                    ToastManage.s(context, context.getString(R.string.picture_rule2));
                    return;
                }
                if (!toEqual && !isVideo()) {//已经选择一个图片又想选择视频
                    ToastManage.s(context, context.getString(R.string.picture_rule));
                    return;
                }
            }

            if (!"".equals(pictureType)) {
                boolean eqImg = pictureType.startsWith(PictureConfig.IMAGE);
                if (eqImg) {
                    //选择的图片
                    if (!isVideo()&&selectImages.size() >= maxSelectNum) {
                        String str = context.getString(R.string.picture_message_max_num, maxSelectNum);
                        ToastManage.s(context, str);
                        return;
                    }
                } else {
                    if (!"".equals(extension)){
                        if (extension.contains("|")){
                            String[] arr=extension.split("\\|");
                            boolean flag=false;
                            for (int i=0;i<arr.length;i++){
                                if (isOkType(arr[i],image)){
                                    flag=true;
                                    break;
                                }
                            }
                            if (!flag){
                                ToastManage.s(context, "暂只支持"+extension.replace("|","、")+"格式的视频");
                                return;
                            }
                        }else{
                            if (!isOkType(extension,image)){
                                ToastManage.s(context, "暂只支持"+extension+"格式的视频");
                                return;
                            }
                        }
                    }
//                    if (ifOnlyMp4&&!image.getPath().toLowerCase().endsWith(".mp4")){
//                        ToastManage.s(context, "暂时只支持上传mp4格式的视频");
//                        return;
//                    }

                    //选中的视频
                    if (selectImages.size() >= 1) {
                        String str = context.getString(R.string.picture_message_video_max_num, 1);
                        ToastManage.s(context, str);
                        return;
                    }

                    if (max_live_duration > 0) {
                        if (image.getDuration() / 1000 > max_live_duration) {
                            ToastManage.s(context, "视频时长必须小于" + max_live_duration + "秒");
                            return;
                        }
                        if (image.getDuration() / 1000 < min_live_duration) {
                            ToastManage.s(context, "视频时长必须大于" + min_live_duration + "秒");
                            return;
                        }
                    }
//                    else {
//                        if (image.getDuration() / 1000 > 10) {
//                            //视频大于十秒
//                            ToastManage.s(context, "视频时长限十秒");
//                            return;
//                        }
//                    }
                }
            }

            // 如果是单选，则清空已选中的并刷新列表(作单一选择)
            if (selectMode == PictureConfig.SINGLE) {
                singleRadioMediaImage();
            }
            selectImages.add(image);
            image.setNum(selectImages.size());
            VoiceUtils.playVoice(context, enableVoice);
            zoom(contentHolder.iv_picture);

        } else {//取消
            for (LocalMedia media : selectImages) {
                if (media.getPath().equals(image.getPath())) {
                    selectImages.remove(media);
                    subSelectPosition();
                    disZoom(contentHolder.iv_picture);
                    break;
                }
            }


        }
        //通知点击项发生了改变
        notifyItemChanged(contentHolder.getAdapterPosition());
        selectImage(contentHolder, !isChecked, true);
        if (imageSelectChangedListener != null) {
            imageSelectChangedListener.onChange(selectImages);
        }
    }

    public boolean isOkType(String targetType,LocalMedia image){
        if ("mp4".equals(targetType)||"mov".equals(targetType)){
            return isOkMMType(targetType,image);
        }else if (image.getPath().toLowerCase().endsWith(targetType)){//后缀名一致
            return true;
        }
        return false;
    }

    public boolean isOkMMType(String targetType,LocalMedia image){//既要判断后缀，也要判断媒体类型
        if (image.getPath().toLowerCase().endsWith(targetType)){
            if (targetType.equals("mp4")){
                if ("video/mp4".equals(image.getPictureType())){
                    return true;
                }
            }
            if (targetType.equals("mov")){
                if ("video/quicktime".equals(image.getPictureType())){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean isVideo() {
        if (selectImages.size()>0&&PictureMimeType.isPictureType(selectImages.get(0).getPictureType()) == PictureConfig.TYPE_VIDEO) {
            return true;
        }
        return false;
    }

    /**
     * 单选模式
     */
    private void singleRadioMediaImage() {
        if (selectImages != null
                && selectImages.size() > 0) {
            isGo = true;
            LocalMedia media = selectImages.get(0);
            notifyItemChanged(config.isCamera ? media.position :
                    isGo ? media.position : media.position > 0 ? media.position - 1 : 0);
            selectImages.clear();
        }
    }

    /**
     * 更新选择的顺序
     */
    private void subSelectPosition() {
        if (is_checked_num) {
            int size = selectImages.size();
            for (int index = 0, length = size; index < length; index++) {
                LocalMedia media = selectImages.get(index);
                media.setNum(index + 1);
                notifyItemChanged(media.position);
            }
        }
    }

    /**
     * 选中的图片并执行动画
     *
     * @param holder
     * @param isChecked
     * @param isAnim
     */
    public void selectImage(ViewHolder holder, boolean isChecked, boolean isAnim) {
        holder.check.setSelected(isChecked);
        if (isChecked) {
            if (isAnim) {
                if (animation != null) {
                    holder.check.startAnimation(animation);
                }
            }
            holder.iv_picture.setColorFilter(ContextCompat.getColor
                    (context, R.color.image_overlay_true), PorterDuff.Mode.SRC_ATOP);
        } else {
            holder.iv_picture.setColorFilter(ContextCompat.getColor
                    (context, R.color.image_overlay_false), PorterDuff.Mode.SRC_ATOP);
        }
    }

    public interface OnPhotoSelectChangedListener {
        /**
         * 拍照回调
         */
        void onTakePhoto();

        /**
         * 已选Media回调
         *
         * @param selectImages
         */
        void onChange(List<LocalMedia> selectImages);

        /**
         * 图片预览回调
         *
         * @param media
         * @param position
         */
        void onPictureClick(LocalMedia media, int position);
    }

    public void setOnPhotoSelectChangedListener(OnPhotoSelectChangedListener
                                                        imageSelectChangedListener) {
        this.imageSelectChangedListener = imageSelectChangedListener;
    }

    private void zoom(ImageView iv_img) {
        if (zoomAnim) {
            AnimatorSet set = new AnimatorSet();
            set.playTogether(
                    ObjectAnimator.ofFloat(iv_img, "scaleX", 1f, 1.12f),
                    ObjectAnimator.ofFloat(iv_img, "scaleY", 1f, 1.12f)
            );
            set.setDuration(DURATION);
            set.start();
        }
    }

    private void disZoom(ImageView iv_img) {
        if (zoomAnim) {
            AnimatorSet set = new AnimatorSet();
            set.playTogether(
                    ObjectAnimator.ofFloat(iv_img, "scaleX", 1.12f, 1f),
                    ObjectAnimator.ofFloat(iv_img, "scaleY", 1.12f, 1f)
            );
            set.setDuration(DURATION);
            set.start();
        }
    }
}
