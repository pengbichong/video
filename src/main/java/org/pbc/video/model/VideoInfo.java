package org.pbc.video.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author pbc
 * @since 2018-04-17
 */
public class VideoInfo extends Model<VideoInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "v_id", type = IdType.AUTO)
    private Integer vId;
    private String imgPath;
    private Integer vTime;
    private Integer authorId;
    private String videoPath;
    private Date uploadDate;
    private Date reviewDate;
    private String state;
    private Integer tId;
    private String title;
    private String authorName;
    private String detail;
    private String tag;
    @TableField("clickTime")
    private Integer clickTime;


    public Integer getvId() {
        return vId;
    }

    public void setvId(Integer vId) {
        this.vId = vId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getvTime() {
        return vTime;
    }

    public void setvTime(Integer vTime) {
        this.vTime = vTime;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer gettId() {
        return tId;
    }

    public void settId(Integer tId) {
        this.tId = tId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getClickTime() {
        return clickTime;
    }

    public void setClickTime(Integer clickTime) {
        this.clickTime = clickTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.vId;
    }

    @Override
    public String toString() {
        return "VideoInfo{" +
        ", vId=" + vId +
        ", imgPath=" + imgPath +
        ", vTime=" + vTime +
        ", authorId=" + authorId +
        ", videoPath=" + videoPath +
        ", uploadDate=" + uploadDate +
        ", reviewDate=" + reviewDate +
        ", state=" + state +
        ", tId=" + tId +
        ", title=" + title +
        ", authorName=" + authorName +
        ", detail=" + detail +
        ", tag=" + tag +
        ", clickTime=" + clickTime +
        "}";
    }
}
