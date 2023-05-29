package io.github.zhinushannan.lcplatformback.service;

import io.github.zhinushannan.lcplatformback.entity.FieldMetaInfo;
import io.github.zhinushannan.lcplatformback.entity.TableMetaInfo;

public interface CreateBusinessService {

    Boolean createBusinessService(TableMetaInfo tableMetaInfo, FieldMetaInfo fieldMetaInfo);

}
