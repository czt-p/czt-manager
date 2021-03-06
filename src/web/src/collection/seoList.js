/**
 * Created by Administrator on 2017/4/7.
 */
import Seo from "src/models/seo";
class SeoList {
  constructor(data) {
    // console.log('constructor', data)
    this.default = data;
    this.models = [];
    this.reset(this.default);
  }
  reset(items) {
    let _this = this;
    items.map(function (item) {
      let simple = new Seo(item);
      _this.models.push(simple.getModel());
    //   console.log('_this.models', _this.models)
    })
  }
  getModels() {
    return this.models;
  }
  getModel(i) {
    return this.models[i]
  }
}
export default SeoList
