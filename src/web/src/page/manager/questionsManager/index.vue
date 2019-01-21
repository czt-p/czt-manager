<template>
  <div class="container">
    <div class="title">问答管理</div>
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
          问答名称：<el-input @keyup.enter.native="searchTable" size='small' v-model="searchObj.askName" placeholder="请输入问答名称"></el-input>
        </div>
        <div class="questionName">
          问题解答：<el-input @keyup.enter.native="searchTable" size='small' v-model="searchObj.answers" placeholder="请输入答案"></el-input>
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
          问答名称：<el-input size='small' v-model="addObj.question" placeholder="请输入问答名称"></el-input>
        </div>
        <div class="addRole questionName">
          问题解答：
          <!-- <el-input size='small' v-model="addObj.answer" placeholder="请输入答案"></el-input> -->
          <div class="edit_container">
            <quill-editor 
                class="editor"
                v-model="addObj.answer" 
                ref="myQuillEditor" 
                :options="editorOption" 
                >
            </quill-editor>
          </div>
        </div>
      </template>
      <template slot="changeRole">
        <div class="changeRole askName">
          问答名称：<el-input size='small' v-model="sendInfo.question" placeholder="请输入问答名称"></el-input>
        </div>
        <div class="changeRole questionName">
          问题解答：
          <!-- <el-input size='small' v-model="sendInfo.answer" placeholder="请输入答案"></el-input> -->
          <div class="edit_container">
            <quill-editor 
                class="editor"
                v-model="sendInfo.answer" 
                ref="myQuillEditor" 
                :options="editorOption" 
                >
            </quill-editor>
          </div>
        </div>
      </template>
      <template slot="viewRole">
        <div class="changeRole askName">
          问答名称：<span>{{sendInfo.question}}</span>
        </div>
        <div class="changeRole questionName">
          问题解答：
          <!-- <span>{{sendInfo.answer}}</span> -->
          <div class="edit_container">
            <quill-editor 
                class="editor"
                v-model="sendInfo.answer" 
                ref="myQuillEditor" 
                :options="editorOption2" 
                @focus="onEditorFocus($event)"
                >
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

  export default {
    name: 'questionsManager',
    data(){
      return{
        currentTable:"questionsManager",
        searchObj:{
          searchTime:'',
          askName:'',
          answers:'',
        },
        value11:"",
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
        },
        editorOption2: {
          placeholder: "",
          theme: "snow", // or 'bubble'
          placeholder: "请输入内容",
          modules:{
              toolbar:[
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
      ...mapGetters('questionsManager',{
        addObj: "addObj",
        sendInfo: "sendInfo",
      }),
    },
    methods:{
      searchTable(){
        let param = {
          question: this.searchObj.askName, //{"question":"title","answer":"test","startDate":1545713393497,"endDate":1545713393497}
          answer:this.searchObj.answers,
          startDate: this.searchObj.searchTime.length>0?this.searchObj.searchTime[0]:'',
          endDate: this.searchObj.searchTime.length>0?this.searchObj.searchTime[1]:'',
        }
        this.$store.dispatch(this.currentTable+'/paramChange',param);
      },
      handleAdd(){
        this.$store.dispatch(this.currentTable+'/openDialog',"add");
      },
      onEditorFocus(editor){
        // console.log('edit',editor)
        editor.enable(false); 
      }, 
    },
    beforeCreate(){
      this.$store.dispatch("reSetSate","questionsManager");
      Util.setItem("currentVue",{vue:"questionsManager"});
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
    }
  }
</style>
