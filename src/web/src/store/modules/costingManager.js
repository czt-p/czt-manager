import * as types from '../mutation-types'
import Util from 'framework/util/util'
import XHR from 'framework/xhr/xhr'
import { MessageBox } from 'element-ui';
import costingsList from "src/collection/costing"

const state = {
  name: "costingManager",
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
    accountRecords: "accountRecords",
    costDetail: "costDetail", ///accountRecords/{id}根据id查询单个评测记录
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
  },//新增问题
  costDetail:''//核算详情
}

// getters 对数据进行格式化
const getters = {
  costDetail: (state) => state.costDetail,
  tableData:(state) =>state.tableData,
  id: state =>state.roleId,
  currentDialog:(state)=>state.currentDialog,
  sendInfo: state => state.sendInfo,
  result: state =>state.result,
}
function resetParam(params) {
  let resultParam = { ...params.page,
    queryString: []
  };
  if (params.query && params.query.length > 0) {
    params.query.map((param) => { //name,role
      if (params[param] && params[param] !== "") {
        if (param == 'accountTime') {
          resultParam.queryString.push(param + "~GE~" + params[param][0])
          resultParam.queryString.push(param + "~LE~" + params[param][1])
        }else {
          resultParam.queryString.push(param + "~like~%" + params[param] + "%")
        }
      }
    })
  }
  // console.log('params', params)
  return resultParam;
}
const actions = {
  getItems({commit, state}) {
    let param = resetParam(Object.assign({}, state.param));
    // console.log('param',param)
    XHR.ajaxGetForArray({
      url: state.url.accountRecords, ///accountRecords批量查询评测记录操作， 支持分页查询
      data: param
    }, function (data) {
      let parse = {
        type: state.url.accountRecords,
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
      console.log('data',data)
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
    let result = data.data.data.content;
    let List = new costingsList(result);
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
