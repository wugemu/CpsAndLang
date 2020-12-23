package com.lxkj.dmhw.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.utils.Share;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 分享参数
 */
@Getter
@Setter
public class ShareParams implements Serializable {
    /**
     * 拼接信息，默认不拼接
     */
    private int splice = Share.SPLICE_NO;


    /**
     * 分享是来源于那个页面(防止重复任务)
     * 分享类型 0 邀请好友 1分享商品 5是分享页面的分享的商品 4是来自任务页面的分享好友  6 频道商品和主题商品   3 其他分享不算任务
     */
    private int shareTag =3;

    /**
     * 分享标题
     */
    private String title = Variable.shareTitle;
    /**
     * 分享内容
     */
    private String content = "";
    /**
     * 分享链接
     */
    private String url = "";
    /**
     * 分享缩略图
     */
    private String thumbData = "";
    /**
     * 分享信息
     */
    private ArrayList<ShareInfo> shareInfo = new ArrayList<>();
    /**
     * 分享图片
     */
    private ArrayList<String> image = new ArrayList<>();

    /**
     * 保存视频url
     */
    private String Vediourl = "";

    /**
     * 1-保存图片到文件夹images，0-其他就是保存的share
     */
    private int issave = 0;

    /**
     * 判断是否是PJW分享
     */
    private int isPJW = 0;
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(this.splice);
//        dest.writeString(this.title);
//        dest.writeString(this.content);
//        dest.writeString(this.url);
//        dest.writeString(this.thumbData);
//        dest.writeTypedList(this.shareInfo);
//        dest.writeStringList(this.image);
//        dest.writeString(this.Vediourl);
//        dest.writeInt(this.issave);
//        dest.writeInt(this.shareTag);
//        dest.writeInt(this.isPJW);
//    }
//
//    public ShareParams() {
//    }
//
//    protected ShareParams(Parcel in) {
//        this.splice = in.readInt();
//        this.title = in.readString();
//        this.content = in.readString();
//        this.url = in.readString();
//        this.Vediourl = in.readString();
//        this.thumbData = in.readString();
//        this.shareInfo = in.createTypedArrayList(ShareInfo.CREATOR);
//        this.image = in.createStringArrayList();
//        this.issave=in.readInt();
//        this.isPJW=in.readInt();
//        this.shareTag=in.readInt();
//    }
//
//    public static final Parcelable.Creator<ShareParams> CREATOR = new Parcelable.Creator<ShareParams>() {
//        @Override
//        public ShareParams createFromParcel(Parcel source) {
//            return new ShareParams(source);
//        }
//
//        @Override
//        public ShareParams[] newArray(int size) {
//            return new ShareParams[size];
//        }
//    };
}
