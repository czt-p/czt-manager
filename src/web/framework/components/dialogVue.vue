<template>
  <el-dialog
    class="dialog-container"
    :title="currentDialog.title"
    :visible.sync="currentDialog.visible"
    :width="currentDialog.width"
    :modal-append-to-body="true"
    :close-on-click-modal='false'
    :append-to-body="true"
    @open='open'
    @opened='opened'
    :before-close="handleClose">
       <slot :name="currentDialog.template"></slot>
      <span slot="footer" v-if='currentDialog.template!="viewRole"'>
        <el-button @click="handleClose" size="mini">取 消</el-button>
        <el-button type="primary" @click.native="sure" size="mini">确 定</el-button>
      </span>
  </el-dialog>
</template>

<script>
  import { mapGetters, mapActions } from 'vuex'
  import Util from "framework/util/util"
  import { addQuillTitle } from 'framework/util/quill-title.js'
  export default {
    data(){
      return{
        flag:{
          sure:"SURE",
          close:"CLOSE",
          event:'dialogEvent'
        }
      }
    },
    components:{

    },
    computed: {
      ...mapGetters({
         currentDialog: "resentDialog",
         addObj: "addObj",
      }),
    },
    methods: {
      handleClose() {
        this.dispatchEvent(this.flag.close);
      },
      sure(){
        this.dispatchEvent(this.flag.sure)
      },
      dispatchEvent(flag){
        this.$store.dispatch(this.flag.event,{
          type:flag,
          dialog:this.currentDialog
        });
      },
      open(){
        
        if(this.currentDialog.title === '查看核算记录详情'){
          console.log('sss',document.querySelector('.el-dialog__title'))
          let spanTitle = document.querySelector('.el-dialog__title');
          spanTitle?spanTitle.className = '.el-dialog__title costingView':'';
        }
        for (const key in this.addObj) {
          this.addObj[key] = this.addObj[key] instanceof Array?[]:'';
        }
        
      },
      opened(){
        this.$nextTick(()=>{
          addQuillTitle();
        })
      }
    },
    mounted(){
    
    }
  };
</script>
<style lang="scss">
  @import "framework/scss/dialog";
  .addRole,.changeRole{
    padding:20px!important;
    .title{
      display: inline-block;
      width: 100px;
    }
    .content{
      display: inline-block;
      width: 80%;
      vertical-align: text-top;
    }
    .el-input{
      width: 80%!important;
    }
    .el-cascader {
      width: 80%!important;
      .el-input{
        width:100%!important;
        .el-input__inner{
          height:32px;
          line-height: 32px!important;
        }
      }
    }
  }
  .part{
    padding-bottom: 10px;
    border-bottom: 1px solid rgba(204,204,204,1);
    .title{
      padding: 15px 0px;
      font-size:18px;
      font-family:SourceHanSansCN-Medium;
      font-weight:500;
      color:rgba(51,51,51,1);
      line-height:27px;
    }
    .pContent{
      padding-left: 25px;
      .viewRole{
        padding:5px;
        .lC{
          display: inline-block;
          width: 360px;
        }
        .mC{
          display: inline-block;
          width: 200px;
        }
      }
    }
  }
  .part:nth-last-child(1){
    border-bottom:none;
  }
  .costingView{
    border-left:4px solid rgba(74,144,226,1);
    padding-left:10px;
    font-size:18px;
    font-family:SourceHanSansCN-Medium;
    font-weight:500;
    color:rgba(51,51,51,1);
  }
</style>
