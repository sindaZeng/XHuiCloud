package com.zsinda.fdp.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsinda.fdp.entity.GenDatasourceInfo;
import com.zsinda.fdp.handle.JdbcHandle;
import com.zsinda.fdp.service.GenDatasourceInfoService;
import com.zsinda.fdp.utils.R;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @program: FDPlatform
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
     * TODO 后面入参需要加密
     */
    @GetMapping("/save")
    public R save(GenDatasourceInfo datasourceInfo) {
        if (handle.get(datasourceInfo.getType()).test(datasourceInfo)) {
            return R.ok(genDatasourceInfoService.save(datasourceInfo));
        }
        return R.failed();
    }

}
