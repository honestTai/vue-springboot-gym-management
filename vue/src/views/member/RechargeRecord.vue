<template>
  <div class="buyRecord">
    <el-table
        :data="tableData"
        stripe
        height="520px"
        style="width: 100%;height: 85%">
      <el-table-column type="expand">
      <template slot-scope="props">
        <div style="width: 85%;margin: auto">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="收货人">
              <span>{{ props.row.username }}</span>
            </el-form-item>
            <el-form-item label="收货地址">
              <span>{{ props.row.phone }}</span>
            </el-form-item>
            <el-form-item label="收货电话">
              <span>{{ props.row.address }}</span>
            </el-form-item>
          </el-form>
        </div>
      </template>
      </el-table-column>
      <el-table-column prop="pic" label="图片" show-overflow-tooltip>
        <template v-slot="scope">
          <div style="display: flex; align-items: center">
            <el-image style="width: 40px; height: 40px" v-if="scope.row.equipment.pic"
                      :src="scope.row.equipment.pic" :preview-src-list="[scope.row.equipment.pic]"></el-image>
          </div>
        </template>
      </el-table-column>
      <el-table-column
          prop="equipment.equipmentName"
          label="购买的物品名称">
      </el-table-column>
      <el-table-column
          prop="time"
          label="购买时间">
      </el-table-column>
      <el-table-column
          label="订单状态"
          prop="status"
          :formatter="formatStatus">
      </el-table-column>
      <el-table-column
          prop="price"
          label="价格">
      </el-table-column>
      <el-table-column
          prop="num"
          label="数量">
      </el-table-column>
      <el-table-column
          label="操作"
          prop="courseDesc" width="200">
        <template slot-scope="scope">
          <el-button type="primary" plain size="mini"
                     @click="cancelOrder(scope.row)" v-if="scope.row.status === '0' || scope.row.status === '2' ">退款
          </el-button>
          <el-button type="primary" plain size="mini"
                     @click="confirmOrder(scope.row)" v-if="scope.row.status === '2'">收货
          </el-button>
        </template>
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
import {
  cancelOrders, confirmOrders,
  deleteCartApi,
  getAllRegister,
  getOrder,
  getRegisterByMemberNo,
  totalRegister
} from "@/api/allApi";
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
    cancelOrder(e){
      this.$confirm('是否确认退款, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //确定 发送ajax请求
        cancelOrders({
          id: e.id,
          eqId:e.eqId,
          userId:e.userId,
          num:e.num,
          price:e.price
        }).then(res => {
          this.getRegisterByMemberNo()
        }).catch(err => {
          this.$message.error('操作失败，请稍后重试')
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    confirmOrder(e){
      this.$confirm('是否确认收货, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //确定 发送ajax请求
        confirmOrders({
          id: e.id
        }).then(res => {
          this.getRegisterByMemberNo()
        }).catch(err => {
          this.$message.error('操作失败，请稍后重试')
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消收货'
        });
      });
    },
    formatStatus(e) {
      console.log(e)
      switch (e.status) {
        case '0':
          return '待发货';
        case '1':
          return '已退款';
        case '2':
          return '已发货';
        case '3':
          return '已收货';
        default:
          return '未知状态';
      }
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    getRegisterByMemberNo(){
      let _this = this
      getOrder({
        page: this.currentPage,
        size: this.pageSize,
        userId:_this.admin.memberNo,
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
