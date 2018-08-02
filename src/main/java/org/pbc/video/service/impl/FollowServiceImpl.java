package org.pbc.video.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xiaoleilu.hutool.json.JSONObject;
import org.pbc.video.dao.UserInfoMapper;
import org.pbc.video.dao.VideoInfoMapper;
import org.pbc.video.model.Follow;
import org.pbc.video.dao.FollowMapper;
import org.pbc.video.model.UserInfo;
import org.pbc.video.model.VideoInfo;
import org.pbc.video.service.IFollowService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pbc
 * @since 2018-04-13
 */
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements IFollowService {
    @Autowired
    private FollowMapper followMapper;
    @Autowired
    private VideoInfoMapper videoInfoMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public boolean save(int vid, int userid) {

        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setvId(vid);
        VideoInfo res = videoInfoMapper.selectOne(videoInfo);



        Follow entity = new Follow();
        entity.setUid(userid);
        entity.setAuthorid(res.getAuthorId());
        entity.setDate(new Date());


        int flag = followMapper.selectList(
                new EntityWrapper<Follow>().eq("uid",userid).eq("authorid",res.getAuthorId())
        ).size();

        if (flag>0){

            followMapper.delete(
                    new EntityWrapper<Follow>().eq("uid",userid).eq("authorid",res.getAuthorId())
                    );
            return false;
        }else {

            followMapper.insert(entity);
            return true;
        }

    }


    @Override
    public boolean queryFollowYes(int vid, int userid) {

        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setvId(vid);
        VideoInfo res = videoInfoMapper.selectOne(videoInfo);


        int flag = followMapper.selectList(
                new EntityWrapper<Follow>().eq("uid",userid).eq("authorid",res.getAuthorId())
        ).size();

        if (flag>0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public JSONObject queryMyFollow(int uid, int page, int size) {
        List<UserInfo> list_userInfo = new ArrayList<UserInfo>();

        List<Follow> list_follow = followMapper.selectPage(
                new Page<Follow>(page,size),
                new EntityWrapper<Follow>().eq("uid",uid).orderBy("date",false)
        );
        int count = followMapper.selectList(
                new EntityWrapper<Follow>().eq("uid",uid).orderBy("date",false)
        ).size();
        for (int i = 0 ;i<list_follow.size();i++) {
            UserInfo entity = new UserInfo();
            entity.setUserId(list_follow.get(i).getAuthorid());
            UserInfo userInfo = userInfoMapper.selectOne(entity);
            System.out.println(userInfo);
            list_userInfo.add(userInfo);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.append("count",count);
        jsonObject.append("list",list_userInfo);
        return jsonObject;


    }

    @Override
    public boolean daleFollow(int authorId, int uid) {


        int res = followMapper.delete(
                new EntityWrapper<Follow>().eq("authorid",authorId).eq("uid",uid)
        );

        if (res>0){
            return true;
        }else {
            return false;
        }


    }

}
