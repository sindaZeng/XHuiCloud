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

import request from '@/utils/request'

/**
* ${tableComment}分页
* @param query
* @returns {*}
*/
export const ${className}Page = query => {
  return request({
    url: '/${moduleName}/${pathName}/page',
    method: 'get',
    params: query
  })
}

/**
* 通过id查询${tableComment}
* @param id
* @returns {*}
*/
export const get${ClassName}ById = id => {
  return request({
    url: '/${moduleName}/${pathName}' + id,
    method: 'post'
  })
}

/**
* 新增${tableComment}
* @param data
* @returns {*}
*/
export const create${ClassName} = data => {
  return request({
    url: '/${moduleName}/${pathName}',
    method: 'post',
    data
  })
}

/**
* 删除${tableComment}
* @param id
* @returns {*}
*/
export const del${ClassName} = id => {
  return request({
    url: '/${moduleName}/${pathName}/' + id,
    method: 'delete'
  })
}

/**
* 更新${tableComment}
* @param data
* @returns {*}
*/
export const update${ClassName} = data => {
  return request({
    url: '/${moduleName}/${pathName}',
    method: 'put',
    data
  })
}
