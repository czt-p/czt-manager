<template>
  <el-card class="box-card scrollbar">
    <template slot="header">
      <slot :name="options.caption"></slot>
    </template>
    <el-table  ref="multipleTable" :data="tableData"
                fit
                stripe
                 :highlight-current-row="options.highlight"
                 @selection-change="selectionChange"
                 @cell-click="tableCellClick"
                 @select-all="selectAll"
                 @current-change="handleRowChange"
      >
        <el-table-column :type="options.type" width="50" label="序号">
          <template scope="scope">
              <span v-if='pageData'>{{ pageData.limit * (pageData.pageIndex - 1)  + 1 + scope.$index}}</span>
              <span v-else>{{scope.$index+1}}</span>
          </template>
        </el-table-column>
        <el-table-column v-for="(item,index) in options.th"
                         :property="item.property"
                         :width='item.width'
                         :label="item.label" :key='index'
                         style='height:40px;overflow:hidden;'>
          <template slot-scope="scope">
            <label v-if='item.type=="time"'>{{new Date(scope.row[item.property]-0).Format('yyyy-MM-dd hh:mm:ss')}}</label>
            <!-- <label v-if='item.type=="html"' v-html='scope.row[item.property]' >{{scope.row[item.property]}}</label> -->
            <label v-if='item.type=="html"'>
              {{scope.row[item.property].replace(reg,'').substr(0,60).length==60?scope.row[item.property].replace(reg,'').substr(0,60)+'...':scope.row[item.property].replace(reg,'').substr(0,60)}}
              </label>
            <label v-else>{{scope.row[item.property]}}</label>
          </template>
        </el-table-column>
        <el-table-column v-if="options.deals && options.deals.max > 0"
                         :label="options.deals.label || '操作'"
                         align="left"
                         :width="options.deals.max*60">
          <template scope="scope" >
            <template v-for="(deal,index) in scope.row.deals" >
              <label v-if="deal.type == 'label'" class="deal-label" :key='index'>
                {{deal.text}}
              </label>
              <el-button v-else
                         :type="deal.type"
                         size="mini"
                         :style='{background:deal.bgc,color:"#fff",border:"none"}'
                         @click.native="onClickHandler(scope.$index, scope.row,deal.event)"
                         :name="deal.event" :key='index'>
                {{deal.text}}
              </el-button>
            </template>
          </template>
        </el-table-column>
      </el-table>
    <el-row v-if="pageData" type="flex" align="middle" :gutter="20" class="page-nation">
      <el-col  :span="24" style="text-align: right">
        <el-pagination
          @current-change="handleCurrentPage"
          :current-page="pageData.pageIndex"
          layout="total,prev, pager, next"
          :page-size="pageData.limit"
          :total="pageData.total">
        </el-pagination>
      </el-col>
    </el-row>
    </el-card>
