package io.github.zhinushannan.lcplatformback.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mapper
public interface CrudMapper {

    int save(@Param("physicsTableName") String physicsTableName,
             @Param("fields") List<String> fields,
             @Param("values") List<Object> values);

    int update(@Param("physicsTableName") String physicsTableName,
               @Param("id") Long id,
               @Param("values") Map<String, Objects> values);

    int delete(@Param("physicsTableName") String physicsTableName,
               @Param("ids") List<Long> ids);

    List<Map<String, Object>> list(@Param("physicsTableName") String physicsTableName,
                      @Param("phyLogic") Map<String, String> phyLogic,
                      @Param("cursor") long cursor,
                      @Param("size") long size);

    long count(@Param("physicsTableName") String physicsTableName);

}
