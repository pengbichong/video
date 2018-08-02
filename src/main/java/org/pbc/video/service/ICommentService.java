package org.pbc.video.service;

import com.xiaoleilu.hutool.json.JSONObject;
import org.pbc.video.model.Comment;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pbc
 * @since 2018-04-11
 */
public interface ICommentService extends IService<Comment> {

    //保存评论
    public String save(int userid,String comment,String vid);

    //查询分页评论
    public JSONObject getComment(int vid,int page,int size);
}
