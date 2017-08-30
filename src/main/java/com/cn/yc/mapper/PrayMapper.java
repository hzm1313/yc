package com.cn.yc.mapper;

import com.cn.yc.pojo.PrayDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by DT167 on 2017/6/19.
 */
@Mapper
public interface PrayMapper {
    public void savePray(PrayDO pray);

    public List<PrayDO> selectPray(PrayDO pray);
}
