package org.pbc.video.service;

import com.xiaoleilu.hutool.json.JSONObject;
import org.pbc.video.model.Collect;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pbc
 * @since 2018-04-13
 */
public interface ICollectService extends IService<Collect> {

    //保存收藏
    public  boolean saveCollect(int vid,int uid);


    //查询我的收藏

    public JSONObject queryMyCollect(int uid, int page, int size);


    public boolean deleCollect(int vid,int uid);

}
