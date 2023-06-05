package io.github.zhinushannan.lcplatformback.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SystemInitializationMapper {
    List<String> selectAllTableNames();

    void dropTables(@Param("tableName") String tableName);
    void createTableMetaInfo();
    void createFieldMetaInfo();

}
