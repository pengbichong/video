package org.pbc.video.service;

import com.xiaoleilu.hutool.json.JSONObject;
import org.pbc.video.model.UserInfo;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pbc
 * @since 2018-04-07
 */
public interface IUserInfoService extends IService<UserInfo> {
     /*
     *上传头像
      */
    public String upHead(int userid,String imgdata);

    /*
     *查询用户信息
     */
    public UserInfo queryUserInfo(int userid);




    /*
     *修改用户信息
     */
    public String modifyUserInfo(int userid,String email,String telephone,String nickname,String sex);

    /*
     *查询作者信息
     */
    public UserInfo queryAuthor(int vid);

}
