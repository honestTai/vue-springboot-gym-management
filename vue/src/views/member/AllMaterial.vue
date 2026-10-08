<template>
  <div class="allCourse">

        <div class="search">
          <el-input placeholder="请输入健身器材名称" v-model="KeywordMaterial" class="input-with-select" size="mini">
            <el-button slot="append" icon="el-icon-search" size="mini"
                       @click="getByKeyword(KeywordMaterial)"></el-button>
          </el-input>
        </div>

    <div class="table">

<!--      <el-row>-->
<!--        <el-col :span="8" v-for="(o, index) in 2" :key="o" :offset="index > 0 ? 2 : 0">-->
<!--          <el-card :body-style="{ padding: '0px' }">-->
<!--            <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png" class="image">-->
<!--            <div style="padding: 14px;">-->
<!--              <span>好吃的汉堡</span>-->
<!--              <div class="bottom clearfix">-->
<!--                <time class="time">{{ currentDate }}</time>-->
<!--                <el-button type="text" class="button">操作按钮</el-button>-->
<!--              </div>-->
<!--            </div>-->
<!--          </el-card>-->
<!--        </el-col>-->
<!--      </el-row>-->
      <div style="padding: 10px 15px; display: flex; flex-wrap: wrap; justify-content: space-between;">
        <div v-for="item in tableData" style="flex-basis: 24%; margin-bottom: 20px; padding-right: 10px;">
          <div style="border-radius: 10px; border: 1px solid #cccccc; overflow: hidden;">
            <img :src="item.pic" alt="" style="width: 100%; height: 155px; object-fit: cover;">
          </div>
          <div style="padding: 10px;">
            <div style="font-size: 15px; font-weight: 550; color: #817a70">器材昵称：{{ item.equipmentName }}</div>
            <div style="font-size: 15px; font-weight: 550; color: #817a70">器材展示位置：{{ item.equipmentLocation }}</div>
            <div style="font-size: 15px; font-weight: 550; color: #817a70">介绍：{{ item.info }}</div>
            <div style="margin-top: 5px; font-weight: 600; font-size: 20px; color: red">￥ {{ item.price }}</div>
            <div style="margin-top: 5px; font-size: 13px; color: #4b4949">剩余数量：{{ item.num }}</div>
            <div style="margin-top: 10px">
              <el-input-number v-model="item.tmpNum" :min="1" :max="item.num" size="mini"></el-input-number>
            </div>
            <div style="margin-top: 20px; display: flex; justify-content: space-between;">
              <el-button type="warning" size="mini" :disabled="item.num === 0" @click="handleEdit(item)">购买</el-button>
              <el-button type="warning" size="mini" :disabled="item.num === 0" @click="addCarts(item)">加入购物车</el-button>
            </div>
          </div>
        </div>
      </div>



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
    <el-dialog title="收货信息" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="username" label="收货人">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="phone" label="联系方式">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="address" label="收货地址">
          <el-input v-model="form.address" autocomplete="off"></el-input>
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
  getByKeywordMaterial,
  totalCourseFuzzy,
  totalCourse,
  updateMemberChangeByMemberNo, updateMemberIntegral, getAllEquipment, getByKeywordEquipment, addCart, addOrder
} from "@/api/allApi";

export default {
  data() {
    return {
      tableData: [
      ],
      member: JSON.parse(window.localStorage.getItem('access-member')),
      KeywordMaterial: '',
      pageSize: 10,
      currentPage: 1,
      total: 0,
      searchValue: '',
      memberPower:'',
      coursePrice:'',
      form: {},
      fromVisible: false,
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
      ids: []
    }

  },
  created() {
    this.load(1)
  },
  methods: {
    addCarts(e){
      let param = JSON.parse(JSON.stringify(e))
      addCart({
        userId:this.member.memberNo,
        eqId:param.equipmentNo,
        eqNum:param.tmpNum
      }).then(res => {
        this.$message.success('添加成功')
      }).catch(err => {
        this.$message.error('操作失败，请稍后重试')
      })
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.fromVisible = true
    },
    buy() {
      let data = {
        userId: this.member.memberNo,
        eqId: this.form.equipmentNo,
        username: this.form.username,
        phone: this.form.phone,
        address: this.form.address,
        num: this.form.tmpNum,
        price: this.form.tmpNum * this.form.price
      }
      let that =this
      addOrder(data).then(res => {
        let message = res.data.message
        if (res.data.code === 200) {
          updateMemberChangeByMemberNo({
            memberNo: this.member.memberNo,
            coursePrice: data.price
          })
          that.load(that.currentPage)
          this.$message.success('购买成功')
          this.fromVisible = false

        } else {
          console.log(res)
          this.$message.warning(message)
        }
      }).catch(err => {
        this.$message.error('操作失败，请稍后重试')
      })
    },
    load(pageNum) {  // 分页查询
      if (pageNum) this.currentPage = pageNum
      getAllEquipment({
        page: this.currentPage,
        size: this.pageSize
      }).then(res => {
        this.tableData = res.data.list
        this.total = res.data.total
      }).catch(err => {
        this.$message.error('操作失败，请稍后重试')
      })
    },
    reset() {
      this.name = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    getByKeyword(KeywordEquipment) {
      getByKeywordEquipment({
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

</style>
