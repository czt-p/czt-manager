/**
 * Created by Administrator on 2017/4/7.
 */
import Questions from "src/models/questions";
class QuestionsList{
    constructor(data){
        // console.log('constructor', data)
        this.default = data;
        this.models= [];
        this.reset(this.default);
    }
    reset(items){
        let _this = this;
        items.map(function(item){
           let simple = new Questions(item);
            _this.models.push(simple.getModel());
       })
    }
    getModels(){
        return this.models;
    }
    getModel(i){
        return this.models[i]
    }
}
export default QuestionsList


