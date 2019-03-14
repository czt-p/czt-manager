<template>
  <div class='container'>
    <div class="title">SEO管理</div>
    <TableVue v-bind:currentTable="currentTable"></TableVue>
    <DialogVue>
      <template slot="changeRole">
        <el-form :label-position="labelPosition" label-width="100px" :model="sendInfo" style='padding:25px;' :rules='rules' ref='seoForm'>
          <el-form-item label="Title：" prop="title">
            <el-input v-model="sendInfo.title"></el-input>
          </el-form-item>
          <el-form-item label="Description：" prop="description">
            <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 8}" v-model="sendInfo.description"></el-input>
          </el-form-item>
          <el-form-item label="Keywords：" prop="keywords">
            <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 8}" v-model="sendInfo.keywords"></el-input>
          </el-form-item>
        </el-form>
        <!-- <el-button type="primary" style='margin-left: 125px;' @click='saveConfig'>保存</el-button> -->
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
export default {
  name: 'seoManager',
  data () {
    return {
      currentTable:"seoManager",
      labelPosition: 'left',
      formLabelAlign: {
        title: '',
        description: '',
        keywords: ''
      },
      rules:{
        title:[
          {required:true,message:'请输入title',trigger:'blur'}
        ],
        description:[
          {required:true,message:'请输入description',trigger:'blur'}
        ],
        keywords:[
          {required:true,message:'请输入keywords',trigger:'blur'}
        ]
      }
    }
  },
  components:{
    TableVue,
    DialogVue,
  },
  computed: {
     ...mapGetters('seoManager',{
      //  metaInfo:'metaInfo',
      //  result:'result',
       sendInfo:'sendInfo',
     })
  },
  methods: {
    // saveConfig(){
    //   let params = {
    //     meta:[
    //       {"name":'description',"content":this.formLabelAlign.description},
    //       {"name":'keywords',"content":this.formLabelAlign.keywords}
    //     ],
    //     title:this.formLabelAlign.title
    //   }
    //   this.$refs['formLabelAlign'].validate((valid)=>{
    //     if(valid){
    //       this.$store.dispatch('seoManager/saveConfigs',params);
    //     }
    //   })
    // },
    // success(type,data){
    //     switch(type){
    //       case"delete":
    //       case"add":
    //       case"change":
    //         this.message('success',data.msg);
    //         break;
    //       default:
    //         break;
    //     }
    //   },
    //   error(data){
    //     this.message('warning',data.msg);
    //   },
    //   message(type,msg){
    //     this.$message({
    //       type: type,
    //       message:msg,
    //       duration:"3000"
    //     });
    //   }
  },
  beforeCreate(){
    this.$store.dispatch("reSetSate","seoManager");
    Util.setItem("currentVue",{vue:"seoManager"});
    // this.$store.dispatch('seoManager'+'/getConfigs')
  },
  watch:{
    // metaInfo(n,o){
    //   if(n){
    //     this.formLabelAlign.title = n.title;
    //     n.meta.map(x=>{
    //       if(x.name == 'description'){
    //         this.formLabelAlign.description = x.content;
    //       }else{
    //         this.formLabelAlign.keywords = x.content;
    //       }
    //     })
    //     this.formLabelAlign.title = n.title;
    //   }

    // },
    // result:{
    //   handler:function({type,data}){
    //     data.success ? this.success(type,data) : this.error(data);
    //   },
    //   deep:true//对象内部的属性监听，也叫深度监听
    // },
    // sendInfo:{
    //   handler:function(val){
    //     console.log('val',val);
    //     if(val){
    //       val.description ?this.formLabelAlign.description = val.description:'';
    //       val.keywords ?this.formLabelAlign.keywords = val.keywords:'';
    //       val.title ?this.formLabelAlign.title = val.title:'';
    //     }
    //   },
    //   deep:true//对象内部的属性监听，也叫深度监听
    // },
  },
  mounted(){
    // console.log('this.$store',this.$store);
    // console.log('metaInfo',this.metaInfo)
    this.$nextTick(()=>{
      this.$store.state['seoManager'].form = this.$refs;
      // console.log('this.$store.state',this.$store.state['seoManager']);
    })
  },
}
</script>

<style lang='scss'>
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
      margin: 30px 0;
    }
    .info{
      height: 50px;
      line-height: 0px;
      padding: 25px;
      margin: 15px;
      box-sizing: border-box;
      background: #f0f7fc;
      color: #0199cc;
    }
  }
</style>
