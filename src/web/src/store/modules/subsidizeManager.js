import * as types from '../mutation-types'
import Util from 'framework/util/util'
import XHR from 'framework/xhr/xhr'
import { MessageBox } from 'element-ui';
import SubsidizeList from "src/collection/subsidize"

const state = {
  name: "subsidizeListManager",
  param: {
    modifyTime: "", //{"question":"title","answer":"test","startDate":1545713393497,"endDate":1545713393497}
    regionCode:"",
    name:'',
    query: ["modifyTime", "regionCode", "name"],
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
        {property: "regionArea", label: "政策所属地区"},
        {property: "name", label: "政策名称"},
        {property: "modifyTime", label: "更新时间"},
      ],
      deals: {
        max:4
      },
    }
  },
  pageData: {
    
  },
  tableData: [],
  currentDialog:{},
  
  url: {
    del: "deleteSubside",
    change: "changeRole",
    subsidyPolicies: "subsidyPolicies",
    changeSubside: "changeSubside",
    add: "subsidyPolicies",
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
      title:"新增政策",
      visible:false,
      width:"654px",
      template:"addRole"
    },
    change:{
      title: "政策修改",
      visible:false,
      width:"654px",
      template:"changeRole"
    },
    view:{
      title: "政策查看",
      visible:false,
      width:"654px",
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
  addObj: {
    area: [],
    name: '',
    content: '',
  },
}

// getters 对数据进行格式化
const getters = {
  addObj:(state)=>state.addObj,
  tableData:(state) =>state.tableData,
  // id: state =>state.roleId,
  currentDialog:(state)=>state.currentDialog,
  sendInfo: state => state.sendInfo,
  result: state =>state.result,
}

function resetParam(params) {
  let resultParam = {...params.page, queryString: []};
  if (params.query && params.query.length > 0) {
    params.query.map((param) => { //name,role
      if (params[param] && params[param] !== "") {
        if (param == 'modifyTime'){
          resultParam.queryString.push(param + "~GE~" + params[param][0])
          resultParam.queryString.push(param + "~LE~" + params[param][1])
        } else if (param == 'regionCode' && params['regionCode'] && params['regionCode'].length > 0) {
          resultParam.queryString.push(param + "~like~%" + params[param][params[param].length - 1] + "%")
        }else{
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
      url: state.url.subsidyPolicies,
      data: param
    }, function (data) {
      let parse = {
        type: state.url.subsidyPolicies,
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
    if (state.addObj.area.length > 0 && state.addObj.name && state.addObj.name.trim() && state.addObj.content && state.addObj.content.trim()) {
      let sendData = {
        regionCode: state.addObj.area[state.addObj.area.length - 1],
        name: state.addObj.name,
        content: state.addObj.content,
      }
      XHR.post({
        url:state.url.add,
        data: sendData,
      },function(data){
        data.success ? commit(types.SUCCESS,{type:"add",data:data}):commit(types.ERROR,{type:"add",data:data})
      })
    }else{
      if (state.addObj.area.length<1) {
        MessageBox({
          title:'提示',
          message: '政策所属地区不能为空',
          type: 'warning'
        })
      } else if (!state.addObj.name) {
        MessageBox({
          title: '提示',
          message: '政策名称不能为空',
          type: 'warning'
        })
      }else if (!state.addObj.content) {
        MessageBox({
          title: '提示',
          message: '政策内容不能为空',
          type: 'warning'
        })
      }
    }
  },
  changeItem({commit, state}){
    // console.log(state.sendInfo);
    XHR.restfulMiddle({
      url: state.url.changeSubside,
      method: "PUT",
      think: {id: state.sendInfo.id},
      data:{regionCode:state.sendInfo.area[state.sendInfo.area.length-1],name:state.sendInfo.name,content:state.sendInfo.content},
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
    console.log('dialogSure',data)
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
    let List = new SubsidizeList(result);
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
    // console.log('state.param', state.param )
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
    let temp = Object.assign({},state.sendInfo,data);
    // temp.region = temp.region.parent?[temp.region.parent.substring(0,2)+'0000',temp.region.code]
    if (temp.region.parent){
      if(temp.region.code.startsWith('11') || temp.region.code.startsWith('12') || temp.region.code.startsWith('31') || temp.region.code.startsWith('50')){
        temp.area = [temp.region.parent, temp.region.code];//四个直辖市
      }else{
        temp.area = [temp.region.parent.substring(0, 2) + '0000', temp.region.parent, temp.region.code];
      }
    }
    state.sendInfo = temp;
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

