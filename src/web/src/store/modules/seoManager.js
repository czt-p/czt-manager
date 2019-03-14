import * as types from '../mutation-types'
import Util from 'framework/util/util'
import XHR from 'framework/xhr/xhr'
import { MessageBox } from 'element-ui';
import SeoList from "src/collection/SeoList"

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
        {property: "pageName", label: "页面名称"},
        {property: "title", label: "Title"},
        {property: "description", label: "Description"},
        {property: "keywords", label: "Keywords"},
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
    seoConfigs: "seoConfigs", 
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
   _default: {
     // name: "",
     // desc: "",
     // menus: []
   },
  dialog:{
    _default: {
      title: "",
      visible: false,
      template: ""
    },
    change: {
      title: "修改",
      visible: false,
      width: "75%",
      template: "changeRole"
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
  metaInfo: '', //核算详情
  form:'',
}

// getters 对数据进行格式化
const getters = {
  metaInfo: (state) => state.metaInfo,
  result: state =>state.result,
  sendInfo: state => state.sendInfo,
}
const actions = {
  getItems({commit, state}) {
    XHR.get({
      url: state.url.seoConfigs, ///accountRecords批量查询评测记录操作， 支持分页查询
    }, function (data) {
      let parse = {
        type: state.url.seoConfig,
        data: data
      };
      data.success ? commit(types[state.actions.items], parse) : commit(types.ERROR, parse);
    })
  },
  changeItem({state,commit}){
      state.form['seoForm'].validate((valid) => {
        if(!valid) return false;
        let params = {seoConfigs : [
          {
            meta:[
              {name:'keywords',content:state.sendInfo.keywords},
              {name:'description',content:state.sendInfo.description},
            ],
            pageCode: state.sendInfo.pageCode,
            title: state.sendInfo.title
          }
        ]}
        XHR.form({
          url: state.url.seoConfigs,
          method: "PUT",
          data: params,
        }, function (data) {
          data.success ? commit(types.SUCCESS, {type:"change",data:data}) : commit(types.ERROR, {type:"change",data:data});
        })
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
    let result = data.data.data;
    let List = new SeoList(result);
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
  [types[state.actions.change]](state) {
    state.currentDialog= Object.assign({},state.dialog.change,{visible:true});
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
