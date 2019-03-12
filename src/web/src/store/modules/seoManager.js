import * as types from '../mutation-types'
import Util from 'framework/util/util'
import XHR from 'framework/xhr/xhr'
import { MessageBox } from 'element-ui';
import costingsList from "src/collection/costing"

const state = {
  name: "seoManager",
  param: {
    accountTime: "",
    companyName: "",
    telephone: "",
    query: ["accountTime", "companyName", "telephone"],
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
        {property: "companyName", label: "公司名称"},
        {property: "consultCost", label: "咨询费用（元）"},
        {property: "ipCost", label: "知识产权（元）"},
        {property: "annualAuditCost", label: "年度审计报告（元）"},
        {property: "specialAuditCost", label: "高薪专项审计（元）"},
        {property: "otherCost", label: "其他费用（元）"},
        {property: "totalCost", label: "总费用（元）"},
        {property: "telephone", label: "联系方式"},
        {property: "accountTime", label: "时间"},
      ],
      deals: {
        max:2
      },
    }
  },
  pageData: {
    
  },
  tableData: [],
  currentDialog:{},
  url: {
    seoConfig: "seoConfig", 
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
    view:{
      title: "查看核算记录详情",
      visible:false,
      width:"80%",
      template:"viewRole"
    }
  },
  template:{
    addRole:"addRole",
    changeRole:"changeRole",
    viewRole: "viewRole"
  },
  sendInfo:{
  
  },
  result: {},
  metaInfo: '' //核算详情
}

// getters 对数据进行格式化
const getters = {
  metaInfo: (state) => state.metaInfo,
  result: state =>state.result,
}
const actions = {
  getConfigs({commit, state}) {
    XHR.get({
      url: state.url.seoConfig, ///accountRecords批量查询评测记录操作， 支持分页查询
    }, function (data) {
      let parse = {
        type: state.url.seoConfig,
        data: data
      };
      data.success ? commit(types[state.actions.items], parse) : commit(types.ERROR, parse);
    })
  },
  saveConfigs({state,commit},data){
    console.log('saveConfig',data)
    XHR.form({
      url: state.url.seoConfig,
      method: "PUT",
      data:data,
    }, function (data) {
      data.success ? commit(types.SUCCESS, {type:"change",data:data}) : commit(types.ERROR, {type:"change",data:data});
    })
  },
  openDialog({state,commit},type){
    commit(types[state.actions.add]);
  },
  openViewDialog({state,commit},data){
    commit(types[state.actions.current], data);
    XHR.restfulMiddle({
      url: state.url.costDetail,
      method: "GET",
      think: {id: data.id},
    }, function (data) {
      // console.log('data',data)
      state.costDetail = data.data;
    })
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
  }
}
const mutations = {
  //查
  [types[state.actions.items]](state, data) {
    const result = data;
    // console.log('metaInfo', data);
    state.metaInfo = data.data.data;
  },
  //修改参数
  [types[state.actions.param]](state, param) {
    state.param = Object.assign({}, state.param, param);
  },
  [types[state.actions.view]](state) {
    state.currentDialog= Object.assign({},state.dialog.view,{visible:true});
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
