/*
 * MIT License
 * Copyright <2021-2022>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @Author: Sinda
 * @Email:  xhuicloud@163.com
 */

package com.xhuicloud.upms.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.xhuicloud.common.core.constant.CacheConstants;
import com.xhuicloud.upms.entity.SysDept;
import com.xhuicloud.upms.mapper.SysDeptMapper;
import com.xhuicloud.upms.service.SysDeptService;
import com.xhuicloud.upms.vo.DeptVo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Override
    @Cacheable(value = CacheConstants.SYS_DEPT, unless = "#result == null")
    public List<Integer> getAllDeptIds() {
        return list(Wrappers.emptyWrapper()).stream().map(SysDept::getId).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstants.SYS_DEPT, allEntries = true)
    public Boolean saveDept(SysDept sysDept) {
        return save(sysDept);
    }

    @Override
    @CacheEvict(value = CacheConstants.SYS_DEPT, allEntries = true)
    public Boolean deleteDept(Integer id) {
        return null;
    }

    @Override
    @CacheEvict(value = CacheConstants.SYS_DEPT, allEntries = true)
    public Boolean updateDept(SysDept sysDept) {
        return null;
    }

    @Override
    public List<List<Integer>> getDeptTree(List<DeptVo> deptVos) {
        List<SysDept> sysDepts = list(Wrappers.emptyWrapper());
        List<List<Integer>> depts = Lists.newArrayList();
        for (DeptVo deptVo : deptVos) {
            List<Integer> oneDepts = Lists.newArrayList();
            completeDepts(sysDepts, deptVo.getDeptId(), oneDepts);
            Collections.sort(oneDepts);
            depts.add(oneDepts);
        }
        return depts;
    }

    /**
     * 补全完整的部门id
     * @param sysDepts
     * @param deptId
     * @param oneDepts
     */
    private void completeDepts(List<SysDept> sysDepts, Integer deptId, List<Integer> oneDepts) {
        for (SysDept sysDept : sysDepts) {
            if (deptId == sysDept.getId()) {
                oneDepts.add(deptId);
                if (sysDept.getParentId() != 0 ){
                    completeDepts(sysDepts, sysDept.getParentId(), oneDepts);
                }
            }
        }
    }

}
