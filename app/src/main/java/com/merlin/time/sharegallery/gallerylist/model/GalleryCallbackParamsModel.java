package com.merlin.time.sharegallery.gallerylist.model;

import java.io.Serializable;

/**
 * @author merlin720
 * @date 2019/2/23
 * @mail zy44638@gmail.com
 * @description
 */
public class GalleryCallbackParamsModel implements Serializable {
    private String callbackUrl;
    private String callbackBody;
    private String callbackBodyType;
    private String token;

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getCallbackBody() {
        return callbackBody;
    }

    public void setCallbackBody(String callbackBody) {
        this.callbackBody = callbackBody;
    }

    public String getCallbackBodyType() {
        return callbackBodyType;
    }

    public void setCallbackBodyType(String callbackBodyType) {
        this.callbackBodyType = callbackBodyType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
