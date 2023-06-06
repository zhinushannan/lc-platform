package io.github.zhinushannan.lcplatformback.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.zhinushannan.lcplatformback.dto.req.PathBindReq;
import io.github.zhinushannan.lcplatformback.entity.PathBind;

public interface PathBindService extends IService<PathBind> {
    PathBind getByIdWithAssertNull(Long id);

}
