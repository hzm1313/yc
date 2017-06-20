package com.cn.yc.web.pray.mapper;

import com.cn.yc.bean.Pray;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by DT167 on 2017/6/19.
 */
@Mapper
public interface PrayMapper {
    public void savePray(Pray pray);

    public List<Pray> selectPray(Pray pray);
}
