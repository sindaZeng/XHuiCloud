package com.zsinda.fdp.datasource;

import com.baomidou.mybatisplus.core.parser.AbstractJsqlParser;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.update.Update;

/**
 * @program: FDPlatform
 * @description: SQL拦截
 * @author: Sinda
 * @create: 2020-05-13 10:57
 */
public class FdpJsqlParser extends AbstractJsqlParser {
    @Override
    public void processInsert(Insert insert) {

    }

    @Override
    public void processDelete(Delete delete) {
        Assert.notNull(delete.getWhere(), "删除条件不能为空!");
    }

    @Override
    public void processUpdate(Update update) {
        Assert.notNull(update.getWhere(), "更新条件不能为空!");
    }

    @Override
    public void processSelectBody(SelectBody selectBody) {

    }
}
