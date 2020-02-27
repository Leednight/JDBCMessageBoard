<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <title>注册</title>
    <link rel="stylesheet" href="<%=basePath%>/css/login.css">

    <script type="text/javascript">
        function changeImg() {
            var img = document.getElementById("img");
            img.src = "/verificationCode.do?date=" + new Date();
        }

        function checkVerificationCode() {
            var verificationCode = document.getElementById('verificationCode').value;
            var flag = (getCookie('v_c_v') == verificationCode);
            if (!flag) {
                alert('验证码输入错误');
            }
            return flag;
        }

        function checkPassword() {
            var pwd1 = document.getElementById('pwd1').value;
            var pwd2 = document.getElementById('pwd2').value;
            var flag = (pwd1 == pwd2);
            if (!flag) {
                alert('两次密码不一致！');
            }
            return flag;
        }

        function checkNull() {
            var pwd1 = document.getElementById('pwd1').value;
            var name = document.getElementById('name').value;
            var birthday = document.getElementById('birthday').value;
            var flag = (pwd1 !=null && name != null && pwd1 !="" && name !=""&&birthday!=""&&birthday!=null);
            if (!flag) {
                alert('用户名,密码,生日不能为空！');
            }
            return flag;
        }

        function getCookie(cookie_name) {
            var allCookies = document.cookie;
            var cookie_pos = allCookies.indexOf(cookie_name);   //如果找到了索引，就代表cookie存在
            if (cookie_pos != -1) {
                cookie_pos += cookie_name.length + 1;
                var cookie_end = allCookies.indexOf(";", cookie_pos);
                if (cookie_end == -1) {
                    cookie_end = allCookies.length;
                }
                return unescape(allCookies.substring(cookie_pos, cookie_end));
            }
            return null;
        }
    </script>
</head>
<body>
<div class="login">
    <div class="header">
        <h1>
            <a href="/login.do">登录</a>
            <a href="/regPrompt.do">注册</a>
            <a href="/message/list.do">返回留言板</a>
        </h1>
        <button></button>
    </div>
    <form action="/regist.do" method="post">
        <div class="name">
            <label  class="col-sm-2 control-label">用户名 ：</label>
            <input type="text" id="name" name="username" placeholder="请输入登录用户名">
        </div>
        <div class="pwd">
            <label  class="col-sm-2 control-label">密码 ：</label>
            <input type="password" id="pwd1" name="password" placeholder="6-16位密码，区分大小写，不能用空格">
        </div>
        <div class="pwd">
            <label  class="col-sm-2 control-label">确认密码 ：</label>
            <input type="password" id="pwd2" name="password2" placeholder="再次输入密码！">
        </div>
        <div class="name">
            <label for="realName" class="col-sm-2 control-label">姓名 ：</label>
            <input type="text" id="realName" name="realName" placeholder="请输入真实姓名">
        </div>
        <div class="name">
            <label for="birthday" class="col-sm-2 control-label">生日 ：</label>
            <input type="String" id="birthday" name="birthday" placeholder="请输入生日">
        </div>
        <div class="name">
            <label for="phone" class="col-sm-2 control-label">电话 ：</label>
            <input type="text" id="phone" name="phone" placeholder="请输入电话号码">
        </div>
        <div class="name">
            <label for="address" class="col-sm-2 control-label">地址 ：</label>
            <input type="text" id="address" name="address" placeholder="请输入地址">
            <label>验证码 ：</label>
        </div>

        <div class="idcode">
            <input type="text" id="verificationCode" placeholder="请输入验证码">
            <a href='#' onclick="javascript:changeImg()">&nbsp;&nbsp;&nbsp;&nbsp;换一张</a>
            <span><img id="img" src="/verificationCode.do"/></span>
            <div class="clear"></div>
        </div>
        <div class="btn-red">
            <input onclick="return checkVerificationCode()&&checkNull()&&checkPassword();" type="submit" value="注册" id="reg-btn">
        </div>
    </form>
</div>
</body>
</html>