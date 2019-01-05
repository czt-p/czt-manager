<template>
  <div class="container">
    <TableVue v-bind:currentTable="currentTable">
      <template slot="caption">
        <div class="clearfix">
          <span class='title'>角色列表</span>
          <el-button v-for="(deal,index) in options.tops"
                     :type="deal.type"
                     size="small"
                     :key='index'
                     icon="el-icon-plus"
                     style="float: right"
                     @click.native="onTopClickHandler(deal.event)"
                     :name="deal.event">
            {{deal.text}}
          </el-button>
        </div>
      </template>
    </TableVue>
    <DialogVue>
      <template slot="addRole">
        <AddVue></AddVue>
      </template>
      <template slot="changeRole">
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
  import AddVue from './children/add'
  import ChangeVue from './children/change'

  export default {
    name: 'roleManager',
    data(){
      return{
        currentTable:"roleManager",
      }
    },
    components: {
      TableVue,
      DialogVue,
      AddVue,
      ChangeVue
    },
    computed: {
      ...mapGetters({
        setState: "setState",
        options:"options",
        selectItems:"selectItems"
      }),
    },
    methods:{
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
      handleAdd(){
        this.$store.dispatch(this.currentTable+'/topHandler',"add");
      },
      //批量删除
      handleDeletes(){
        if(this.selectItems.length==0){
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
    },
    beforeCreate(){
      this.$store.dispatch("reSetSate","roleManager");
      Util.setItem("currentVue",{vue:"roleManager"});
    },
    created(){
      // console.log(2)
    }
  }
</script>

<style lang="scss" type="text/css">
  @import "static/scss/container";
  .container{
    .title{
      border-left:4px solid rgba(74,144,226,1);
      height:18px;
      display: inline-block;
      font-size:18px;
      font-family:SourceHanSansCN-Medium;
      font-weight:500;
      color:rgba(51,51,51,1);
      line-height: 16px;
      padding-left: 10px;
    }
    .el-card{
      height: calc(100% - 60px);
    }
    .clearfix{
      button{
        background:#40AB9B;
      }
    }
  }
</style>
