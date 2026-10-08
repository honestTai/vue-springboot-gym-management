<template>
  <div class="allCourse">
    <el-table
        :data="tableData"
        height="520px"
        stripe
        style="width: 100%">
      <el-table-column prop="pic" label="物品图片" show-overflow-tooltip>
        <template v-slot="scope">
          <div style="display: flex; align-items: center">
            <el-image style="width: 40px; height: 40px" v-if="scope.row.equipment.pic"
                      :src="scope.row.equipment.pic" :preview-src-list="[scope.row.equipment.pic]"></el-image>
          </div>
        </template>
      </el-table-column>
      <el-table-column
          label="购买的物品名称"
          prop="equipment.equipmentName">
      </el-table-column>
      <el-table-column
          label="购买的物品数量"
          prop="eqNum">
        <template v-slot="scope">
          <div style="display: flex; align-items: center">
            <el-input-number v-model="scope.row.eqNum" placeholder="请输入数量" size="mini" @change="handleInputChange(scope.row)" :max="scope.row.equipment.num" :min="1"></el-input-number>
          </div>
        </template>
      </el-table-column>
      <el-table-column
          label="物品单价"
          prop="equipment.price">
      </el-table-column>
      <el-table-column
          label="总价"
          prop="coursePrice">
        <template v-slot="scope">
          <div style="display: flex; align-items: center">
            {{scope.row.eqNum * scope.row.equipment.price}}
          </div>
        </template>
      </el-table-column>
      <el-table-column
          label="操作"
          prop="courseDesc">
        <template slot-scope="scope">
          <el-button type="primary" plain size="mini"
                     @click="buyEquipment(scope.row)">购买
          </el-button>
          <el-button type="primary" plain size="mini"
                     @click="deleteCart(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="display: flex;justify-content: center;margin-top: 10px">
      <el-pagination
          background
          layout="prev, pager, next"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-size="pageSize"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="收货信息" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="cartEquipment" :rules="rules" ref="formRef">
        <el-form-item prop="username" label="收货人">
          <el-input v-model="cartEquipment.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="phone" label="联系方式">
          <el-input v-model="cartEquipment.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="address" label="收货地址">
          <el-input v-model="cartEquipment.address" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="buy">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import moment from "moment";
import {
  addRegister,
  getAllCourse,
  getAllRegister,
  getMemberPower,
  getByKeywordCourse,
  totalCourseFuzzy,
  totalCourse,
  updateMemberChangeByMemberNo,
  updateMemberIntegral,
  courseType,
  getCart,
  deleteCartApi,
  deleteCourse,
  addCart,
  updateCart, addOrder
} from "@/api/allApi";
import fa from "element-ui/src/locale/lang/fa";

export default {
  data() {
    return {
      tableData: [],
      admin: {},
      KeywordCourse: '',
      pageSize: 10,
      currentPage: 1,
      total: 0,
      searchValue: '',
      memberPower: '',
      coursePrice: '',
      options:[],
      value:'',
      cartEquipment:{},
      fromVisible:false,
      rules: {
        username: [
          {required: true, message: '请输入收货人', trigger: 'blur'},
        ],
        phone: [
          {required: true, message: '请输入联系电话', trigger: 'blur'},
        ],
        address: [
          {required: true, message: '请输入联系地址', trigger: 'blur'},
        ],
      },
    }

  },
  computed: {
    indexMethod() {
      return (this.currentPage - 1) * 10 + 1
    }
  },
  filters: {
    dataFormat(value) {
      return moment(value).format("YYYY-MM-DD")
    }
  },
  created() {
    this.admin = JSON.parse(window.localStorage.getItem('access-member'))
  },
  mounted() {
    this.getCats()
  },
  methods: {
    buy() {
      let data = {
        userId: this.admin.memberNo,
        eqId: this.cartEquipment.equipment.equipmentNo,
        username: this.cartEquipment.username,
        phone: this.cartEquipment.phone,
        address: this.cartEquipment.address,
        num: this.cartEquipment.eqNum,
        price: this.cartEquipment.eqNum * this.cartEquipment.equipment.price,
        cartId: this.cartEquipment.id
      }
      addOrder(data).then(res => {
        let message = res.data.message
        if (res.data.code === 200) {
          updateMemberChangeByMemberNo({
            memberNo: this.admin.memberNo,
            coursePrice: data.price
          })
          this.$message.success('购买成功')
          this.fromVisible = false
          this.getCats()
        } else {
          console.log(res)
          this.$message.warning(message)
        }
      }).catch(err => {
        this.$message.error('操作失败，请稍后重试')
      })
    },
    handleInputChange(e){
      let param = JSON.parse(JSON.stringify(e))
      updateCart({
        id:param.id,
        eqNum:param.eqNum
      }).then(res => {
        this.$message.success('修改成功')
        this.getCats()
      }).catch(err => {
        this.$message.error('操作失败，请稍后重试')
      })
    },
    buyEquipment(e){
      this.cartEquipment = JSON.parse(JSON.stringify(e))
      this.fromVisible = true
    },
    deleteCart(e){
      this.$confirm('此操作将永久删除该购物车记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //确定 发送ajax请求
        deleteCartApi({
          id: e
        }).then(res => {
          this.getCats()
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
    handleCurrentChange(e) {
      this.currentPage = e
      this.getCats()
    },

    getCats() {
      getCart({
        page: this.currentPage,
        size: this.pageSize,
        userId: this.admin.memberNo
      }).then(res => {
        this.tableData = res.data.list
        this.total = res.data.total
      }).catch(err => {
        this.$message.error('操作失败，请稍后重试')
      })
    }

  }
}
</script>

<style scoped>

.allCourse {
  padding: 40px;
  width: 1000px;
}

.demo-table-expand {
  font-size: 0;
}

.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}

.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>
