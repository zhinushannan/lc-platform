package io.github.zhinushannan.lcplatformback.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * restful 统一返回值
 *
 * @author zhinushannan
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("统一返回值[ResultBean<T>]")
public class ResultBean<T> {

    @ApiModelProperty("业务状态码")
    private Integer code;

    @ApiModelProperty("响应消息")
    private String message;

    @ApiModelProperty("返回值实际数据(可能为null)")
    private T data;

    public static <T> ResultBean<T> create(Integer code, String message, T data) {
        return new ResultBean<>(code, message, data);
    }

    public static <T> ResultBean<T> create(Integer code, String message) {
        return new ResultBean<>(code, message, null);
    }

    public static <T> ResultBean<T> success() {
        return create(200, null, null);
    }

    public static <T> ResultBean<T> success(String message, T data) {
        return create(200, message, data);
    }

    public static <T> ResultBean<T> success(T data) {
        return success("成功！", data);
    }

    public static <T> ResultBean<T> success(String message) {
        return success(message, null);
    }

    public static <T> ResultBean<T> notFound(String message, T data) {
        return create(404, message, data);
    }

    public static <T> ResultBean<T> notFound(T data) {
        return notFound("不存在！", data);
    }

    public static <T> ResultBean<T> notFound(String message) {
        return notFound(message, null);
    }

    public static <T> ResultBean<T> error(String message, T data) {
        return create(500, message, data);
    }

    public static <T> ResultBean<T> error(T data) {
        return error("错误！", data);
    }

    public static <T> ResultBean<T> error(String message) {
        return error(message, null);
    }

}
