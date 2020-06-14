package com.zsinda.fdp.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @program: FDPlatform
 * @description: ExcelDto
 * @author: Sinda
 * @create: 2020-06-12 14:16
 */
@Data
public class ExcelDto {

    @Excel(name = "代理商名字")
    private String name;

    @Excel(name = "登录账号")
    private String account;

    @Excel(name = "登录密码")
    private String password;

}
