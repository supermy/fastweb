Ext.onReady(function(){
    Ext.QuickTips.init();

    function formatDate(value){
        return value ? value.dateFormat('M d, Y') : '';
    };
    // shorthand alias
    var fm = Ext.form;

    // custom column plugin example
    var checkColumn = new Ext.grid.CheckColumn({
       header: "enabled?",
       dataIndex: 'enabled',
       width: 55
    });

    var cm = new Ext.grid.ColumnModel([{
           id:'id',
           header: "ID",
           dataIndex: 'id',
           width: 30,
           editor: new fm.TextField({
               allowBlank: false
           })
        },{
           header: "email",
           dataIndex: 'email',
           width: 130,
           editor: new fm.TextField({
               allowBlank: false
           })
        },{
           header: "name",
           dataIndex: 'name',
           width: 70,
           align: 'right',
            editor: new fm.TextField({
               allowBlank: false
           })
       },{
           header: "role name",
           dataIndex: 'roleNames',
           width: 95,
           editor: new fm.TextField({
               allowBlank: false
           })
        },
        checkColumn
    ]);

    // by default columns are sortable
    cm.defaultSortable = true;

    // this could be inline, but we want to define the Plant record
    // type so we can add records dynamically
    var Plant = Ext.data.Record.create([
           // the "name" below matches the tag name to read, except "availDate"
           // which is mapped to the tag "availability"
           {name: 'id', type: 'string'},
           {name: 'email', type: 'string'},           {name: 'name', type: 'string'},           {name: 'roleNames', type: 'string'},
	   {name: 'enabled', type: 'bool'}      ]);

  var store = new Ext.data.JsonStore({
        root: 'result',
	autoLoad: true,
        remoteSort: false,
	totalProperty: 'totalCount',
        fields: [
           	"id","email","name","roleNames","enabled"
        ],

        proxy: new Ext.data.HttpProxy({
            url: '/fastweb/user/user!jsonList.action'
        })

    });

    // create the editor grid
    var grid = new Ext.grid.EditorGridPanel({
        store: store,
        cm: cm,
        renderTo: 'editor-grid',
        width:660,
        height:300,
        autoExpandColumn:'id',
        title:'用户列表(可编辑)',
        frame:true,
        plugins:checkColumn,
        clicksToEdit:1,

        tbar: [{
            text: '增加用户',
            handler : function(){
                var p = new Plant({
                    id: '1',
                    email: 'xx@xx.xx',
                    name:'xx',
                    roleNames: '',
                    enabled: false
                });
                grid.stopEditing();
                store.insert(0, p);
                grid.startEditing(0, 0);
            }
        }]
    });

    // trigger the data store load
    store.load();
});

Ext.grid.CheckColumn = function(config){
    Ext.apply(this, config);
    if(!this.id){
        this.id = Ext.id();
    }
    this.renderer = this.renderer.createDelegate(this);
};

Ext.grid.CheckColumn.prototype ={
    init : function(grid){
        this.grid = grid;
        this.grid.on('render', function(){
            var view = this.grid.getView();
            view.mainBody.on('mousedown', this.onMouseDown, this);
        }, this);
    },

    onMouseDown : function(e, t){
        if(t.className && t.className.indexOf('x-grid3-cc-'+this.id) != -1){
            e.stopEvent();
            var index = this.grid.getView().findRowIndex(t);
            var record = this.grid.store.getAt(index);
            record.set(this.dataIndex, !record.data[this.dataIndex]);
        }
    },

    renderer : function(v, p, record){
        p.css += ' x-grid3-check-col-td'; 
        return '<div class="x-grid3-check-col'+(v?'-on':'')+' x-grid3-cc-'+this.id+'">&#160;</div>';
    }
};
