package org.pbc.video.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.pbc.video.dao.UserInfoMapper;
import org.pbc.video.model.User;
import org.pbc.video.dao.UserMapper;
import org.pbc.video.model.UserInfo;
import org.pbc.video.service.IUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pbc
 * @since 2018-04-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    //用户注册
    @Override
    public String registr(String username, String password, String nickname, String email, String re_date) {

        List<User> list = userMapper.selectList(
                new EntityWrapper<User>().eq("username",username)
        );
        if (!list.isEmpty()){
            return "04";
        }else {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setReDate(new Date());
            user.setState("0");
            int res = userMapper.insert(user);
            if (res == 1) {
                UserInfo userInfo = new UserInfo();
                userInfo.setUserId(user.getUserId());
                userInfo.setEmail(email);
                userInfo.setNickName(nickname);
                int res2 = userInfoMapper.insert(userInfo);
                if (res2 == 1) {
                    return "00";
                } else {
                    return "04";
                }
            } else {
                return "04";
            }

        }
    }

    //用户登录
    @Override
    public User login(String username, String password) {
        System.out.println(username+"---"+password);
        User user = new User();
        //根据用户名查询用户
        List<User> list = userMapper.selectList(
                new EntityWrapper<User>().eq("username",username).eq("state","0")
        );
        if (!list.isEmpty()){
            if (list.get(0).getPassword().equals(password)){//判断密码是否正确
                String nickname = userInfoMapper.selectList(new EntityWrapper<UserInfo>()
                        .eq("user_id",list.get(0).getUserId())).get(0).getNickName();
                user.setUsername(nickname);
                user.setUserId(list.get(0).getUserId());
                return user;
            }else {
                return user;
            }
        }else {
            return user;
        }




    }
    //修改密码
    @Override
    public String modifyPw(String ps1, String ps2,String uid) {
        User userFind = new User();
        userFind.setUserId(Integer.parseInt(uid));
        User user = userMapper.selectOne(userFind);
        if (user!=null){
            if (user.getPassword().equals(ps1)){
                user.setPassword(ps2);
                userMapper.updateById(user);
                return "success";
            }else {
                return "pserror";
            }
        }else {
            return "nouser";
        }

    }
}
