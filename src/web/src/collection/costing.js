/**
 * Created by Administrator on 2017/4/7.
 */
import costings from "src/models/costing";
class costingsList {
  constructor(data) {
    // console.log('constructor', data)
    this.default = data;
    this.models = [];
    this.reset(this.default);
  }
  reset(items) {
    let _this = this;
    items.map(function (item) {
      let simple = new costings(item);
      _this.models.push(simple.getModel());
    })
  }
  getModels() {
    return this.models;
  }
  getModel(i) {
    return this.models[i]
  }
}
export default costingsList
