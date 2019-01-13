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
        // console.log('jjjjjjjjjjjjjjjjjjjjjjjjjjjjj==========')
        for (const key in this.addObj) {
          this.addObj[key] = this.addObj[key] instanceof Array?[]:'';
        }
      }
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
</style>
