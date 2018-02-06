package com.duke.properties;

/**
 * Created duke on 2018/1/4
 */
public class ImageCodeProperties extends SmsCodeProperties {

    /**
     * 图片验证码图片的宽度
     */
    private Integer width = 67;

    /**
     * 图片验证码图片的高度
     */
    private Integer height = 23;

    ImageCodeProperties() {
        setLength(4);
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
