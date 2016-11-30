$(function(){
    $('#tt').tree({
        data: [{
            text: '收件箱',
            state: 'open',
            attributes:{url:"tScMessageReceiveController.do?tScMessageReceive"},
            children: [{
                text: '已读消息',
                attributes:{url:"tScMessageReceiveController.do?tScMessageReceive&readStatus=1"}
            },{
                text: '未读消息',
                attributes:{url:"tScMessageReceiveController.do?tScMessageReceive&readStatus=0"}
            }]
        },{
            text: '发件箱',
            attributes:{url:"tScMessageController.do?tScMessage"}
        }],
        onClick: function(node){
            document.getElementById('message').src = node.attributes.url;
        }
    });
})

