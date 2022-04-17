package com.xhuicloud.common.security.component;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.Assert;

public class XHuiOAuth2ExpressionParser extends SpelExpressionParser {

    private final ExpressionParser delegate;

    public XHuiOAuth2ExpressionParser(ExpressionParser delegate) {
        Assert.notNull(delegate, "delegate cannot be null");
        this.delegate = delegate;
    }

    public Expression parseExpression(String expressionString) throws ParseException {
        return delegate.parseExpression(wrapExpression(expressionString));
    }

    public Expression parseExpression(String expressionString, ParserContext context) throws ParseException {
        return delegate.parseExpression(wrapExpression(expressionString), context);
    }

    private String wrapExpression(String expressionString) {
        if (!expressionString.contains("#oauth2")) {
            return expressionString;
        }
        return "#oauth2.throwOnError(" + expressionString + ")";
    }

}
