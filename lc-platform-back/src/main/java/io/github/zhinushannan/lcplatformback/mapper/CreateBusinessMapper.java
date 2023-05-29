package io.github.zhinushannan.lcplatformback.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CreateBusinessMapper {
    int createNewTable(@Param("tableName") String tableName,
                       @Param("fieldDDL") List<String> fieldDDL);
}
