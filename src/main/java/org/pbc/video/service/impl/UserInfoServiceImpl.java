package org.pbc.video.service.impl;

import org.pbc.video.dao.VideoInfoMapper;
import org.pbc.video.model.UserInfo;
import org.pbc.video.dao.UserInfoMapper;
import org.pbc.video.model.VideoInfo;
import org.pbc.video.service.IUserInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pbc
 * @since 2018-04-07
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {
    @Autowired
    private  UserInfoMapper userInfoMapper;
    @Autowired
    private VideoInfoMapper videoInfoMapper;

    //上传头像
    @Override
    public String upHead(int userid, String data) {
        String imgData = data.substring(data.indexOf("base64")+7);
        System.out.println(imgData);
        String imgType = data.substring(data.indexOf("data:image/")+11,data.indexOf(";base64"));
        System.out.println(imgType);
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解码
            byte[] b = decoder.decodeBuffer(imgData);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            String date = String.valueOf(new Date().getTime());
            String path = "\\headImg\\"+userid+"_head"+date+"."+imgType;
            String contentpath = this.getClass().getResource("/").getPath();
                    contentpath = contentpath.substring(1,contentpath.indexOf("/WEB-INF/classes/"));
            System.out.println(path);
            System.out.println(contentpath);
            OutputStream out = new FileOutputStream(contentpath+"/web"+path);
            out.write(b);
            out.flush();
            out.close();
            //保存头像路径
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userid);
            UserInfo userInfo1 = userInfoMapper.selectOne(userInfo);
            userInfo1.setHeadPath(path);
            userInfoMapper.updateById(userInfo1);
            return path;
        } catch (Exception e) {
            return "error";
        }
    }

    @Override
    public UserInfo queryUserInfo(int userid) {
        UserInfo entity = new UserInfo();
        entity.setUserId(userid);
        UserInfo userInfo = userInfoMapper.selectOne(entity);
        if (userInfo.getHeadPath()==null||"".equals(userInfo.getHeadPath())){
            userInfo.setHeadPath("/headImg/hu.jpg");
        }



        return userInfo;
    }


    //修改用户信息
    @Override
    public String modifyUserInfo(int userid, String email, String telephone, String nickname, String sex) {
        UserInfo entity = new UserInfo();
        entity.setUserId(userid);
        UserInfo userInfo = userInfoMapper.selectOne(entity);

        userInfo.setNickName(nickname);
        userInfo.setEmail(email);
        userInfo.setTelephone(telephone);
        userInfo.setSex(sex);
        userInfoMapper.updateById(userInfo);

        return "success";
    }

    @Override
    public UserInfo queryAuthor(int vid) {
        VideoInfo entity = new VideoInfo();
        entity.setvId(vid);
        VideoInfo videoInfo = videoInfoMapper.selectOne(entity);

        int authorid= videoInfo.getAuthorId();

        UserInfo entity2 = new UserInfo();
        entity2.setUserId(authorid);
        UserInfo userInfo = userInfoMapper.selectOne(entity2);

        if (userInfo.getHeadPath()==null||"".equals(userInfo.getHeadPath())){
            userInfo.setHeadPath("/headImg/hu.jpg");
        }

        return userInfo;

    }


}
