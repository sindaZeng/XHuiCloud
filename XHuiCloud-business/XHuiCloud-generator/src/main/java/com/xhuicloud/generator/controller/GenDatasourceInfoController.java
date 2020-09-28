package com.xhuicloud.generator.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xhuicloud.common.log.annotation.SysLog;
import com.xhuicloud.generator.entity.GenDatasourceInfo;
import com.xhuicloud.generator.handle.JdbcHandle;
import com.xhuicloud.generator.service.GenDatasourceInfoService;
import com.xhuicloud.common.core.utils.AesUtil;
import com.xhuicloud.common.core.utils.R;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @program: XHuiCloud
 * @description: DataSourceController
 * @author: Sinda
 * @create: 2020-06-22 11:53
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dataSource")
public class GenDatasourceInfoController {

    private final GenDatasourceInfoService genDatasourceInfoService;

    private final Map<String, JdbcHandle> handle;

    /**
     * 预测试数据库连接是否有效
     * TODO 后面入参需要加密
     */
    @GetMapping("/test")
    public R test(GenDatasourceInfo datasourceInfo) {
        return R.ok(handle.get(datasourceInfo.getType()).test(datasourceInfo));
    }

    /**
     * 分页查询数据源列表
     *
     * @param page
     * @return
     */
    @GetMapping("/page")
    public R page(Page page) {
        return R.ok(genDatasourceInfoService.page(page));
    }

    /**
     * 分页查询数据源的表信息
     *
     * @param id 数据源
     * @return
     */
    @GetMapping("/{id}")
    public R getTableInfoById(Page page, @PathVariable Integer id) {
        GenDatasourceInfo genDatasourceInfo = genDatasourceInfoService.getById(id);
        JdbcHandle jdbcHandle = handle.get(genDatasourceInfo.getType());
        jdbcHandle.addDynamicDataSource(genDatasourceInfo);
        return R.ok(jdbcHandle.getPageTableInfo(page));
    }

    /**
     * 保存
     * <p>
     * TODO 后面入参需要加密
     */
    @PostMapping
    @PreAuthorize("@authorize.hasPermission('sys_add_dataSource')")
    public R save(@RequestBody GenDatasourceInfo datasourceInfo) throws Exception {
        // 构建前端对应解密AES 因子
        datasourceInfo.setPassword(AesUtil.decrypt(datasourceInfo.getPassword()));
        datasourceInfo.setUsername(AesUtil.decrypt(datasourceInfo.getUsername()));
        if (handle.get(datasourceInfo.getType()).test(datasourceInfo)) {
            return R.ok(genDatasourceInfoService.save(datasourceInfo));
        }
        return R.failed();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @SysLog("删除")
    @DeleteMapping("/{id}")
    @PreAuthorize("@authorize.hasPermission('sys_delete_dataSource')")
    public R delete(@PathVariable Integer id) {
        return R.ok(genDatasourceInfoService.removeById(id));
    }

}
