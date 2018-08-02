package org.pbc.video.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xiaoleilu.hutool.json.JSONObject;
import org.pbc.video.dao.CollectMapper;
import org.pbc.video.dao.VideoInfoMapper;
import org.pbc.video.model.Collect;
import org.pbc.video.model.VideoInfo;
import org.pbc.video.service.ICollectService;
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
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements ICollectService {
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private VideoInfoMapper videoInfoMapper;

    @Override
    public boolean saveCollect(int vid, int uid) {//保存收藏
        Collect entity = new Collect();
        entity.setUid(uid);
        entity.setVid(vid);
        int flag = collectMapper.selectList(
                new EntityWrapper<Collect>().eq("uid",uid).eq("vid",vid)
        ).size();
        if (flag>0){
            return false;
        }else {
            entity.setDate(new Date());
            collectMapper.insert(entity);
            return true;
        }
    }
    @Override
    public JSONObject queryMyCollect(int uid,int page,int size) {//分页查询收藏列表
       List<VideoInfo> list_videoInfo = new ArrayList<VideoInfo>();
        List<Collect> list_collect = collectMapper.selectPage(
                new Page<Collect>(page,size),
                new EntityWrapper<Collect>().eq("uid",uid).orderBy("date",false)
        );
        int count = collectMapper.selectList(
                new EntityWrapper<Collect>().eq("uid",uid).orderBy("date",false)
        ).size();
        for (int i = 0 ;i<list_collect.size();i++) {
            VideoInfo entity = new VideoInfo();
            entity.setvId(list_collect.get(i).getVid());
            VideoInfo videoInfo = videoInfoMapper.selectOne(entity);
            System.out.println(videoInfo);
            list_videoInfo.add(videoInfo);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.append("count",count);
        jsonObject.append("list",list_videoInfo);
        return jsonObject;
    }
    @Override
    public boolean deleCollect(int vid, int uid) {//删除收藏
        int res = collectMapper.delete(
                new EntityWrapper<Collect>().eq("uid",uid).eq("vid",vid)
        );
        if (res>0){
            return true;
        }else {
            return  false;
        }
    }

}
