<!DOCTYPE html>
<%@ page contentType="text/html;charset=gb2312" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>grap</title>
    <!-- Query�ļ� -->
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            //ģ��30000���첽���󣬽��в���
            var max = 30000;
            for (var i = 1; i <= max; i++) {
                //jQuery��post������ע�������첽����
                $.post({
                    //������idΪ1�ĺ��
                    url: "/userRedPacket/grapRedPacketByRedis?redPacketId=2&userId=" + i,
                    //�ɹ���ķ���
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