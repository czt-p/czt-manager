<template>
  <div class="container">
    <div class="title">政策管理</div>
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
             @visible-change='valideRegion'
             @change="handleChange"
            :props="props">
          </el-cascader>
        </div>
        <div class="addRole questionName">
          <span class="title">政策名称：</span><el-input size='small' v-model="addObj.name" placeholder="请输入政策名称"></el-input>
        </div>
        <div class="addRole questionName">
          <span class="title">政策内容：</span>
          <!-- <el-input size='small' v-model="addObj.content" placeholder="请输入政策内容">
          </el-input> -->
          <div class="edit_container">
            <quill-editor 
                class="editor"
                v-model="addObj.content" 
                ref="myQuillEditor" 
                :options="editorOption" 
                @blur="onEditorBlur($event)" @focus="onEditorFocus($event)"
                @change="onEditorChange($event)">
            </quill-editor>
        </div>
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
          <span class="title">政策内容：</span>
          <!-- <el-input size='small' v-model="sendInfo.content" placeholder="请输入政策内容"></el-input> -->
          <div class="edit_container">
            <quill-editor 
                class="editor"
                v-model="sendInfo.content" 
                ref="myQuillEditor" 
                :options="editorOption" 
                @blur="onEditorBlur($event)" @focus="onEditorFocus($event)"
                @change="onEditorChange($event)">
            </quill-editor>
        </div>
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
          <span class="title">政策内容：</span>
          <!-- <span class='content' v-html='sendInfo.content'></span> -->
          <div class="edit_container">
            <quill-editor 
                class="editor"
                v-model="sendInfo.content" 
                ref="myQuillEditor" 
                :options="editorOption2" 
                @blur="onEditorBlur($event)" @focus="onEditorFocus2($event)"
                @change="onEditorChange($event)">
            </quill-editor>
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
  import { quillEditor } from "vue-quill-editor"; //调用编辑器
  import 'quill/dist/quill.core.css';
  import 'quill/dist/quill.snow.css';
  import 'quill/dist/quill.bubble.css';
  import { MessageBox } from 'element-ui';
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
        editorOption2:{
          enable:false,
           modules:{
              toolbar:[]
           },
           theme: 'snow'
        },
        editorOption: {
          placeholder: "",
          theme: "snow", // or 'bubble'
          placeholder: "请输入内容",
          modules:{
              toolbar:[
                  ["bold", "italic", "underline", "strike"], // 加粗 斜体 下划线 删除线
                  ["blockquote", "code-block"], // 引用  代码块
                  [{ header: 1 }, { header: 2 }], // 1、2 级标题
                  [{ list: "ordered" }, { list: "bullet" }], // 有序、无序列表
                  [{ script: "sub" }, { script: "super" }], // 上标/下标
                  [{ indent: "-1" }, { indent: "+1" }], // 缩进
                  // [{'direction': 'rtl'}],                         // 文本方向
                  [{ size: ["small", false, "large", "huge"] }], // 字体大小
                  [{ header: [1, 2, 3, 4, 5, 6, false] }], // 标题
                  [{ color: [] }, { background: [] }], // 字体颜色、字体背景颜色
                  [{ font: [] }], // 字体种类
                  [{ align: [] }], // 对齐方式
                  ["clean"], // 清除文本格式
                  ["link"] // 链接、图片、视频
              ]
          }
        }
      }
    },
    components: {
      TableVue,
      DialogVue,
      quillEditor,
    },
    computed: {
      ...mapGetters({
        setState: "setState",
      }),
      ...mapGetters('subsidizeManager',{
        addObj: "addObj",
        sendInfo: "sendInfo",
      }),
      editor() {
          return this.$refs.myQuillEditor.quill;
      },
    },
    methods:{
      valideRegion(val){
        console.log('val',val,this.addObj.area)
        if(!val && this.addObj.area.length>0){
          let sendData = {
            queryString:[
              "regionCode~like~%"+this.addObj.area[this.addObj.area.length-1]+ "%",
            ]
              
          }
          // console.log('param',param)
          XHR.ajaxGetForArray({
            url: 'subsidyPolicies',
            data: sendData
          }, function (data) {
            
            console.log('valideRegion',data.data.content)
            if (data.data.content.length>0) {
              MessageBox.alert('此地区已存在资助政策，不可重复添加！')
              // commit([types.SUCCESS],{type:'change',dat:{msg:''}})
            }
          })
        }
      },
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
      },
      onEditorReady(editor) { // 准备编辑器
 
      },
      onEditorBlur(){}, // 失去焦点事件
      onEditorFocus(){}, // 获得焦点事件
      onEditorFocus2(editor){
        // console.log('edit',editor)
        editor.enable(false); 
      }, // 获得焦点事件
      onEditorChange(){}, // 内容改变事件
    },
    beforeCreate(){
      this.$store.dispatch("reSetSate","subsidizeManager");
      Util.setItem("currentVue",{vue:"subsidizeManager"});
    },
    mounted(){
      // console.log('addObj',this.addObj)
      this.initArea();
    }
  }
</script>

<style lang="scss" >
  @import "static/scss/container";
  @import "./edit.scss";
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
          .el-cascader{
            // line-height:0!important;
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
        input{
          width:160px;
        }
    }
    .questionName{
      input{
          width:160px;
        }
    }
    .el-date-editor{
      input{
        width:120px;
      }
    }
    .el-date-editor--datetimerange.el-input__inner {
        width: 360px;
    }
    .el-card{
      height: calc(100% - 90px);
    }
  }
</style>
