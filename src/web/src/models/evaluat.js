/**
 * @param a means b
 * Created by Administrator on 2016/12/29.
 * lastRole: Administrator
 * Date: 2016/12/29
 * Time: 12:52
 */
class Evaluat {
  constructor(data) {
    this.default = {
      id: "",//ID
      addtime: "",//
      answer:"",//答案
      question:"",//问题
      updatedTime:"",//跟新时间
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
            this.model[key] = new Date(this.defaultData[key]).Format("yyyy-MM-dd");
          } else {
            this.model[key] = "";
          }
          break;
        case"files":
          this.model.fileIds = this.getFilesIds(this.defaultData.files);
          break;
        case"deals":
          this.model.deals = [
            {text: "查看详情",bgc:'#7269D5', id: "btnView", name: "view", type: "button", event: "view"},
            // {text: "编辑",bgc:'#45BED9', id: "btnChange", name: "change", type: "button", event: "change"},
            // {text: "删除",bgc:'#DB5550', id: "btnDele", name: "delete", type: "button", event: "delete"},
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
    return new Evaluat().default;
  }
}

export default Evaluat
