package org.pbc.video.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author pbc
 * @since 2018-04-13
 */
public class Follow extends Model<Follow> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "fid", type = IdType.AUTO)
    private Integer fid;
    private Integer authorid;
    private Integer uid;
    private Date date;


    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getAuthorid() {
        return authorid;
    }

    public void setAuthorid(Integer authorid) {
        this.authorid = authorid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    protected Serializable pkVal() {
        return this.fid;
    }

    @Override
    public String toString() {
        return "Follow{" +
        ", fid=" + fid +
        ", authorid=" + authorid +
        ", uid=" + uid +
        ", date=" + date +
        "}";
    }
}
