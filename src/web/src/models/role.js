/**
 * @param a means b
 * Created by Administrator on 2016/12/29.
 * lastRole: Administrator
 * Date: 2016/12/29
 * Time: 12:52
 */
class Role {
  constructor(data) {
    this.default = {
      id: "",//用户ID
      account: "",//用户账号
      password:"",//用户密码
      createTime:"",//创建时间
      modifyTime:"",//编辑时间
      deals: "",//操作
    };
    this.model = {};
    this.setDefault(data);
  }

  setDefault(data) {
    this.defaultData = Object.assign({}, this.default, data);
  }

  getModel() {
    for (let key in this.defaultData) {
      switch (key) {
        case"createTime":
        case"modifyTime":
          if (this.defaultData[key] && this.defaultData[key] !== "") {
            this.model[key] = new Date(this.defaultData[key]).Format("yyyy-MM-dd hh:mm:ss");
          } else {
            this.model[key] = "";
          }
          break;
        case"files":
          this.model.fileIds = this.getFilesIds(this.defaultData.files);
          break;
        case"deals":
          this.model.deals = 
          // this.defaultData.name=="root" ? [
          //   {text:"无权修改",type:"label"},
          // ]:
          [
            {text: "编辑",bgc:'#45BED9', id: "btnChange", name: "change", type: "button", event: "change"},
            {text: "删除",bgc:'#DB5550', id: "btnDele", name: "delete", type: "danger", event: "delete"},
          ]
          break;
        default:
          this.model[key] = this.defaultData[key] == "0" ? this.defaultData[key] : (this.defaultData[key] ? this.defaultData[key] : "");
          break;
      }
    }
    return this.model;
  }

  getFilesIds(files) {
    let ids = [];
    files.map(function (file) {
      let {id} = file;
      ids.push(id);
    })
    return ids;
  }

  static DEFAULT() {
    return new Role().default;
  }
}

export default Role
