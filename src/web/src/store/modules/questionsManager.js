import * as types from '../mutation-types'
import Util from 'framework/util/util'
import XHR from 'framework/xhr/xhr'
import { MessageBox } from 'element-ui';
import QuestionsList from "src/collection/questions"

const state = {
  name:"questionsManager",
  param: {
    question: "", //{"question":"title","answer":"test","startDate":1545713393497,"endDate":1545713393497}
    answer:"",
    startDate: "",
    endDate: "",
    query: ["question", "answer", "startDate", "endDate"],
    page: {
      pageIndex: "1",
      limit: 20,
      orderBy: ""
    }
  },
  options:{
    table: {
      type: "index",
      isPageNation: true,
      highlight: true,
      caption:"caption",
      th: [
        {property: "question", label: "问题名称"},
        {property: "answer", label: "解答",type:'html',width:'300px'},
        {property: "updatedTime", label: "更新时间",type:'time',width:'250px'},
      ],
      deals: {
        max:4
      },
      // tops: [
      //   {text: "新增", id: "btnAdd", name: "add", type: "primary", event: "add"}
      // ]
    }
  },
  pageData: {
    
  },
  tableData: [],
  currentDialog:{},
  
  url: {
    del: "delQuestions",
    change: "changeRole",
    highTechQuestions: "highTechQuestions",
    infoChange: "changeQuestion",
    add:"addQuestion",
  },
  actions:{
    param: "PARAM_CHANGE",
    add:"ROLE_ADD",
    view:"VIEW",
    change:"ROLE_CHANGE",
    addReset:"RESET_ADD",
    items:"ROLES_SUCCESS",
    close:"DIALOG_CLOSE",
    id:"ROLE_ID",
    current:"ROLE_CURRENT",
  },
  dialog:{
    _default:{
      title:"",
      visible:false,
      template:""
    },
    add:{
      title:"新增问答",
      visible:false,
      width: "75%",
      template:"addRole"
    },
    change:{
      title: "问答修改",
      visible:false,
      width: "75%",
      template:"changeRole"
    },
    view:{
      title: "问答查看",
      visible:false,
      width: "75%",
      template:"viewRole"
    }
  },
  template:{
    addRole:"addRole",
    changeRole:"changeRole",
    viewRole: "viewRole"
  },
  sendInfo:{
    // name: "",
    // desc: "",
    // menus: []
  },
  _default:{
    // name: "",
    // desc: "",
    // menus: []
  },
  result: {},
  flag: {},
  addObj:{
    question: '',
    answer: '',
  }//新增问题
}

// getters 对数据进行格式化
const getters = {
  addObj:(state)=>state.addObj,
  tableData:(state) =>state.tableData,
  id: state =>state.roleId,
  currentDialog:(state)=>state.currentDialog,
  // treeData:state => state.treeData,
  sendInfo: state => state.sendInfo,
  result: state =>state.result,
}

const actions = {
  getItems({commit, state}) {
    let param = Util.resetParamEq(Object.assign({}, state.param));
    // console.log('param',param)
    XHR.ajaxGetForArray({
      url: state.url.highTechQuestions,
      data: param
    }, function (data) {
      let parse = {
        type: state.url.highTechQuestions,
        data: data
      };
      data.success ? commit(types[state.actions.items], parse) : commit(types.ERROR, parse);
    })
  },
  pageChange({dispatch,commit,state},pageIndex){
    commit(types[state.actions.param],{page:{pageIndex:pageIndex,limit:20,orderBy:''}});
    dispatch("getItems");
  },
  paramChange({dispatch,commit,state},param){
    commit(types[state.actions.param],param);
    // console.log('paramChange',param)
    dispatch("getItems");
  },
  //新增
  addItem({commit, state}){
    if (state.addObj.answer && state.addObj.answer.trim() && state.addObj.question && state.addObj.question.trim()) {
      XHR.post({
        url:state.url.add,
        data:state.addObj,
      },function(data){
        data.success ? commit(types.SUCCESS,{type:"add",data:data}):commit(types.ERROR,{type:"add",data:data})
      })
    }else{
      if (!state.addObj.question) {
        MessageBox({
          title:'提示',
          message: '问答名称不能为空',
          type: 'warning'
        })
      } else if (!state.addObj.answer) {
        MessageBox({
          title: '提示',
          message: '问题解答不能为空',
          type: 'warning'
        })
      }
    }
  },
  changeItem({commit, state}){
    // console.log(state.sendInfo);
    XHR.restfulMiddle({
      url: state.url.infoChange,
      method: "PUT",
      think: {id: state.sendInfo.id},
      data:{answer:state.sendInfo.answer,question:state.sendInfo.question},
    }, function (data) {
      data.success ? commit(types.SUCCESS, {type:"change",data:data}) : commit(types.ERROR, {type:"change",data:data});
    })
  },
  deleteItem({commit, state}, data) {
    // console.log('deleteItem',data)
    XHR.restfulMiddle({
      url: state.url.del,
      method: "DELETE",
      think: {id: data[0]},
    }, function (data) {
      let parse = {
        type: "delete",
        data: data
      };
      data.success ? commit(types.SUCCESS, parse) : commit(types.ERROR, parse);
    })
  },
  openDialog({state,commit},type){
    commit(types[state.actions.add]);
  },
  openViewDialog({state,commit},data){
    commit(types[state.actions.current], data);
    commit(types[state.actions.view],data);
  },
  //弹出窗口
  dialogSure({dispatch, state},data){
    let {dialog} = data;
    // console.log('dialogSure',data)
    switch(dialog.template){
      case state.template.addRole:
        dispatch("addItem")
        break;
      case state.template.changeRole:
        dispatch("changeItem")
        break;
      default:
        break;
    }
  },
  dialogClose({commit}){
    commit(types[state.actions.close]);
  },
  //获取当前对象的数据
  getCurrentData({commit, state}, data) {
    commit(types[state.actions.current], data);
    commit(types[state.actions.change]);
  },
}
const mutations = {
  //查
  [types[state.actions.items]](state, data) {
    let result = data.data.data.content;
    let List = new QuestionsList(result);
    // console.log('types', List)
    state.tableData = List.getModels();
    if (data.data.data.pageIndex) {
      state.pageData = {
          limit: data.data.data.limit,
          pageIndex: data.data.data.pageIndex,
          total: data.data.data.total
      }
    } else {
      state.pageData = null;
    }
  },
  //修改参数
  [types[state.actions.param]](state, param) {
    state.param = Object.assign({}, state.param, param);
  },
  //改
  [types[state.actions.change]](state) {
    state.currentDialog= Object.assign({},state.dialog.change,{visible:true});
  },
  [types[state.actions.view]](state) {
    state.currentDialog= Object.assign({},state.dialog.view,{visible:true});
  },
  //增
  [types[state.actions.add]](state) {
    state.currentDialog= Object.assign({},state.dialog.add,{visible:true});
  },
  [types[state.actions.close]](state){
    state.currentDialog = Object.assign({},state.currentDialog,state.dialog._default);
    state.sendInfo = Object.assign({},state.sendInfo,state._default);
  },
  [types[state.actions.current]](state,data){
    state.sendInfo = Object.assign({},state.sendInfo,data)
    // state.currentRole = data;
  },
  //回调
  [types.SUCCESS](state, data) {
    state.result = data;
  },
  [types.ERROR](state, data) {
    state.result = data;
  }
}

export default {
  namespaced:true,
  state,
  getters,
  actions,
  mutations
}