</template>
<script>
  import { mapGetters, mapActions } from 'vuex'
  import Util from "framework/util/util"
  export default {
    data() {
      return {
        currentRow: 0,
        reg:/<\/?.+?\/?>/g
      }
    },
    computed: {
      ...mapGetters({
        tableData: "tableData",
        pageData: "pageData",
        options:"options",
        result:"result",
      }),
    },
    methods: {
      //表格事件
      tableCellClick: function (row,column,cell,event){
        if (column.label == '操作') {
          //点击操作栏内的功能按钮
        } else if(column.type == 'selection'){
          row.$info = !row.$selected;//行选中转换
        }else{
          //单击表格切换选中状态
          row.$selected = !row.$selected;
          row.$info = row.$selected;
          //更新数据模型
          //this.toggleSelection(row);
          this.setSelectionData(row)
        }
      },
      toggleSelection(row){
        if (row) {
          this.$refs.multipleTable.clearSelection();
          this.$refs.multipleTable.toggleRowSelection(row);
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },
      //用户单击行
      setSelectionData(row){
       /* //this.setCurrent(row);
        this.$store.dispatch('tableHandler',"detail");
        this.$store.dispatch('trHandler',{data:row});
        this.$store.dispatch('getCurrentData',row);
        //this.$emit('detail',row,0)*/
      },
      //用户点击单个checkbox
      selectionChange(val){
        var arr = [];
        val.forEach((item)=>{
          arr.push(item.id);
        });
        this.$store.dispatch(this.currentTable+'/tableSelected',arr);
      },
      //用户点击全选
      selectAll(selection){
        this.selectionChange(selection)
      },
      //用户点击操作栏
      onClickHandler(index,row,type){
        switch(type){
          case"change":
            this.handleChange(row);
            break;
          case"reset":
            this.handleReset(row);
            break;
          case"delete":
            this.handleDelete(row);
            break;
          case"view":
            this.$store.dispatch(this.currentTable+'/openViewDialog',row);
            break;
          case"add":
            this.$store.dispatch(this.currentTable+'/addItem',row);
            break;
          default:
            break;
        }
      },

      handleChange(row){
        //this.toggleSelection(row);
        this.$store.dispatch(this.currentTable+'/getCurrentData',row);
      },
      //行点击
      trHandler(row){
        this.$store.dispatch(this.currentTable+'/trHandler',row);
      },
      //用户点击重置
      handleReset(row){
        //this.message('info',"功能维护中");
        this.$store.dispatch(this.currentTable+'/resetPass',row);
      },
      handleRowChange(val,old){
        // this.$store.dispatch(this.currentTable+'/trHandler',val);
        // console.log(val,old);
      },
      //单体删除
      handleDelete(row){
        this.selectItems=[];
        this.selectItems.push(row.id);
        this.handleConfirm(
          '此操作将永久删除该数据, 是否继续?',
          "提示",
          {confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'},
          this.onDelete);
      },
      handleRemove(){
        var tableData = this.tableData;
        this.selectItems.forEach(function (id) {
          tableData.forEach(function (data) {
            if(id == data.id){
              tableData.splice(tableData.indexOf(data),1)
            }
          })
        });
        this.selectItems = [];
      },
      handleConfirm(message="删除",title="提示",button={confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'},callBack=this.onDelete){
        this.$confirm(message,title,button,callBack).then(() => {
          callBack();
        }).catch(() => {
          this.message('info',"已取消删除");
        });
      },
      handleCurrentPage(pageIndex){
        // console.log(pageIndex)
        this.$store.dispatch(this.currentTable + '/pageChange',pageIndex);
      },
      onDelete(){
        this.$store.dispatch(this.currentTable + '/deleteItem',this.selectItems);
      },
      success(type,data){
        switch(type){
          case"delete":
          case"add":
          case"change":
            this.message('success',data.msg);
            break;
          case"reset":
            this.message('success',"密码重置成功，新密码为" + data.data);
            break;
          default:
            break;
        }
        this.$store.dispatch(this.currentTable + '/getItems');
        this.$store.dispatch(this.currentTable + '/dialogClose');

      },
      error(data){
        this.message('warning',data.msg);
      },
      message(type,msg){
        this.$message({
          type: type,
          message:msg,
          duration:"3000"
        });
      }
    },
    watch:{
      result:{
        handler:function({type,data}){
          data.success ? this.success(type,data) : this.error(data);
        },
        deep:true//对象内部的属性监听，也叫深度监听
      },
      tableData:function(val){
        val.length>0?this.$refs.multipleTable.setCurrentRow(this.tableData[0]):'';
      }
    },
    beforeCreate(){
      // console.log()
    },
    created () {
      this.currentTable = Util.getItem("currentVue").vue;
      this.$store.dispatch(this.currentTable + '/getItems');
    },
    mounted(){
      
    }
  }
</script>
<!--table 组件 api-->
<style>
  .el-table td, .el-table th {
    padding: 4px 0;
  }
  .page-nation{
    padding:10px 10px 0 10px;
  }
  .el-pager li {
    border: 1px solid #ccc;
    border-radius: 5px;
    margin: 0 3px;
    min-width: 25px;
    color: #5a5e66;
  }
  .el-pager li.active+li {
    border: 1px solid #ccc;
  }
  .el-pagination .btn-prev,.el-pagination .btn-next {
    border: 1px solid #ccc;
    border-radius: 5px;
    min-width: 25px;
  }
  .deal-label{
    font-size: 12px;
    color: #5a5e66;
  }
</style>

