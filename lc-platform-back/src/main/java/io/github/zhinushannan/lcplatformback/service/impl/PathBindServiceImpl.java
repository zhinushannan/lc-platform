package io.github.zhinushannan.lcplatformback.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.zhinushannan.lcplatformback.dto.req.PathBindReq;
import io.github.zhinushannan.lcplatformback.entity.PathBind;
import io.github.zhinushannan.lcplatformback.exception.PathBindException;
import io.github.zhinushannan.lcplatformback.mapper.PathBindMapper;
import io.github.zhinushannan.lcplatformback.service.PathBindService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PathBindServiceImpl extends ServiceImpl<PathBindMapper, PathBind> implements PathBindService {
    @Override
    public PathBind getByIdWithAssertNull(Long id) {
        return Optional.of(getById(id)).orElseThrow(() -> PathBindException.NOT_FOUNT);
    }

}
