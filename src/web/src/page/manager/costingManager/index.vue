<template>
  <div class="container">
    <div class="title">成本核算</div>
    <div class="searchArea">
      <div class="searchInput">
        <div class="searchTime">
          <span>时间：</span>
          <el-date-picker
            v-model="searchObj.searchTime"
            type="datetimerange"
            :editable="false"
            size="small"
            value-format="timestamp"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
        </div>
        <div class="askName">公司名称：
          <el-input
            @keyup.enter.native="searchTable"
            size="small"
            v-model="searchObj.companyName"
            placeholder="请输入公司名称"
          ></el-input>
        </div>
        <div class="questionName">联系方式：
          <el-input
            @keyup.enter.native="searchTable"
            size="small"
            v-model="searchObj.telephone"
            placeholder="请输入联系方式"
          ></el-input>
        </div>
      </div>
      <div class="rightBtn">
        <el-button type="primary" icon="el-icon-search" size="small" @click="searchTable">开始搜索</el-button>
        <!-- <el-button type="primary" icon="el-icon-plus" size="small" @click='handleAdd' style='background:#40AB9B'>新增</el-button> -->
      </div>
    </div>
    <TableVue v-bind:currentTable="currentTable"></TableVue>
    <DialogVue>
      <template slot="viewRole" v-if='costDetail'>
        <div class="part">
          <div class="pContent">
            <div class="viewRole">
              公司名称：
              <span>{{costDetail.companyName}}</span>
            </div>
            <div class="viewRole">
              手机号码：
              <span>{{costDetail.telephone}}</span>
            </div>
            <div class="viewRole">
              核算总费用：
              <span>{{costDetail.totalCost}}元</span>
            </div>
            <div class="viewRole">
              评测结果：
              <span>
                咨询费用：{{costDetail.consultCost}}元；
                专项审计报告费用：{{costDetail.specialAuditCost}}元；
                知识产权费用：{{costDetail.ipCost}}元；
                年度审计报告费用{{costDetail.annualAuditCost}}元；
                其他费用：{{costDetail.otherCost}}元；</span>
            </div>
          </div>
        </div>
        <div class="part">
          <div class='title'>咨询费用</div>
          <div class="pContent">
            <div class="viewRole">
              <span class='lC'>费用合计：{{costDetail.details.consultCost.totalCost}}元</span>
            </div>
            <div class="viewRole">
              <span class='lC'>企业所属高新领域：{{costDetail.details.consultCost.highFieldCost.highField}}</span>
              <span class='rC'>费用：{{costDetail.details.consultCost.highFieldCost.cost}}元</span>
            </div>
            <div class="viewRole">
              <span class='lC'>企业财务成长性情况：{{costDetail.details.consultCost.financialGrowthCost.financialGrowth}}</span>
              <span class='rC'>费用：{{costDetail.details.consultCost.financialGrowthCost.cost}}元</span>
            </div>
            <div class="viewRole">
              <span class='lC'>企业最近一年的营业收入：{{costDetail.details.consultCost.recentYearIncomingCost.recentYearIncoming}}</span>
              <span class='rC'>费用：{{costDetail.details.consultCost.recentYearIncomingCost.cost}}元</span>
            </div>
          </div>
        </div>
        <div class="part">
          <div class='title'>专项审计报告费用</div>
          <div class="pContent">
            <div class="viewRole">
              <span class='lC'>费用合计：{{costDetail.details.specialAuditCost.totalCost}}元</span>
            </div>
            <div class="viewRole">
              <span class='lC'>预计申请年度：{{costDetail.details.specialAuditCost.applyYear}}年</span>
            </div>
            <div class="viewRole" v-for='(x,i) in costDetail.details.specialAuditCost.costList' :key='i' style='padding-left: 40px;'>
              <span class='mC'>{{x.year}}年</span>
              <span class='mC'>营业收入：{{x.incoming}}万元</span>
              <span class='lC' v-if='x.managerCost'>其中管理费用投入金额 {{x.managerCost}}万元</span>
              <span class='lC' v-else>其中研发费用投入金额 {{x.rdCost}}万元</span>
              <span class='mC'>费用：{{x.cost}}元</span>
            </div>
          </div>
        </div>
        <div class="part">
          <div class='title'>知识产权费用</div>
          <div class="pContent">
            <div class="viewRole">
              <span class='lC'>费用合计：{{costDetail.details.ipCost.totalCost}}元</span>
            </div>
            <div class="viewRole">
              <span class='lC'>知识产权费用需要测算：{{costDetail.details.ipCost.costList.length>0?'是':'否'}}</span>
            </div>
            <template v-if='costDetail.details.ipCost.costList.length>0'>
              <div class="viewRole"  v-for='(x,i) in costDetail.details.ipCost.costList' :key='i'>
                <span class='lC'>知识产权类型：{{x.type}}</span>
                <span class='mC'>知识产权数量：{{x.quantity}}个</span>
                <span class='mC'>费用：{{x.cost}}元</span>
              </div>
            </template>
          </div>
        </div>
        <div class="part">
          <div class='title'>年度审计报告费用</div>
          <div class="pContent">
            <div class="viewRole">
              <span class='lC'>费用合计：{{costDetail.details.annualAuditCost.totalCost}}元</span>
            </div>
            <div class="viewRole">
              <span class='lC'>预计申请年度：{{costDetail.details.annualAuditCost.applyYear}}年</span>
            </div>
            <div class="viewRole">
              <span class='lC'>年度审计报告费用需要测算：{{costDetail.details.annualAuditCost.costList.length>0?'是':'否'}}</span>
            </div>
            <template v-if='costDetail.details.annualAuditCost.costList.length>0'>
              <div class="viewRole" v-for='(x,i) in costDetail.details.annualAuditCost.costList'  :key='i' style='padding-left: 40px;'>
                <span class='mC'>{{x.year}}年</span>
                <span class='mC'>资产总额：{{x.amount}}万元</span>
                <span class='mC'>费用：{{x.cost}}元</span>
              </div>
            </template>
          </div>
        </div>
        <div class="part">
          <div class='title'>其他费用</div>
          <div class="pContent">
            <div class="viewRole">
              <span class='lC'>费用合计：{{costDetail.details.otherCost.totalCost}}元</span>
            </div>
            <div class="viewRole">
              <span class='lC'>其他费用需要测算：{{costDetail.details.otherCost.costList.length>0?'是':'否'}}</span>
            </div>
            <template v-if='costDetail.details.otherCost.costList.length>0'>
              <div class="viewRole" v-for='(x,i) in costDetail.details.otherCost.costList'  :key='i'>
                <span class='lC'>费用类型：{{x.type}}</span>
                <span class='mC'>所需数量：{{x.quantity}}个</span>
                <span class='mC'>费用：{{x.cost}}元</span>
              </div>
            </template>
            
          </div>
        </div>
        
        
      </template>
    </DialogVue>
  </div>
