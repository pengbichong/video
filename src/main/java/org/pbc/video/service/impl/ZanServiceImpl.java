package org.pbc.video.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.pbc.video.model.Zan;
import org.pbc.video.dao.ZanMapper;
import org.pbc.video.service.IZanService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pbc
 * @since 2018-04-13
 */
@Service
public class ZanServiceImpl extends ServiceImpl<ZanMapper, Zan> implements IZanService {
    @Autowired
    private ZanMapper zanMapper;

    @Override
    public int quryZanNum(int vid) {//查询被赞数
       int num = zanMapper.selectList(
                new EntityWrapper<Zan>().eq("vid",vid)
        ).size();
        return num;
    }
    @Override
    public boolean quryZanYes(int vid, int userid) {//查询用户是否已经赞过
        int num = zanMapper.selectList(
                new EntityWrapper<Zan>().eq("vid",vid).eq("user_id",userid)
        ).size();
        if (num>0){
            return true;
        }else {
            return false;
        }
    }
    @Override
    public boolean save(int vid, int userid) {//保存点赞信息
        Zan entity = new Zan();
        entity.setUserId(userid);
        entity.setVid(vid);
        int flag = zanMapper.selectList(
                new EntityWrapper<Zan>().eq("user_id",userid).eq("vid",vid)
        ).size();
        if (flag>0){
            return false;
        }else {
            entity.setDate(new Date());
            zanMapper.insert(entity);
            return true;
        }

    }


}
