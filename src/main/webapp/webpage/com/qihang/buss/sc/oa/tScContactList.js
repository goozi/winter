$(function(){
    $('#tt').tree({
        data: [{
            text: '个人通讯簿',
            attributes:{url:"tScContactController.do?tScContact&type=1"}
        },{
            text: '共享通讯簿',
            attributes:{url:"tScContactController.do?tScContact&type=2"}
        }],
        onClick: function(node){
            document.getElementById('message').src = node.attributes.url;
        }
    });
})