</template>
<script>
import "src/basic";
import { mapGetters, mapActions } from "vuex";
import XHR from "framework/xhr/xhr";
import Util from "framework/util/util";
import TableVue from "framework/components/TableVue";
import DialogVue from "framework/components/dialogVue";

export default {
  name: "costingManager",
  data() {
    return {
      currentTable: "costingManager",
      searchObj: {
        searchTime: "",
        companyName: "",
        telephone: ""
      },
      value11: ""
    };
  },
  components: {
    TableVue,
    DialogVue
  },
  computed: {
    ...mapGetters({
      setState: "setState"
    }),
    ...mapGetters("costingManager", {
      costDetail: "costDetail",
      sendInfo: "sendInfo"
    })
  },
  methods: {
    searchTable() {
      let param = {
        companyName: this.searchObj.companyName, //{"question":"title","answer":"test","startDate":1545713393497,"endDate":1545713393497}
        telephone: this.searchObj.telephone,
        accountTime: this.searchObj.searchTime
      };
      this.$store.dispatch(this.currentTable + "/paramChange", param);
    }
  },
  beforeCreate() {
    this.$store.dispatch("reSetSate", "costingManager");
    Util.setItem("currentVue", { vue: "costingManager" });
  },
  mounted() {
    // console.log('sendInfo',this.sendInfo)
  }
};
</script>

<style lang="scss" >
@import "static/scss/container";
.container {
  .title {
    border-left: 4px solid rgba(74, 144, 226, 1);
    height: 18px;
    font-size: 18px;
    font-family: SourceHanSansCN-Medium;
    font-weight: 500;
    color: rgba(51, 51, 51, 1);
    line-height: 16px;
    padding-left: 10px;
  }
  .searchArea {
    display: flex;
    justify-content: space-between;
    align-items: center;
    .searchInput {
      display: flex;
      justify-content: space-between;
      align-items: center;
      & > div {
        display: inline-block;
        margin-right: 30px;
        padding: 18px 4px;
        .el-input {
          vertical-align: middle;
          width: auto !important;
        }
      }
      .rightBtn {
        align-items: center;
        display: flex;
        button {
          border: none !important;
        }
      }
    }
  }
  .el-card {
    height: calc(100% - 90px);
  }
}
</style>
