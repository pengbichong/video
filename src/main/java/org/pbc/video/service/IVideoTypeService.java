package org.pbc.video.service;

import org.pbc.video.model.VideoType;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pbc
 * @since 2018-04-07
 */
public interface IVideoTypeService extends IService<VideoType> {

    public List<VideoType> queryAllVideoType();
}
