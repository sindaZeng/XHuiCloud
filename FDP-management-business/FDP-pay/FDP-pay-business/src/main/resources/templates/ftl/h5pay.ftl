<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <link rel="stylesheet" href="https://act.weixin.qq.com/static/cdn/css/wepayui/0.1.1/wepayui.min.css">
    <link rel="stylesheet" href="/FDP-pay-business/css/pay.css">
    <link rel="stylesheet" href="/FDP-pay-business/css/keyboard.css">
    <link rel="stylesheet" href="/FDP-pay-business/css/tenant.css">
    <title>商户收款</title>
</head>
<!--
	通用说明：
	1.模块的隐藏添加class:hide;
	2.body标签默认绑定ontouchstart事件，激活所有按钮的:active效果
-->
<body ontouchstart class="weui-wepay-pay-wrap">
<div>
    <div class="tenant-logo">
        <img src="${tenant.logo!''}"/>
    </div>
    <div class="tenant-names">
        <div style="height: 60%;font-size:18px;color: #333333;font-weight: bold;font-family: 微软雅黑, arial;">
            <span>${tenant.name!'请跟商家核对商户名'}</span>
        </div>
        <div style="height: 40%;font-size:12px">
            <a id="prompt" href="avascript:void(0);">添加备注</a>
        </div>
    </div>
</div>
<div class="weui-wepay-pay">
    <div class="weui-wepay-pay__bd">
        <div class="weui-wepay-pay__inner">
            <h1 class="weui-wepay-pay__title">付款金额(元)</h1>
            <div class="weui-wepay-pay__inputs"><strong class="weui-wepay-pay__strong">￥</strong>
                <input id="paymoney" type="text" class="weui-wepay-pay__input" placeholder="请输入金额"></div>
            <div class="weui-wepay-pay__intro">向商家询问支付金额</div>
        </div>
    </div>
    <div class="weui-wepay-pay__ft">
        <p class="weui-wepay-pay__info">支付金额给商户</p>
        <div class="weui-wepay-pay__btn">
            <#if channel == 'WEIXIN_WAP'>
                <img class="weui-btn"
                     src="https://act.weixin.qq.com/static/cdn/img/wepayui/0.1.1/wepay_logo_default_gray.svg" alt=""
                     height="16">
            </#if>
            <#if channel == 'ALIPAY_WAP'>
                <div class="alipay-logo"
                     style="position: absolute; left: 50%; margin-left: -35px; margin-top: 10px; margin-bottom: auto;">
                </div>
            </#if>

        </div>
    </div>
</div>
<div class="payinfo">
    <table cellspacing="0" cellpadding="0">
        <tr>
            <td class="paynum">1</td>
            <td class="paynum">2</td>
            <td class="paynum">3</td>
            <td id="pay-return">
                <div class="keybord-return"></div>
            </td>
        </tr>
        <tr>
            <td class="paynum">4</td>
            <td class="paynum">5</td>
            <td class="paynum">6</td>
            <td rowspan="3" class="pay">支付</td>
        </tr>
        <tr>
            <td class="paynum">7</td>
            <td class="paynum">8</td>
            <td class="paynum">9</td>
        </tr>
        <tr>
            <td id="pay-stop">
                <div class="keybord-stop"></div>
            </td>
            <td id="pay-zero">0</td>
            <td id="pay-float">.</td>
        </tr>
    </table>
</div>
</body>
<script src="//cdn.bootcss.com/jquery/1.11.0/jquery.min.js"></script>
<script src="//cdn.zsinda.cn/layui.js"></script>
<script type="text/javascript">
var layer = layui.use('layer', function () {
    return layui.layer
});
var remark = ''
$(function () {
    $(".payinfo").slideDown();
    var $paymoney = $("#paymoney");

    $("#pay-stop").click(function () {
        $(".payinfo").slideUp("fast");
    });

    $("#paymoney").focus(function () {
        $(".payinfo").slideDown();
        document.activeElement.blur();
    });

    $(".paynum").each(function () {
        $(this).click(function () {
            if (($paymoney.val()).indexOf(".") !== -1 && ($paymoney.val()).substring(($paymoney.val()).indexOf(".") + 1, ($paymoney.val()).length).length === 2) {
                return;
            }
            if ($.trim($paymoney.val()) === "0") {
                return;
            }
            $paymoney.val($paymoney.val() + $(this).text());
        });
    });

    $("#pay-return").click(function () {
        $paymoney.val(($paymoney.val()).substring(0, ($paymoney.val()).length - 1));
    });

    $("#pay-zero").click(function () {
        if (($paymoney.val()).indexOf(".") !== -1 && ($paymoney.val()).substring(($paymoney.val()).indexOf(".") + 1, ($paymoney.val()).length).length === 2) {
            return;
        }
        if ($.trim($paymoney.val()) === "0") {
            return;
        }
        $paymoney.val($paymoney.val() + $(this).text());
    });

    $("#pay-float").click(function () {
        if ($.trim($paymoney.val()) === "") {
            return;
        }

        if (($paymoney.val()).indexOf(".") !== -1) {
            return;
        }

        if (($paymoney.val()).indexOf(".") !== -1) {
            return;
        }

        $paymoney.val($paymoney.val() + $(this).text());
    });

    $(".pay").click(function () {
            location.href = '/FDP-pay-business/pay/createOrder?tenant_id=' +
            ${tenant.id}+'&remark='+remark
        }
    );
    var $prompt = $("#prompt")
    $prompt.click(function (){
        layer.prompt({title: '写点什么吧'}, function (value, index) {
            remark = value;
            if (value.length>=4){
                value=value.substring(0,4);
            }
            document.getElementById("prompt").innerText=value +'...'
            layer.close(index);
        });
    });
});


</script>
<#if channel == 'WEIXIN_WAP'>
    <style>
        .pay {
            color: #ffffff;
            font-size: 120%;
            background-color: #1AAD19;
        }
        .pay:active {
            background-color: #179B16;
        }
    </style>
</#if>
<#if channel == 'ALIPAY_WAP'>
    <style>
        .pay {
            color: #ffffff;
            font-size: 120%;
            background-color: #108ee9;
        }

        .pay:active {
            background-color: #108ee9;
        }
    </style>
</#if>

</html>

