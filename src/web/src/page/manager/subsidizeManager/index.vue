<template>
  <div class="container">
    <div class="title">资助管理</div>
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
          政策所属地区：
          <el-cascader
            :options="areas"
             v-model="searchObj.searchArea"
             change-on-select
             clearable
             @change="handleChange"
            :props="props">
          </el-cascader>
        </div>
        <div class="questionName">
          政策名称：<el-input @keyup.enter.native="searchTable" size='small' v-model="searchObj.searchName" placeholder="请输入政策名称"></el-input>
        </div>
      </div>
      <div class="rightBtn">
        <el-button type="primary" icon="el-icon-search" size="small" @click='searchTable'>开始搜索</el-button>
        <el-button type="primary" icon="el-icon-plus" size="small" @click='handleAdd' style='background:#40AB9B'>新增</el-button>
      </div>
    </div>
    <TableVue v-bind:currentTable="currentTable"></TableVue>
    <DialogVue>
      <template slot="addRole">
        <div class="addRole askName">
          <span class="title">政策所属地区：</span>
          <el-cascader
            :options="areas"
             v-model="addObj.area"
             change-on-select
             clearable
             @change="handleChange"
            :props="props">
          </el-cascader>
        </div>
        <div class="addRole questionName">
          <span class="title">政策名称：</span><el-input size='small' v-model="addObj.name" placeholder="请输入政策名称"></el-input>
        </div>
        <div class="addRole questionName">
          <span class="title">政策内容：</span><el-input size='small' v-model="addObj.content" placeholder="请输入政策内容"></el-input>
        </div>
      </template>
      <template slot="changeRole">
        <div class="changeRole askName">
          <span class="title">政策所属地区：</span>
          <el-cascader
            :options="areas"
             v-model="sendInfo.area"
             change-on-select
             clearable
             @change="handleChange"
            :props="props">
          </el-cascader>
        </div>
        <div class="changeRole questionName">
          <span class="title">政策名称：</span><el-input size='small' v-model="sendInfo.name" placeholder="请输入政策名称"></el-input>
        </div>
        <div class="changeRole questionName">
          <span class="title">政策内容：</span><el-input size='small' v-model="sendInfo.content" placeholder="请输入政策内容"></el-input>
        </div>
      </template>
      <template slot="viewRole">
        <div class="changeRole askName">
          <span class="title">政策所属地区：</span>
          <el-cascader
            disabled
            :options="areas"
             v-model="sendInfo.area"
             change-on-select
             clearable
             @change="handleChange"
            :props="props">
          </el-cascader>
        </div>
        <div class="changeRole questionName">
          <span class="title">政策名称：</span><span class='content'>{{sendInfo.name}}</span>
        </div>
        <div class="changeRole  questionName">
          <span class="title">政策内容：</span><span class='content'>{{sendInfo.content}}</span>
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
    name: 'subsidizeManager',
    data(){
      return{
        currentTable:"subsidizeManager",
        searchObj:{
          searchTime:'',
          searchArea:[],
          searchName:'',
        },

        areas:[],//全国区划列表
        props: {
          value: 'code',
          label:'name',
        },
        // v1:["330000", "330300", "330304"]
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
      ...mapGetters('subsidizeManager',{
        addObj: "addObj",
        sendInfo: "sendInfo",
      }),
    },
    methods:{
      searchTable(){
        let param = {
          modifyTime: this.searchObj.searchTime, //{"question":"title","answer":"test","startDate":1545713393497,"endDate":1545713393497}
          regionCode:this.searchObj.searchArea,
          name: this.searchObj.searchName,
        }
        this.$store.dispatch(this.currentTable+'/paramChange',param);
      },
      handleAdd(){
        this.$store.dispatch(this.currentTable+'/openDialog',"add");
      },
      handleChange(value) {
        console.log(value);
      },
      initArea(){
        XHR.get({
          url:'cascade',
        },res=>{
          console.log('initArea',res)
          this.areas = res.success? res.data:[];
        })
      }
    },
    beforeCreate(){
      this.$store.dispatch("reSetSate","subsidizeManager");
      Util.setItem("currentVue",{vue:"subsidizeManager"});
    },
    mounted(){
      // console.log('sendInfo',this.sendInfo)
      this.initArea();
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
      .searchInput{
        display: flex;
        justify-content: space-between;
        &>div{
          display: inline-block;
          margin-right: 30px;
          padding: 18px 4px;
          .el-input {
            vertical-align: middle;
            width: auto!important;
          }
          .el-cascader{
            line-height:0!important;
            .el-input__prefix, .el-input__suffix{
              top:-3px;
            }
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
    .el-cascader {
        .el-input__inner{
          height:32px;
          line-height: 32px!important;
        }
    }
    .el-card{
      height: calc(100% - 90px);
    }
  }
</style>
