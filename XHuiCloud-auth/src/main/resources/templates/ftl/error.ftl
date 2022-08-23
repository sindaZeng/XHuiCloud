<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>星辉云认证授权</title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/auth.css"/>
</head>

<body class="auth_body">
<nav class="navbar navbar-default container-fluid">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">开放平台</a>
        </div>
        <div class="collapse navbar-collapse">
            <p class="navbar-text navbar-right">
                <a target="_blank" href="">申请接入</a>
            </p>
        </div>
    </div>
</nav>
<div class="panel panel-danger" style="width: 500px;margin: 0 auto;">
    <div class="panel-heading">
        请按照OAuth2.0协议规范使用!
        <button type="button" class="close" data-dismiss="alert" aria-label="Close" onclick="javascript:history.go(-1)">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <div class="panel-body">
        <p>错误码: ${errorCode}</p>
        <p>描述: ${description}</p>
        <p>参照: ${uri}</p>
    </div>
</div>
<footer>
    <p>support by: 星辉云</p>
    <p>email: <a href="mailto:wangiegie@gmail.com">sindazeng@gmail.com</a>.</p>
</footer>
</body>
</html>
