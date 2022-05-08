<template>
  <div>
    <el-form ref="searchForm" :inline="true" :model="searchMap" style="margin-top: 20px">
      <el-form-item prop="name">
        <el-input v-model="searchMap.name" placeholder="Name"></el-input>
      </el-form-item>
      <el-form-item prop="linkman" v-if="!isDialog">
        <el-input v-model="searchMap.linkman" placeholder="linkman"></el-input>
      </el-form-item>
      <el-form-item prop="mobile" v-if="!isDialog">
        <el-input v-model="searchMap.mobile" placeholder="mobile"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="fetchData">Query</el-button>
        <el-button v-if="!isDialog" type="primary" @click="handlerAdd">add</el-button>
        <el-button v-if="!isDialog" type="primary" @click="resetForm('searchForm')">reset</el-button>
      </el-form-item>
    </el-form>


    <el-table
        :highlight-current-row="isDialog"
        @current-change="handleCurrentChange1"
        :data="list"
        style="width: 100%">
      <el-table-column type="index" label="Num" width="60"></el-table-column>
      <el-table-column prop="name" label="supplier name"></el-table-column>
      <el-table-column prop="linkman" label="linkman"></el-table-column>
      <el-table-column v-if="!isDialog" prop="mobile" label="mobile"></el-table-column>
      <el-table-column v-if="!isDialog" prop="remark" label="remark"></el-table-column>
      <el-table-column v-if="!isDialog" label="Operations" width="150">
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
        :layout="!isDialog ? 'total, sizes, prev, pager, next, jumper' : 'prev, pager, next'"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-sizes="[10, 20, 50]"
        :page-size="pageSize"
        :total="total">
    </el-pagination>

    <el-dialog title="Add supplier" :visible.sync="dialogFormVisible" width="500px">
      <el-form :rules="rules" ref="pojoForm" label-width="100px" label-position="right" :model="pojo">
        <el-form-item label="Supplier" prop="name">
          <el-input v-model="pojo.name"></el-input>
        </el-form-item>
        <el-form-item label="linkman" prop="linkman">
          <el-input v-model="pojo.linkman"></el-input>
        </el-form-item>
        <el-form-item label="phone" prop="mobile">
          <el-input v-model="pojo.mobile"></el-input>
        </el-form-item>
        <el-form-item label="notes" prop="remark">
          <el-input type="textarea" v-model="pojo.remark"></el-input>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="pojo.id === undefined ? addData('pojoForm') : UpdateData('pojoForm')">Confirm</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import supplierApi from '@/api/supplier'
import memberApi from "@/api/member";

export default {
  props: {
    isDialog: Boolean
  },
  data(){
    return{
      list: [],
      pageSize: 10,
      currentPage: 1,
      total: 0,
      dialogFormVisible: false,
      searchMap: {
        name: '',
        linkman: '',
        mobile: ''
      },
      pojo: {
        id: null,
        name: '',
        linkman: '',
        mobile: '',
        remark: '',
      },
      rules: {
        name: [
          {required: true, message: "name cannot be empty", trigger: 'blur'}
        ],
        linkman: [
          {required: true, message: "linkman cannot be empty", trigger: 'blur'}
        ],

      }
    }
  },

  created() {
    this.fetchData()
  },

  methods:{
    fetchData() {
      supplierApi.search(this.currentPage,this.pageSize,this.searchMap).then(response =>{
        const resp = response.data.data
        this.list = resp.rows
        this.total = resp.total
        console.log(this.list)
      })
    },
    handleEdit(id){
      this.handlerAdd()
      supplierApi.getById(id).then(response =>{
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
    handleDelete(id){
      console.log("delete ", id)
      this.$confirm('This will permanently delete the file. Continue?', 'Warning', {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => {
        supplierApi.deleteById(id).then(response =>{
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
    handleSizeChange(val){
      this.pageSize = val;
      this.fetchData();
    },
    handleCurrentChange(val){
      this.currentPage = val;
      this.fetchData();
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
          supplierApi.add(this.pojo).then(response =>{
            const resp = response.data
            if(resp.flag){
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
    handleCurrentChange1(currentRow){
      this.$emit('option-supplier',currentRow)
    }

  }
}
</script>

<style scoped>

</style>