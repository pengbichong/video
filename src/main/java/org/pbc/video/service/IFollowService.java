package org.pbc.video.service;

import com.xiaoleilu.hutool.json.JSONObject;
import org.pbc.video.model.Follow;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pbc
 * @since 2018-04-13
 */
public interface IFollowService extends IService<Follow> {
    public boolean save(int vid,int userid);//保存关注信息


    public boolean queryFollowYes(int vid,int userid);//查询是否关注

    //查询我的关注

    public JSONObject queryMyFollow(int uid, int page, int size);


    //取关
    public boolean daleFollow(int authorId,int uid);

}
