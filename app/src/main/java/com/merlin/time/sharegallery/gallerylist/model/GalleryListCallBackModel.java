package com.merlin.time.sharegallery.gallerylist.model;

import java.io.Serializable;

/**
 * @author merlin720
 * @date 2019/2/23
 * @mail zy44638@gmail.com
 * @description
 */
public class GalleryListCallBackModel implements Serializable {
    public String accessid;
    public String host;
    public String policy;
    public String signature;
    public String expire;
    public String callback;
    public String dir;
    public TokenInfo token_info;
    public String callback_host;

}
