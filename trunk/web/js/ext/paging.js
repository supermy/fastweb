Ext.onReady(function(){

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

    store.setDefaultSort('id', 'desc');

    var pagingBar = new Ext.PagingToolbar({
        pageSize: 2,
        store: store,
        displayInfo: true,
        displayMsg: '用户列表 {0} - {1} of {2}',
        emptyMsg: "没有用户",
        
        items:[
            '-', {
            pressed: true,
            enableToggle:true,
            text: '详细信息',
            cls: 'x-btn-text-icon details',
            toggleHandler: function(btn, pressed){
                var view = grid.getView();
                view.showPreview = pressed;
                view.refresh();
            }
        }]
    });

    var grid = new Ext.grid.GridPanel({
        el:'user-page-grid',
        width:660,
        height:300,
        title:'用户信息列表－分页',
        store: store,
        trackMouseOver:false,
        disableSelection:true,
        loadMask: true,

        // grid columns
        columns:[{
            id: 'id', // id assigned so we can apply custom css (e.g. .x-grid-col-topic b { color:#333 })
            header: "ID",
            dataIndex: 'id',
            width: 40,
            sortable: true
        },{
            header: "email",
            dataIndex: 'email',
            width: 100,
            sortable: true
        },{
            header: "姓名",
            dataIndex: 'name',
            width: 70,
            align: 'left',
            sortable: true
        },{
            id: 'last',
            header: "权限",
            dataIndex: 'roleNames',
            width: 150,
            sortable: true
        }],

        // customize view config
        viewConfig: {
            forceFit:true,
            enableRowBody:true,
            showPreview:true,
            getRowClass : function(record, rowIndex, p, store){
                if(this.showPreview){
                    p.body = '<p>'+record.data.enabled+'</p>';
                    return 'x-grid3-row-expanded';
                }
                return 'x-grid3-row-collapsed';
            }
        },

        // paging bar on the bottom
        bbar: pagingBar
    });

    // render it
    grid.render();

    //limit=pagesize;start=start_record
    store.load({params:{start:0, limit:2}});
});



/**
 * @class Ext.ux.SliderTip
 * @extends Ext.Tip
 * Simple plugin for using an Ext.Tip with a slider to show the slider value
 */
Ext.ux.SliderTip = Ext.extend(Ext.Tip, {
    minWidth: 10,
    offsets : [0, -10],
    init : function(slider){
        slider.on('dragstart', this.onSlide, this);
        slider.on('drag', this.onSlide, this);
        slider.on('dragend', this.hide, this);
        slider.on('destroy', this.destroy, this);
    },

    onSlide : function(slider){
        this.show();
        this.body.update(this.getText(slider));
        this.doAutoWidth();
        this.el.alignTo(slider.thumb, 'b-t?', this.offsets);
    },

    getText : function(slider){
        return slider.getValue();
    }
});
