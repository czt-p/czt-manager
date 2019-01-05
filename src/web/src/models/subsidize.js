/**
 * @param a means b
 * Created by Administrator on 2016/12/29.
 * lastRole: Administrator
 * Date: 2016/12/29
 * Time: 12:52
 */
class Subsidize {
  constructor(data) {
    this.default = {
      id: "", //资助政策ID
      name: "", //资助政策名称
      regionCode: "", //政策所属地区行政区划代码
      content: "", //内容
      createTime: "", //创建时间
      modifyTime: "", //修改时间
      region: {
        code: '',
        fullName: '',
        name: '',
        parent: ''
      }, //政策所属地区
      deals: "", //操作
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
        case "createTime":
        case "modifyTime":
          if (this.defaultData[key] && this.defaultData[key] !== "") {
            this.model[key] = new Date(this.defaultData[key]).Format("yyyy-MM-dd hh:mm:ss");
          } else {
            this.model[key] = "";
          }
          break;
        case "files":
          this.model.fileIds = this.getFilesIds(this.defaultData.files);
          break;
        // case "region":
          
        //   break;
        case "deals":
          this.model.deals = [{
              text: "查看",
              bgc: '#7269D5',
              id: "btnView",
              name: "view",
              type: "button",
              event: "view"
            },
            {
              text: "编辑",
              bgc: '#45BED9',
              id: "btnChange",
              name: "change",
              type: "button",
              event: "change"
            },
            {
              text: "删除",
              bgc: '#DB5550',
              id: "btnDele",
              name: "delete",
              type: "button",
              event: "delete"
            },
          ]
          break;
        default:
          this.model.regionArea = this.defaultData[key].fullName;
          this.model[key] = this.defaultData[key] == "0" ? this.defaultData[key] : (this.defaultData[key] ? this.defaultData[key] : "");
          break;
      }
    }
    return this.model;
  }

  getFilesIds(files) {
    let ids = [];
    files.map(function (file) {
      let {
        id
      } = file;
      ids.push(id);
    })
    return ids;
  }

  static DEFAULT() {
    return new Subsidize().default;
  }
}

export default Subsidize
