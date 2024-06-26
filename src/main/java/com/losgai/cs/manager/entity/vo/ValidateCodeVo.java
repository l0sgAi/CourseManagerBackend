package com.losgai.cs.manager.entity.vo;

import lombok.Data;

@Data
public class ValidateCodeVo { //验证码响应结果

    private String codeKey ;        // 验证码的key

    private String codeValue ;      // 图片验证码对应的字符串数据

}