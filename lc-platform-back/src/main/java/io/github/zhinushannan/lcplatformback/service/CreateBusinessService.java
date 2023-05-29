package io.github.zhinushannan.lcplatformback.service;

import io.github.zhinushannan.lcplatformback.entity.FieldMetaInfo;
import io.github.zhinushannan.lcplatformback.entity.TableMetaInfo;

import java.util.List;

public interface CreateBusinessService {

    Boolean createBusinessService(TableMetaInfo tableMetaInfo, List<FieldMetaInfo> fieldMetaInfos);

}
