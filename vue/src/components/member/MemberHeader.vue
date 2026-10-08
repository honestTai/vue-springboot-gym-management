<template>
  <div class="memberHeader">
    <div style="width: 200px; padding-left: 30px;font-weight: bold;color:#309975;" >
      健身房购物系统
    </div>
    <div style="flex: 1"></div>
    <div style="width: 100px">
      <el-row class="block-col-2">
        <el-dropdown v-if="member !== null">
          <div class="adminer">
            <span class="el-dropdown-link">{{ member.memberName }}<i class="el-icon-arrow-down el-icon--right"></i></span>
          </div>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="logout">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <div v-else>
          <span class="el-dropdown-link">游客<i class="el-icon-arrow-down el-icon--right"></i></span>
        </div>
      </el-row>
    </div>
  </div>

</template>

<script>
export default {
  name: "MemberHeader",
  data() {
    return {
      member: {

      },
    }
  },
  created() {
    this.member = JSON.parse(window.localStorage.getItem('access-member'))
    console.log(this.member)
  },
  methods:{
    logout() {
      //退出登陆，清空token
      localStorage.removeItem('access-member');
      this.$router.push('/memberLogin');
    },
  }
}
</script>

<style scoped>
.memberHeader {
  height:70px;
  width: 100%;
  line-height: 70px;
  border-bottom: 1px solid #ccc;
  display: flex;
  position: fixed;
  top:0;
  left: 0;
  background-color: #fff;
  z-index: 300;
}
</style>
