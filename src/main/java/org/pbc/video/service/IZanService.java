package org.pbc.video.service;

import com.baomidou.mybatisplus.service.IService;
import org.pbc.video.model.Zan;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pbc
 * @since 2018-04-13
 */
public interface IZanService extends IService<Zan> {

    public int quryZanNum(int vid);//查询视频被赞次数

    public boolean quryZanYes(int vid,int userid);//查询是否已赞

    public boolean save(int vid,int userid);//保存点赞信息
}
