package io.github.zhinushannan.lcplatformback.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface LcQueryMapper {

    List<Map<String, Object>> select(@Param("sql") String sql);

    long count(@Param("sql") String sql);

}
