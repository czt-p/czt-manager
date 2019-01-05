<template>
  <div class="container">
    <!-- <SearchVue v-bind:current="currentTable"></SearchVue> -->
    <div class="title">账号管理</div>
    <div class="searchArea">
      <div class="searchInput">
        <!-- <div class="searchTime">
          <span>时间：</span>
          <el-date-picker
            v-model="searchObj.searchTime"
            type="datetimerange"
            :editable='false'
            size='small'
            value-format="timestamp"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
        </div> -->
        <div class="askName">
          账号名称：<el-input size='small' @change.native="inputChange" name="name" placeholder="请输入账号名称"></el-input>
        </div>
      </div>
      <div class="rightBtn">
        <el-button type="primary" icon="el-icon-search" size="small" name="submit" @click.native="onClickHandler">开始搜索</el-button>
        <el-button type="primary" icon="el-icon-plus" size="small" name="add" @click.native="onTopClickHandler('add')" style='background:#40AB9B'>新增</el-button>
      </div>
    </div>
    <TableVue v-bind:currentTable="currentTable">
      <!-- <template slot="caption">
        <div class="clearfix">
          <span>用户列表</span>
          <el-button v-for="(deal,index) in options.tops"
                     :type="deal.type"
                     size="small"
                     :key='index'
                     style="float: right;margin-left:5px"
                     @click.native="onTopClickHandler(deal.event)"
                     :name="deal.event">
            {{deal.text}}
          </el-button>
        </div>
      </template> -->
    </TableVue>
    <br/>
    <DialogVue>
      <template slot="addUser">
        <AddVue></AddVue>
      </template>
      <template slot="changeUser">
        <ChangeVue></ChangeVue>
      </template>
    </DialogVue>
  </div>
</template>
<script>
  import "src/basic"
  import { mapGetters, mapActions } from 'vuex'
  import XHR from "framework/xhr/xhr"
  import Util from "framework/util/util"
  import TableVue from 'framework/components/TableVue'
  import DialogVue from 'framework/components/dialogVue'
  import SearchVue from 'framework/components/searchVue'
  import AddVue from './children/add'
  import ChangeVue from './children/change'

  export default {
    name: 'userManager',
    data(){
      return{
        currentTable:"userManager"
      }
    },
    components: {
      TableVue,
      DialogVue,
      SearchVue,
      AddVue,
      ChangeVue
    },
    computed: {
      ...mapGetters({
        setState: "setState",
        options:"options"
      }),
      ...mapGetters("userManager",{
        tableSelected:"tableSelected"
      }),
    },
    methods:{
      inputChange(e) {
        let srcElement = e.srcElement || e.target, name = srcElement.getAttribute("name"), value = srcElement.value;
        this.$store.dispatch(this.currentTable + '/resetParam', {[name]: value});
      },
      onTopClickHandler(type){
        switch(type){
          case"add":
            this.handleAdd();
            break;
          case"deletes":
            this.handleDeletes();
            break;
          default:
            break;
        }
      },
      onClickHandler(e) {
        let srcElement = e.srcElement || e.target, eventName = srcElement.getAttribute("name");
        eventName = !eventName ? srcElement.parentNode.getAttribute("name") : eventName;//兼容element-ui 组合标签
        switch (eventName) {
          case"submit":
            this.onSubmit();
            break;
          case"add":
            this.onAdd();
            break;
          default:
            // this.onClickDefault(eventName);
            break;
        }
      },
      onSubmit(){
        this.$store.dispatch(this.currentTable+"/getItems");
      },
      handleAdd(){
        this.$store.dispatch(this.currentTable+'/topHandler',"add");
      },
      //批量删除
      handleDeletes(){
        if(!this.tableSelected||this.tableSelected.length==0){
          this.$message({
            showClose: false,
            message: '请您务必选择一条记录',
            type: 'warning'
          });
        }else{
          this.handleConfirm(
            '此操作将永久删除该数据, 是否继续?',
            "提示",
            {confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'},
            this.onDelete);
        }
      },
      handleConfirm(message="删除",title="提示",button={confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'},callBack=this.onDelete){
        this.$confirm(message,title,button,callBack).then(() => {
          callBack();
        }).catch(() => {
          this.message('info',"已取消删除");
        });
      },
      onDelete(){
        let msg = this.tableSelected.join(",");
        this.$notify({
          title: '提示',
          message: '您要删除的列是id='+ msg + "我们抱歉的通知您，当前不支持此操作",
          duration: 0
        });
        //this.$store.dispatch(this.currentTable + "/deletes")
      }
    },
    beforeCreate(){
      this.$store.dispatch("reSetSate","userManager");
      Util.setItem("currentVue",{vue:"userManager"});
    },
    create(){
      console.log(2)
    }
  }
</script>

<style lang="scss" type="text/css">
  @import "static/scss/container";
  .container{
    .title{
      border-left:4px solid rgba(74,144,226,1);
      height:18px;
      font-size:18px;
      font-family:SourceHanSansCN-Medium;
      font-weight:500;
      color:rgba(51,51,51,1);
      line-height: 16px;
      padding-left: 10px;
    }
    .searchArea{
      display: flex;
      justify-content: space-between;
      .searchInput>div{
        display: inline-block;
        margin-right: 30px;
        padding: 18px 4px;
        .el-input {
            width: auto!important;
        }
      }
      .rightBtn{
        align-items: center;
        display: flex;
        button{
          border:none!important;
        }
      }
    }
    .el-card{
      height: calc(100% - 70px);
    }
  }
</style>
