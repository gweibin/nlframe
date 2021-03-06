/**
 * Copyright (C) 2017 Baidu Inc. All rights reserved.
 */
package com.nlinks.faceplatform.idl.face.platform;


import com.nlinks.faceplatform.idl.face.platform.model.FaceModel;

/**
 * 活体检测功能接口
 */
public interface ILiveness {
    FaceModel liveness(LivenessTypeEnum type, byte[] imageData, int imageWidth, int imageHeight);
    void reset();
}
