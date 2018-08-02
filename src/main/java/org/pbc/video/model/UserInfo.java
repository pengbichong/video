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
 * @since 2018-04-07
 */
public class UserInfo extends Model<UserInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ui_id", type = IdType.AUTO)
    private Integer uiId;
    /**
     * 账号表ID
     */
    private Integer userId;
    /**
     * 头像
     */
    private String headPath;
    private String email;
    private String telephone;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 0男1女
     */
    private String sex;
    @TableField("lastLogin_date")
    private Date lastloginDate;


    public Integer getUiId() {
        return uiId;
    }

    public void setUiId(Integer uiId) {
        this.uiId = uiId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getLastloginDate() {
        return lastloginDate;
    }

    public void setLastloginDate(Date lastloginDate) {
        this.lastloginDate = lastloginDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.uiId;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
        ", uiId=" + uiId +
        ", userId=" + userId +
        ", headPath=" + headPath +
        ", email=" + email +
        ", telephone=" + telephone +
        ", nickName=" + nickName +
        ", sex=" + sex +
        ", lastloginDate=" + lastloginDate +
        "}";
    }
}
