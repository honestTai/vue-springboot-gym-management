<template>

  <div class="home">
    <el-card shadow="hover">
      <div class="header">


        <div class="buttons">

          <!--搜索区域-->
          <div class="search">
            <el-input placeholder="请输入内容" v-model="KeywordEquipment" class="input-with-select" size="mini">
              <el-button slot="append" icon="el-icon-search" size="mini"
                         @click="getByKeyword(KeywordEquipment)"></el-button>
            </el-input>
          </div>
        </div>

      </div>


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
            prop="courseDesc">
          <template slot-scope="scope">
            <el-button type="primary" plain size="mini"
                       @click="sendOrder(scope.row)" v-if="scope.row.status === '0'">发货
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

    </el-card>

  </div>

</template>

<script>
import moment from 'moment';
import {addEquipment, cancelOrders, getAllEquipment, getOrder, getOrderSearch, sendOrders} from "@/api/allApi";
import {deleteEquipment} from "@/api/allApi";
import {updateEquipment} from "@/api/allApi";
import {totalEquipment} from "@/api/allApi";
import {getByKeywordEquipment} from "@/api/allApi";
import {totalEquipmentFuzzy} from "@/api/allApi";

export default {
  data() {
    return {
      tableData: [],
      pageSize: 10,
      currentPage: 1,
      total:0,
      admin:{

      },
      KeywordEquipment:''
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
    sendOrder(e){
      this.$confirm('是否确认发货, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //确定 发送ajax请求
        sendOrders({
          id: e.id
        }).then(res => {
          this.$message.success('发货成功')
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
        userId:-1,
      }).then(res=>{
        this.tableData = res.data.list
        this.total = res.data.total
      }).catch(err=>{
        this.$message.error('操作失败，请稍后重试')
      })
    },
    getByKeyword(KeywordEquipment) {
      getOrderSearch({
        keyWord: KeywordEquipment,
        page: this.currentPage,
        size: this.pageSize
      }).then(res => {
        this.tableData = res.data.list
        this.total = res.data.total
      }).catch(err => {
        this.$message.error('操作失败，请稍后重试')
      })

    },
  }
}
</script>


<style scoped>
.header {
  display: flex;
  justify-content: space-between;
}

.page-header {
  width: 20%;
}

.buttons {
  margin: 0px 0 10px 0;

}

.search {
  margin-right: 10px;
  width: 50%;
  float: left;
}

.search-input input {
  width: 110px;
}

</style>
