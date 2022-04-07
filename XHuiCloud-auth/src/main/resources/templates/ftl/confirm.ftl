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

<body>
<nav class="navbar navbar-default container-fluid">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">开放平台</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-5">
            <p class="navbar-text navbar-right">
                <a target="_blank" href="">技术支持</a>
            </p>
            <p class="navbar-text navbar-right">
                <a target="_blank" href="">申请接入</a>
            </p>
        </div>
    </div>
</nav>
<div style="padding-top: 80px;width: 300px; color: #555; margin:0px auto;">
    <form id='confirmationForm' name='confirmationForm' action="/oauth/authorize" method='post'>
        <input name='user_oauth_approval' value='true' type='hidden'/>
        <p>
            <a href="#" target="_blank">${app.name!'未知应用'}</a> 同意将获得以下权限：</p>
        <ul class="list-group">
            <li class="list-group-item">
                <#list scopeList as scope>
                    <input type="hidden" name="${scope}" value="true"/>
                </#list>
                <#list permission?keys as key>
                    <input type="hidden" name="${permission[key]}" value="true"/>
                    <input type="checkbox" disabled checked="checked"/><label>${permission[key]}</label>
                </#list>
            </li>
        </ul>
        <p class="help-block">授权后表明你已同意 <a>服务协议</a></p>
        <button class="btn btn-success pull-right" type="submit" id="write-email-btn">授权</button>
        </p>
    </form>
</div>
<footer>
    <p>support by: 星辉云</p>
    <p>email: <a href="mailto:wangiegie@gmail.com">sindazeng@gmail.com</a>.</p>
</footer>
</body>
</html>
