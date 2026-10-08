<template>
  <div class="buyRecord">
    <el-table
        :data="tableData"
        stripe
        height="520px"
        style="width: 100%;height: 85%">
      <el-table-column
          type="index"
          :index="indexMethod"
          label="序号"
          style="height:50px">
      </el-table-column>
      <el-table-column
          prop="courseName"
          label="课程名称">
      </el-table-column>
      <el-table-column
          prop="courseTime"
          label="开课时间">
      </el-table-column>
      <el-table-column
          prop="courseDuration"
          label="课程时长">
      </el-table-column>
      <el-table-column
          prop="employeeName"
          label="教练">
      </el-table-column>
      <el-table-column
          prop="employeePhone"
          label="教练手机号">
      </el-table-column>
      <el-table-column
          prop="memberPhone"
          label="会员手机号">
      </el-table-column>
    </el-table>

    <div class="pagination">
      <el-pagination
          background
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, prev, pager, next"
          :total="total">
      </el-pagination>
    </div>

  </div>

</template>

<script>
import {getAllRegister, getRegisterByMemberNo, totalRegister} from "@/api/allApi";
import moment from "moment";

export default {
  data() {
    return {
      tableData: [],
      pageSize: 10,
      currentPage: 1,
      total:0,
      admin:{

      },
    }
  },
  computed: {
    indexMethod(){

    }
  },
  filters:{
    dataFormat(value){
      return moment(value).format("YYYY-MM-DD")
    }
  },
  created() {
    this.admin = JSON.parse(window.localStorage.getItem('access-member'))
  },
  mounted() {
    this.getRegisterByMemberNo()
  },
  methods:{
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    getRegisterByMemberNo(){
      let _this = this
      getRegisterByMemberNo({
        page: this.currentPage,
        size: this.pageSize,
        memberNo:_this.admin.memberNo,
      }).then(res=>{
        this.tableData = res.data.list
        this.total = res.data.total
      }).catch(err=>{
         this.$message.error('操作失败，请稍后重试')
      })
    }
  }
}
</script>
<style scoped>
.buyRecord {
  padding: 40px;
  width: 1000px;
  height: 650px;
}
</style>
