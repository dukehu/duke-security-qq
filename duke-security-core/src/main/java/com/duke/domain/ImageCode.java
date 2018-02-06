package com.duke.domain;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * Created duke on 2018/1/3
 */
public class ImageCode extends ValidateCode {

    private BufferedImage image;

    public ImageCode() {
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code, expireTime);
        this.image = image;
    }

    public ImageCode(BufferedImage image, String code, Integer expireInSeconds) {
        super(code, expireInSeconds);
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
