<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <title>星辉云认证平台</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/auth.css" rel="stylesheet">
    <script type="text/javascript" src="/js/crypto-js.js"></script>
    <script type="text/javascript" src="/js/login.js"></script>
</head>

<body class="auth_body">
<div class="container form-container">
    <#if error??>
        <div class="alert alert-warning" role="alert"><strong>警告!</strong> ${error}</div>
    </#if>
    <div class="header-container">
        <img id="icon" src="https://xhuicloud.oss-cn-shenzhen.aliyuncs.com/xhuicloud-logo.png" alt="" class="icon">
        <div class="title">星辉云</div>
    </div>
    <form class="form-auth" action="/authorize/form" method="post">
        <#if tenants??>
            <select id="tenantSelect" class="form-control" placeholder="请选择租户" name="tenantId" onchange="selectTenant()">
                <#list tenants as tenant>
                    <option value="${tenant.id}" logo="${tenant.logo}">${tenant.name}</option>
                </#list>
            </select>
        </#if>
        </br>
        <input type="text" name="username" class="form-control" placeholder="账号" required autofocus>
        </br>
        <input type="password" name="password" class="form-control" placeholder="密码" required>
        <button class="btn btn-lg btn-primary btn-block" onclick="submitForm()">登录</button>
    </form>
</div>
<footer>
    <p>support by: 星辉云</p>
    <p>email: <a href="mailto:sindazeng@gmail.com">sindazeng@gmail.com</a></p>
</footer>
</body>
<script type="text/javascript">
</script>
</html>
