package com.zsinda.fdp.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.zsinda.fdp.entity.*;
import com.zsinda.fdp.service.AgentService;
import com.zsinda.fdp.service.SysLogService;
import com.zsinda.fdp.service.SysUserRoleService;
import com.zsinda.fdp.service.SysUserService;
import com.zsinda.fdp.utils.R;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * @program: FDPlatform
 * @description: SysLogController
 * @author: Sinda
 * @create: 2020-02-01 00:32
 */
@RestController
@RequestMapping("/log")
@AllArgsConstructor
@Api(value = "log", tags = "日志管理模块")
public class SysLogController {

    private final SysLogService sysLogService;

    private final AgentService agentService;

    private final SysUserService sysUserService;

    private final SysUserRoleService sysUserRoleService;

    //    @Inner
    @PostMapping("save")
    public R save(@RequestBody SysLog sysLog) {
        return R.ok(sysLogService.save(sysLog));
    }

    @SneakyThrows
    @GetMapping("test")
    public R test() {
        List<Agent> userDeptIds = readTxtFile();
//        userDeptIds.forEach(agent -> {
//            SysUser sysUser = new SysUser();
//            String strs[] = getPasswordAndSalt("Bank@123");
//            sysUser.setSalt(strs[0]);
//            sysUser.setPassword(strs[1]);
//            sysUser.setAccount("bank" + agent.getId());
//            sysUser.setNickname(agent.getName());
//            sysUser.setDeptId(agent.getId());
//            sysUser.setCreateTime(new Date());
//            sysUser.setStatus(Short.valueOf("1"));
//            sysUserService.save(sysUser);
//            SysUserRole sysUserRole = new SysUserRole();
//            sysUserRole.setSysUserId(sysUser.getId());
//            sysUserRole.setSysRoleId("AGENT_ADMIN");
//            sysUserRoleService.save(sysUserRole);
//        });
        List<ExcelDto> excelDtos = Lists.newArrayList();

        userDeptIds.forEach(agent -> {
            ExcelDto excelDto = new ExcelDto();
            excelDto.setName(agent.getName());
            SysUser sysUser = sysUserService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getDeptId, agent.getId()));
            excelDto.setAccount(sysUser.getAccount());
            excelDto.setPassword("Bank@123");
            excelDtos.add(excelDto);
        });
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("",""),
                ExcelDto .class, excelDtos);
//        List<Agent> list1 = agentService.list();
        FileOutputStream fos = new FileOutputStream("/Users/cengxinda/Desktop/agent.xlsx");
        workbook.write(fos);
        fos.close();
        return R.ok();
    }

    public static String[] getPasswordAndSalt(String plainPassword) {
        String[] pwd = new String[2];
        // 得到8位盐
        byte[] salts = Digests.generateSalt(8);
        // 将8位byte数组装换为spring
        String saltStr = encodeHex(salts);
        pwd[0] = saltStr;
        // 原密码
        String password = String.valueOf(plainPassword);

        password = new Md5Hash(password, saltStr, 1024).toString();

        pwd[1] = password;

        return pwd;
    }

    public static String encodeHex(byte[] input) {
        return new String(Hex.encodeHex(input));
    }

    public static String getPassword(String plainPwd, String saltStr) {
        try {
            byte[] salts = Hex.decodeHex(saltStr.toCharArray());
            // 原密码
            String password = String.valueOf(plainPwd);
            // 对密码加盐进行1024次SHA1加密
            byte[] hashPassword = Digests.sha1(password.getBytes(), salts, 1024);
            // 将加密后的密码数组转换成字符串
            password = new String(Hex.encodeHex(hashPassword));
            return password;
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSaltStr() {
        byte[] salts = Digests.generateSalt(8);
        return new String(Hex.encodeHex(salts));
    }

    public List<Agent> readTxtFile() {
        List<Agent> agents = Lists.newArrayList();
        try {
            String encoding = "GBK";
            File file = new File("/Users/cengxinda/Desktop/机构号.txt");
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    String[] str = lineTxt.split(" ");
                    Agent agent = new Agent();
                    agent.setId(str[0]);
                    agent.setName(str[1]);
                    agent.setCreateTime(new Date());
                    agent.setIsDel(0);
                    agents.add(agent);
//                    System.out.println(str[0] + " " + str[1]);
                }
//                agentService.saveBatch(agents);
                read.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return agents;
    }

}
