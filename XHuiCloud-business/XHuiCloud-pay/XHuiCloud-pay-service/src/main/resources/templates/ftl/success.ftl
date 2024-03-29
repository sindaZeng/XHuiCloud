<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta content="email=no" name="format-detection">
<title>付款成功</title>
<link rel="stylesheet" href="https://act.weixin.qq.com/static/cdn/css/wepayui/0.1.1/wepayui.min.css">
<link rel="stylesheet" href="/XHuiCloud-pay-service/css/pay.css">
</head>
<body ontouchstart class="weui-wepay-pay-wrap">
<div class="msg_success">
    <div class="weui-msg">
        <div class="weui-msg__icon-area"><i class="weui-icon-success weui-icon_msg"></i></div>
        <div class="weui-msg__text-area">
            <h2 class="weui-msg__title">付款成功</h2>
<#--            <p class="weui-msg__desc">内容详情，可根据实际需要安排</p>-->
        </div>
        <div class="weui-msg__opr-area">
            <p class="weui-btn-area">
                <a href="javascript:closeWindow();" class="weui-btn weui-btn_primary">确认</a>
                <!-- <a href="javascript:history.back();" class="weui-btn weui-btn_default">辅助操作</a> -->
            </p>
        </div>

    </div>
    <div class="weui-wepay-logos weui-wepay-logos_ft">
    	<div class="weui-msg__extra-area">
			<div class="weui-footer" style="margin-bottom: 30px;">
				<p class="weui-footer__links">
					<a href="javascript:void(0);" class="weui-footer__link">星辉云</a>
				</p>
				<p class="weui-footer__text">Copyright © 2020-2022</p>
			</div>
			<img src="https://act.weixin.qq.com/static/cdn/img/wepayui/0.1.1/wepay_logo_default_gray.svg" alt="" height="16">
		</div>
	</div>
</div>
</body>
<script src="//cdn.bootcss.com/jquery/1.11.0/jquery.min.js"></script>
<script src="//cdn.bootcss.com/jquery-weui/1.0.1/js/jquery-weui.min.js"></script>
<script type="text/javascript">
	function closeWindow() {
		WeixinJSBridge.call('closeWindow');
	}
</script>
</html>
