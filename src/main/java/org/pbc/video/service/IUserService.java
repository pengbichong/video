package org.pbc.video.service;

import org.pbc.video.model.User;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pbc
 * @since 2018-04-07
 */
public interface IUserService extends IService<User> {

    public String registr(String username,String password,String nickname,String email,String re_date);//用户注册

    public User login(String username, String password);//用户登录

    public String modifyPw(String ps1,String ps2,String userid);//修改密码
}
