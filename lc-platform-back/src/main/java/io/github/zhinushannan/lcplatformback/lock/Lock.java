package io.github.zhinushannan.lcplatformback.lock;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Lock {

    private final LocalDateTime createTime;

    private final LocalDateTime expireTime;

    public Lock(Integer expireSeconds) {
        this.createTime = LocalDateTime.now();
        this.expireTime = this.createTime.plusSeconds(expireSeconds);
    }

}
