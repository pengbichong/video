package org.pbc.video.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.pbc.video.dao.VideoTypeMapper;
import org.pbc.video.model.VideoType;
import org.pbc.video.service.IVideoTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pbc
 * @since 2018-04-07
 */
@Service
public class VideoTypeServiceImpl extends ServiceImpl<VideoTypeMapper, VideoType> implements IVideoTypeService {
    @Autowired
    private  VideoTypeMapper videoTypeMapper;

    @Override
    public List<VideoType> queryAllVideoType(){
        List<VideoType> list = videoTypeMapper.selectList(
                new EntityWrapper<VideoType>()
        );

        return  list;
    }
}
