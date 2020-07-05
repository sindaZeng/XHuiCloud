<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>快速开发平台统一认证</title>

    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/signin.css" rel="stylesheet">
</head>

<body class="sign_body">
<div class="container form-margin-top">
    <form class="form-signin" action="/token/form" method="post" name="f">
        <h2 class="form-signin-heading" align="center">统一认证系统</h2>
        <input type="text" name="username" class="form-control form-margin-top" placeholder="账号" required autofocus>
        </br>
        <input type="password" name="password" class="form-control" placeholder="密码" required>
        <button class="btn btn-lg btn-primary btn-block" onclick="">sign in</button>
        <#if error??>
            <span style="color: red; ">${error}</span>
        </#if>
    </form>
</div>
<footer>
    <p>support by: www.zsinda.cn</p>
    <p>email: <a href="mailto:sindazeng@gmain.com">sindazeng@gmain.com</a></p>
</footer>
</body>
<script type="text/javascript">

</script>
</html>
