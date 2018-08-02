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
public class Zan extends Model<Zan> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "z_id", type = IdType.AUTO)
    private Integer zId;
    private Integer userId;
    private Date date;
    private Integer vid;


    public Integer getzId() {
        return zId;
    }

    public void setzId(Integer zId) {
        this.zId = zId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    @Override
    protected Serializable pkVal() {
        return this.zId;
    }

    @Override
    public String toString() {
        return "Zan{" +
        ", zId=" + zId +
        ", userId=" + userId +
        ", date=" + date +
        ", vid=" + vid +
        "}";
    }
}
