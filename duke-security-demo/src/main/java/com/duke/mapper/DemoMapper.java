package com.duke.mapper;

import com.duke.domain.Demo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created duke on 2017/12/30
 */
@Mapper
public interface DemoMapper {

    Demo selectOne(@Param("id") String id);

}
