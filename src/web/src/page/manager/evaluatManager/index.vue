<template>
  <div class="container">
    <div class="title">评测管理</div>
    <div class="searchArea">
      <div class="searchInput">
        <div class="searchTime">
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
        </div>
        <div class="askName">
          公司名称：<el-input @keyup.enter.native="searchTable" size='small' v-model="searchObj.companyName" placeholder="请输入公司名称"></el-input>
        </div>
        <div class="questionName">
          联系方式：<el-input @keyup.enter.native="searchTable" size='small' v-model="searchObj.telephone" placeholder="请输入联系方式"></el-input>
        </div>
      </div>
      <div class="rightBtn">
        <el-button type="primary" icon="el-icon-search" size="small" @click='searchTable'>开始搜索</el-button>
        <!-- <el-button type="primary" icon="el-icon-plus" size="small" @click='handleAdd' style='background:#40AB9B'>新增</el-button> -->
      </div>
    </div>
    <TableVue v-bind:currentTable="currentTable"></TableVue>
    <DialogVue>
      <template slot="viewRole">
        <div class="detail">
            <div class="topDetail">
                <div class="companyName">公司名称：</div><span>{{detailEvaluat.companyName}}</span><br>
                <div class="companyName">手机号码：</div><span>{{detailEvaluat.telephone}}</span><br>
                <div class="companyName">评分分数：</div><span>{{detailEvaluat.score}}</span><br>
                <div class="companyName">评测结果：</div><span>{{detailEvaluat.result}}</span><br>
            </div>
            <div class="contentDetail">
                <el-tabs v-model="activeName" @tab-click="handleClick">
                    <el-tab-pane label="基础评估" name="first" v-if='detailEvaluat && detailEvaluat.details[0]'>
                        <div class="questionsList" v-for='(item,index) in detailEvaluat.details[0].questions' :key='index' >
                            <div>{{`${index+1}、${item.content}`}}</div>
                            <el-radio-group v-model="item.answer">
                                <el-radio disabled :label="x.id" v-for='(x,index) in item.options' :key='index' :class="[item.options.length>2 && x.content.length>30?'moreOptions':'']">{{x.content}}</el-radio>
                            </el-radio-group>
                        </div>
                    </el-tab-pane >
                    <el-tab-pane label="高新评分" name="second" v-if='detailEvaluat && detailEvaluat.details[1]'>
                         <div class="questionsList" v-for='(item,index) in detailEvaluat.details[1].questions' :key='index' >
                            <div>{{`${index+1}、${item.content}`}}</div>
                            <el-radio-group v-model="item.answer">
                                <el-radio disabled :label="x.id" v-for='(x,index) in item.options' :key='index' :class="[item.options.length>2 && x.content.length>30?'moreOptions':'']">{{x.content}}</el-radio>
                            </el-radio-group>
                        </div>
                    </el-tab-pane>
                </el-tabs>
            </div>
        </div>
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
    name: 'evaluatManager',
    data(){
      return{
        currentTable:"evaluatManager",
        searchObj:{
          searchTime:'',
          companyName:'',
          phone:'',
        },
        activeName: 'first'
      }
    },
    components: {
      TableVue,
      DialogVue,
    },
    computed: {
      ...mapGetters({
        setState: "setState",
      }),
      ...mapGetters('evaluatManager',{
        detailEvaluat: "detailEvaluat",
        sendInfo: "sendInfo",
      }),
    },
    methods:{
      handleClick(tab, event) {
        // console.log(tab, event);
      },
      searchTable(){
        let param = {
          companyName: this.searchObj.companyName, //{"question":"title","answer":"test","startDate":1545713393497,"endDate":1545713393497}
          telephone:this.searchObj.telephone,
          examTime: this.searchObj.searchTime&&this.searchObj.searchTime.length>0?[this.searchObj.searchTime[0],this.searchObj.searchTime[1]]:[],
        }
        this.$store.dispatch(this.currentTable+'/paramChange',param);
      },
    //   handleAdd(){
    //     this.$store.dispatch(this.currentTable+'/openDialog',"add");
    //   },
    },
    beforeCreate(){
      this.$store.dispatch("reSetSate","evaluatManager");
      Util.setItem("currentVue",{vue:"evaluatManager"});
    },
    watch:{
        // detailEvaluat:(val)=>{
        //     console.log('val',val)
        //     val.details[0].questions[0].options.push({id:3,content:'hahahah'})
        // }
    },
    mounted(){
      // console.log('sendInfo',this.sendInfo)
    }
  }
</script>

<style lang="scss" >
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
      align-items: center;
      .searchInput{
        display: flex;
        justify-content: space-between;
        align-items: center;
        &>div{
          display: inline-block;
          margin-right: 30px;
          padding: 18px 4px;
          .el-input {
            vertical-align: middle;
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
    }
    .el-card{
      height: calc(100% - 90px);
      // overflow: auto!important;
    }
  }
  .el-dialog{
    background:#f7f7f7!important;
    .topDetail,.contentDetail{
        background: rgba(255,255,255,1);
        box-shadow: 0px 4px 10px 0px rgba(0,0,0,0.1);
        padding: 20px;
        line-height: 30px;
    }
    .topDetail{
        margin-bottom: 20px;
        div{
            font-size:13px;
            font-family:SourceHanSansCN-Medium;
            font-weight:500;
            color:rgba(78,78,78,1);
            display: inline-block;
        }
    }
    .contentDetail{
        .el-radio-group{
            padding-left:23px;
            .el-radio{
                margin-right:40px!important;
            }
            .moreOptions{
                display: block;
                margin-bottom: 10px;    
            }
            .el-radio+.el-radio{
                margin-left: 0!important;
            }
        }
    }
}
.el-radio__input.is-disabled+span.el-radio__label {
    white-space: pre-wrap;
}
</style>
