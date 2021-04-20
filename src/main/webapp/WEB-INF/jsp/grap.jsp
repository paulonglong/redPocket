<!DOCTYPE html>
<%@ page contentType="text/html;charset=gb2312" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>grap</title>
    <!-- Query文件 -->
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            //模拟30000个异步请求，进行并发
            var max = 30000;
            for (var i = 1; i <= max; i++) {
                //jQuery的post请求，请注意这是异步请求
                $.post({
                    //请求抢id为1的红包
                    url: "/userRedPacket/grapRedPacketByRedis?redPacketId=2&userId=" + i,
                    //成功后的方法
                    success: function (result) {
                        console.log(result)
                    }
                });
            }
        });
    </script>
</head>
<body>
</body>
</html>