<template>

  <div class="home">
    <el-card shadow="hover">
      <div class="header">


        <div class="buttons">
          <el-button type="primary" size="mini" @click="addEquipmentPage">新增</el-button>
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
          border
          stripe
          size="mini"
          style="width: 100%;">
        <el-table-column
            type="index"
            :index="indexMethod"
            label="序号"
            width="60"
            style="height:50px"
        >
        </el-table-column>
        <el-table-column
            prop="equipmentName"
            label="器材名称">
        </el-table-column>
        <el-table-column prop="pic" label="器材图片" show-overflow-tooltip>
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 40px; height: 40px" v-if="scope.row.pic"
                        :src="scope.row.pic" :preview-src-list="[scope.row.pic]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column
            prop="price"
            label="器材价格">
        </el-table-column>
        <el-table-column
            prop="info"
            label="器材介绍">
        </el-table-column>
        <el-table-column
            prop="num"
            label="器材数量">
        </el-table-column>
        <el-table-column
            prop="equipmentLocation"
            label="器材位置">
        </el-table-column>
        <el-table-column
            prop="equipmentMessage"
            label="备注信息">
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作">
          <template slot-scope="scope">
            <el-button @click="editEquipment(scope.row)" type="text" size="mini">编辑</el-button>
            <el-button slot="reference" type="text" size="mini" @click="deleteEquipment(scope.row.equipmentNo)">删除
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


      <el-dialog
          title="添加器材"
          :visible.sync="dialogVisible2"
          width="30%"
      >
        <el-form ref="form" :model="sizeForm" label-width="80px" size="mini">
          <el-form-item  label="器材图片">
            <el-upload
                class="avatar-uploader"
                :action="$baseUrl + '/files/upload'"
                list-type="picture"
                :on-success="handleAvatarSuccess"
            >
              <el-button type="primary">上传图片</el-button>
            </el-upload>
          </el-form-item>
          <el-form-item label="器材价格">
            <el-input v-model="sizeForm.price" type="number"></el-input>
          </el-form-item>
          <el-form-item label="器材数量">
            <el-input v-model="sizeForm.num" type="number">></el-input>
          </el-form-item>
          <el-form-item label="器材介绍">
            <el-input v-model="sizeForm.info"></el-input>
          </el-form-item>
          <el-form-item label="器材名称">
            <el-input v-model="sizeForm.equipmentName"></el-input>
          </el-form-item>
          <el-form-item label="器材位置">
            <el-input v-model="sizeForm.equipmentLocation"></el-input>
          </el-form-item>
          <el-form-item label="备注信息">
            <el-input v-model="sizeForm.equipmentMessage"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible2 = false">取 消</el-button>
          <el-button type="primary" @click="addEquipment">确 定</el-button>
        </span>
      </el-dialog>

      <el-dialog
          title="修改器材信息"
          :visible.sync="dialogVisible"
          width="30%"
      >
        <el-form ref="form" :model="sizeForm" label-width="80px" size="mini">
          <el-form-item  label="课程图片">
            <el-image style="width: 40px; height: 40px" v-if="sizeForm.pic"
                      :src="sizeForm.pic" :preview-src-list="[sizeForm.pic]"></el-image>
            <el-upload
                class="avatar-uploader"
                :action="$baseUrl + '/files/upload'"
                list-type="picture"
                :on-success="handleAvatarSuccess"

            >
              <el-button type="primary">上传图片</el-button>
            </el-upload>
          </el-form-item>
          <el-form-item label="器材价格">
            <el-input v-model="sizeForm.price" type="number"></el-input>
          </el-form-item>
          <el-form-item label="器材数量">
            <el-input v-model="sizeForm.num" type="number">></el-input>
          </el-form-item>
          <el-form-item label="器材介绍">
            <el-input v-model="sizeForm.info"></el-input>
          </el-form-item>
          <el-form-item label="器材位置">
            <el-input v-model="sizeForm.equipmentLocation"></el-input>
          </el-form-item>
          <el-form-item label="备注信息">
            <el-input v-model="sizeForm.equipmentMessage"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateEquipment">确 定</el-button>
        </span>
      </el-dialog>
    </el-card>

  </div>

</template>

<script>
import moment from 'moment';
import {addEquipment, getAllEquipment} from "@/api/allApi";
import {deleteEquipment} from "@/api/allApi";
import {updateEquipment} from "@/api/allApi";
import {totalEquipment} from "@/api/allApi";
import {getByKeywordEquipment} from "@/api/allApi";
import {totalEquipmentFuzzy} from "@/api/allApi";

export default {
  name: "EquipmentManage",
  data() {
    return {
      dialogFormVisible: false,
      tableData: [],
      sizeForm: {},
      dialogVisible: false,
      dialogVisible2: false,
      /*分页*/
      pageSize: 10,
      currentPage: 1,
      total: 0,
      searchValue: '',
      KeywordEquipment: ''
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
  mounted() {
    this.getAllEquipment()
  },
  methods: {
    handleCurrentChange(e) {
      this.currentPage = e
      this.getAllEquipment()
    },
    handleAvatarSuccess(response, file, fileList) {
      this.sizeForm.pic = response.data
    },
    onSubmit() {
      this.dialogFormVisible = false
      this.clear()
    },
    clear() {
      this.form = {
        username: '',
        password: '',
        name: '',
        gender: '',
        phone: '',
      }
    },
    cancel() {
      this.dialogFormVisible = false,
          this.form = {
            username: '',
            password: '',
            name: '',
            gender: '',
            phone: '',
          }
    },
    goBack() {
      this.$router.back();
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {
          });
    },
    editEquipment(value) {
      this.sizeForm = value
      this.dialogVisible = true
    },
    addEquipmentPage() {

      this.dialogVisible2 = true

    },
    addEquipment() {

      let currentPage = this.currentPage
      let pageSize = this.pageSize
      addEquipment(this.sizeForm).then(res => {
        if (res.data.code === 200) {
          this.getAllEquipment()
          this.$message.success('操作完成')
        } else {
          this.$message.success('操作完成')
        }
      }).catch(err => {
        this.$message.error('操作失败，请稍后重试')
      })
      this.sizeForm = {},
          this.dialogVisible2 = false

    },

    deleteEquipment(equipmentNo) {
      let currentPage = this.currentPage
      let pageSize = this.pageSize
      this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //确定 发送ajax请求
        deleteEquipment({equipmentNo: equipmentNo}).then(res => {
          if (res.data.code === 200) {
            this.getAllEquipment()
          } else {
            this.$message.success('操作完成')
          }
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
    updateEquipment() {
      let currentPage = this.currentPage
      let pageSize = this.pageSize
      updateEquipment(this.sizeForm).then(res => {
        if (res.data.code === 200) {
          this.getAllEquipment()
        } else {
          this.$message.success('操作完成')
        }
      }).catch(err => {
        this.$message.error('操作失败，请稍后重试')
      })
      this.dialogVisible = false
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
    //获取数据方法
    getAllEquipment() {
      getAllEquipment({
        page: this.currentPage,
        size: this.pageSize
      }).then(res => {
        this.tableData = res.data.list
        this.total = res.data.total
      }).catch(err => {
        this.$message.error('操作失败，请稍后重试')
      })
    }
  },
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
