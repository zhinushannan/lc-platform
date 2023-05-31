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
             @Param("values") List<String> values);

    int update(@Param("physicsTableName") String physicsTableName,
               @Param("id") Long id,
               @Param("values") Map<String, Objects> values);

    int delete(@Param("physicsTableName") String physicsTableName,
               @Param("ids") List<Long> ids);

}
