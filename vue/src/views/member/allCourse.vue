<template>
  <div class="allCourse">

    <div class="search">
      <el-input placeholder="请输入内容" v-model="KeywordCourse" class="input-with-select" size="mini">
        <el-button slot="append" icon="el-icon-search" size="mini"
                   @click="getByKeyword(KeywordCourse)"></el-button>
      </el-input>

      <el-select v-model="value" placeholder="分类选择" size="mini" style="padding: 10px" @change="searchValueAction" clearable filterable>
        <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
    </div>
    <el-table
        :data="tableData"
        height="520px"
        stripe
        style="width: 100%">
      <el-table-column type="expand">
        <template slot-scope="props">
          <div style="width: 85%;margin: auto">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="课程名称">
                <span>{{ props.row.courseName }}</span>
              </el-form-item>
              <el-form-item label="课程价格">
                <span>{{ props.row.coursePrice }} 元</span>
              </el-form-item>
              <el-form-item label="开课时间">
                <span>{{ props.row.courseTime }}</span>
              </el-form-item>
              <el-form-item label="课程时长">
                <span>{{ props.row.courseDuration }} 分钟</span>
              </el-form-item>
              <el-form-item label="授课教练">
                <span>{{ props.row.employeeNameCoach }}</span>
              </el-form-item>
              <el-form-item label="联系方式">
                <span>{{ props.row.employeePhoneCoach }}</span>
              </el-form-item>
              <el-form-item label="课程积分">
                <span>{{ props.row.courseIntegral }}</span>
              </el-form-item>
            </el-form>
          </div>

        </template>
      </el-table-column>
      <el-table-column prop="pic" label="课程图片" show-overflow-tooltip>
        <template v-slot="scope">
          <div style="display: flex; align-items: center">
            <el-image style="width: 40px; height: 40px" v-if="scope.row.pic"
                      :src="scope.row.pic" :preview-src-list="[scope.row.pic]"></el-image>
          </div>
        </template>
      </el-table-column>
      <el-table-column
          label="课程名称"
          prop="courseName">
      </el-table-column>
      <el-table-column
          label="课程分类"
          prop="courseDesc">
      </el-table-column>
      <el-table-column
          label="原价"
          prop="coursePrice">
      </el-table-column>
      <!-- <el-table-column
            label="优惠价"
            prop="discountPrice">
      </el-table-column> -->
      <el-table-column
          label="操作"
          prop="courseDesc">
        <template slot-scope="scope">
          <el-button type="primary" plain size="mini"
                     @click="buyCourse(scope.row.courseNo,scope.row.coursePrice,scope.row.courseIntegral)">购买
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
  updateMemberChangeByMemberNo, updateMemberIntegral, courseType
} from "@/api/allApi";

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
      value:''
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
    courseType().then(res => {
      this.options = res.data
    }).catch(err => {
      this.$message.error('操作失败，请稍后重试')
    })
  },
  mounted() {
    //判断用户等级
    if (this.admin != null) {
      this.getMemberPower()
    }
    this.getAllCourse()
  },
  methods: {
    searchValueAction(e){
      this.getByKeyword(e)
    },
    handleCurrentChange(e) {
      this.currentPage = e
      this.getAllCourse()
    },
    buyCourse(courseNo, coursePrice, courseIntegral) {
      if(this.admin == null){
        this.$message.error('请登录')
        this.$router.replace({path:'/memberLogin'})
      }else {
        let _this = this
        console.log(_this)
        addRegister({
          courseNo: courseNo,
          memberPhone: _this.admin.memberPhone,
          memberNo: _this.admin.memberNo,
        }).then(res => {
          let message = res.data.message

          if (res.data.code === 200) {
            updateMemberChangeByMemberNo({
              memberNo: this.admin.memberNo,
              coursePrice: coursePrice
            })
            updateMemberIntegral({
              memberNo: this.admin.memberNo,
              price: -courseIntegral
            })
            _this.$message.success('购买成功')
          } else {
            console.log(res)
            _this.$message.warning(message)
          }
        }).catch(err => {
          this.$message.error(err.message)
        })
      }
    },
    getByKeyword(KeywordCourse) {
      getByKeywordCourse({
        keyWord: KeywordCourse,
        page: this.currentPage,
        size: this.pageSize
      }).then(res => {
        this.tableData = res.data.list
        this.total = res.data.total
      }).catch(err => {
        this.$message.error('操作失败，请稍后重试')
      })

    },
    getMemberPower() {
      let _this = this
      getMemberPower({
        memberNo: _this.admin.memberNo
      }).then(res => {
        this.memberPower = res.data
      }).catch(err => {
        this.$message.error('操作失败，请稍后重试')
      })
    },
    getAllCourse() {
      getAllCourse({
        page: this.currentPage,
        size: this.pageSize
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
