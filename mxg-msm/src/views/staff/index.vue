<template>
  <div>
    <el-form ref="searchForm" :inline="true" :model="searchMap" style="margin-top: 20px">
      <el-form-item prop="account">
        <el-input v-model="searchMap.account" placeholder="account"></el-input>
      </el-form-item>
      <el-form-item prop="name">
        <el-input v-model="searchMap.name" placeholder="User name"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="fetchData">Query</el-button>
        <el-button type="primary" @click="handlerAdd">add</el-button>
        <el-button type="primary" @click="resetForm('searchForm')">reset</el-button>
      </el-form-item>
    </el-form>


    <el-table
        :data="list"
        style="width: 100%">
      <el-table-column type="index" label="Num" width="60"></el-table-column>
      <el-table-column prop="account" label="账号"></el-table-column>
      <el-table-column prop="name" label="姓名"></el-table-column>
      <el-table-column prop="age" label="年龄"></el-table-column>
      <el-table-column prop="phone" label="电话"></el-table-column>
      <el-table-column prop="salary" label="薪酬"></el-table-column>
      <el-table-column prop="Entrydate" label="入职时间"></el-table-column>
      <el-table-column label="Operations" width="150">
        <template slot-scope="scope">
          <el-button
              size="mini"
              @click="handleEdit(scope.row.id)">Edit</el-button>
          <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.row.id)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-sizes="[10, 20, 50]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>

    <el-dialog title="Add user" :visible.sync="dialogFormVisible" width="500px">
      <el-form :rules="rules" ref="pojoForm" label-width="100px" label-position="right" :model="pojo">
        <el-form-item label="账号" prop="account">
          <el-input v-model="pojo.account"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="pojo.name"></el-input>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="pojo.age"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="pojo.phone"></el-input>
        </el-form-item>
        <el-form-item label="薪酬" prop="salary">
          <el-input v-model="pojo.salary"></el-input>
        </el-form-item>
        <el-form-item label="入职时间" prop="Entrydate">
          <el-date-picker
              value-format="yyyy-MM-dd"
              v-model="pojo.Entrydate"
              type="date"
              placeholder="入职时间">
          </el-date-picker>
        </el-form-item>



      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="pojo.id === undefined ? addData('pojoForm'): UpdateData('pojoForm')">Confirm</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import staffApi from '@/api/staff'
import memberApi from "@/api/member";

export default {
  data(){
    return{
      list: [],
      pageSize: 10,
      currentPage: 1,
      total: 0,
      dialogFormVisible: false,
      searchMap: {
        name: '',
        account: ''
      },
      pojo: {
        id: null,
        name: '',
        account: '',
        age: '',
        salary: '',
        phone: '',
        Entrydate: '',
      },
      rules: {
        name: [
          {required: true, message: "name cannot be empty", trigger: 'blur'}
        ],
        account: [
          {required: true, message: "linkman cannot be empty", trigger: 'blur'}
        ],

      }
    }
  },

  created() {
    this.fetchData()
  },

  methods:{
    handleSizeChange(val){
      this.pageSize = val;
      this.fetchData();
    },
    handleCurrentChange(val){
      this.currentPage = val;
      this.fetchData();
    },
    fetchData() {
      staffApi.search(this.currentPage,this.pageSize,this.searchMap).then(response =>{
        const resp = response.data.data
        this.list = resp.rows
        this.total = resp.total
        console.log(this.list)
      })
    },
    resetForm(formName) {
      console.log("重置",formName)
      this.$refs[formName].resetFields();
    },
    handlerAdd(){
      this.pojo = {}
      this.dialogFormVisible = true

      if (this.$refs['pojoForm']!==undefined) {
        this.$refs['pojoForm'].resetFields()
      }

    },
    addData(formName){
      this.$refs[formName].validate(valid =>{
        console.log("add",this.pojo.id)

        if (valid){
          staffApi.add(this.pojo).then(response =>{
            const resp = response.data
            if(resp.flag){
              this.$message({
                message: "add success, the code is 123456",
                type: "warning"
              })
              this.fetchData()
              this.dialogFormVisible = false
            }else {
              this.$message({
                message: resp.message,
                type: 'warning'
              })
            }
          })

        }else{
          return false;
        }
      })
    },
    handleDelete(id){
      console.log("delete ", id)
      this.$confirm('This will permanently delete the file. Continue?', 'Warning', {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => {
        staffApi.deleteById(id).then(response =>{
          const resp = response.data
          if (resp.flag){
            this.fetchData()
          }
        })
        this.$message({
          type: 'success',
          message: 'Delete completed'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: 'Delete canceled'
        });
      });
    },
    handleEdit(id){
      this.handlerAdd()
      staffApi.getById(id).then(response =>{
        const resp = response.data
        if (resp.flag){
          this.pojo = resp.data
        }else{
          this.$message({
            message: resp.message,
            type: 'warning'
          })
        }
      })
    },
    UpdateData(formName){
      console.log("updateData",this.pojo.id)
      this.$refs[formName].validate(valid => {
        if (valid){
          memberApi.Update(this.pojo).then(response =>{
            const resp = response.data
            if (resp.flag){
              this.fetchData()
              this.dialogFormVisible = false
            }else{
              this.$message({
                message: resp.message,
                type: 'warning'
              })
            }
          })
        }else{
          return false
        }
      })
    },
  }
}
</script>

<style scoped>

</style>