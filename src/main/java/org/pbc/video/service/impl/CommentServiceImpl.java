package org.pbc.video.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xiaoleilu.hutool.json.JSONObject;
import org.pbc.video.dao.UserInfoMapper;
import org.pbc.video.model.Comment;
import org.pbc.video.dao.CommentMapper;
import org.pbc.video.model.UserInfo;
import org.pbc.video.service.ICommentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pbc
 * @since 2018-04-11
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    //保存评论
    @Override
    public String save(int userid, String comment,String vid) {
        UserInfo entity = new UserInfo();
        entity.setUserId(userid);
        UserInfo userInfo = userInfoMapper.selectOne(entity);
        Comment comment1 = new Comment();
        comment1.setContent(comment);
        comment1.setDate(new Date());
        comment1.setFromUid(userid);
        comment1.setState("0");
        comment1.setNickname(userInfo.getNickName());
        comment1.setvId(Integer.parseInt(vid));
        int res = commentMapper.insert(comment1);
        if (res==1){
            return "success";
        }else {
            return "error";
        }
    }
    //分页查询评论
    @Override
    public JSONObject getComment(int vid, int page, int size) {
        List<Comment> List = commentMapper.selectPage(
                new Page<Comment>(page, size),
                new EntityWrapper<Comment>().eq("v_id", vid).eq("state","0")
                        .orderBy("date", false)
        );
        for (int i = 0; i <List.size() ; i++) {
           int userid = List.get(i).getFromUid();
           UserInfo entity = new UserInfo();
           entity.setUserId(userid);
           UserInfo userInfo = userInfoMapper.selectOne(entity);
           if(userInfo.getHeadPath()!=null&&!"".equals(userInfo.getHeadPath())){
               List.get(i).setState(userInfo.getHeadPath());
           }else {
               List.get(i).setState("/headImg/hu.jpg");
           }
        }
        int count = commentMapper.selectList(
                new EntityWrapper<Comment>().eq("v_id",vid).eq("state","0")
        ).size();
        JSONObject jsonObject= new JSONObject();
        jsonObject.append("count",count);
        jsonObject.append("list",List);
        return jsonObject;
    }
}
