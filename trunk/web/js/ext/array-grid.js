Ext.onReady(function(){

    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

   var store1 = new Ext.data.JsonStore({
        root: 'result',
	autoLoad: true,
        remoteSort: false,
        fields: [
           	"id","email","name","roleNames"
        ],

        proxy: new Ext.data.HttpProxy({
            url: '/fastweb/user/user!jsonList.action'
        })

    });

    var grid1 = new Ext.grid.GridPanel({
        store: store1,
        columns: [
            {id:'id',header: "ID",  sortable: true, dataIndex: 'id'},
            {header: "email",  sortable: true,  dataIndex: 'email'},
            {header: "name",  sortable: true,  dataIndex: 'name'},
            {header: "roleNames",  sortable: true,  dataIndex: 'roleNames'}
        ],
        stripeRows: true,
        autoExpandColumn: 'id',
        height:300,
        width:660,
        title:'my Grid'
    });


    grid1.render('user-grid');
});
