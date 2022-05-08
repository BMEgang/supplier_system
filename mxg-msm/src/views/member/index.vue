<template>
  <div>
<!--    :inline="true"-->
    <el-form ref="searchForm" :inline="true" :model="searchMap" style="margin-top: 20px">
      <el-form-item prop="cardNum">
        <el-input v-model="searchMap.cardNum" placeholder="Card Number"></el-input>
      </el-form-item>
      <el-form-item prop="name">
        <el-input v-model="searchMap.name" placeholder="User name"></el-input>
      </el-form-item>
      <el-form-item prop="payType">
        <el-select v-model="searchMap.payType" placeholder="pay type">
          <el-option v-for="option in payTypeOptions"
          :key="option.type"
          :label="option.name"
          :value="option.type"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="birthday">
        <el-date-picker
            value-format="yyyy-MM-dd"
            v-model="searchMap.birthday"
            type="date"
            placeholder="birthday">
        </el-date-picker>
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
      <el-table-column prop="cardNum" label="cardNum"></el-table-column>
      <el-table-column prop="name" label="name"></el-table-column>
      <el-table-column prop="birthday" label="birthday"></el-table-column>
      <el-table-column prop="phone" label="phone"></el-table-column>
      <el-table-column prop="integral" label="integral"></el-table-column>
      <el-table-column prop="money" label="money"></el-table-column>
      <el-table-column prop="payType" label="payType">
        <template slot-scope="scope">
          <span>{{scope.row.payType | payTypeFilter}}</span>
        </template>
      </el-table-column>

      <el-table-column prop="address" label="address"></el-table-column>
      <el-table-column
          label="Operations" width="150">
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
        <el-form-item label="card number" prop="cardNum">
          <el-input v-model="pojo.cardNum"></el-input>
        </el-form-item>
        <el-form-item label="name" prop="name">
          <el-input v-model="pojo.name"></el-input>
        </el-form-item>
        <el-form-item label="birthday" prop="birthday">
          <el-date-picker
              value-format="yyyy-MM-dd"
              v-model="pojo.birthday"
              type="date"
              placeholder="birthday">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="mobile phone" prop="phone">
          <el-input v-model="pojo.phone"></el-input>
        </el-form-item>
        <el-form-item label="money" prop="money">
          <el-input v-model="pojo.money"></el-input>
        </el-form-item>
        <el-form-item label="credit" prop="integral">
          <el-input v-model="pojo.integral"></el-input>
        </el-form-item>
        <el-form-item label="payType" prop="payType">
          <el-select v-model="pojo.payType" placeholder="payType">
            <el-option v-for="option in payTypeOptions"
                       :key="option.type"
                       :label="option.name"
                       :value="option.type"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="address" prop="address">
          <el-input type="textarea" v-model="pojo.address"></el-input>
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
import memberApi from "@/api/member";

const payTypeOptions = [
  {type: '1', name: 'cash'},
  {type: '2', name: 'wechat'},
  {type: '3', name: 'AliPay'},
  {type: '4', name: 'card'},
]

export default {

  data(){
    return{
      list: [],
      total: 0,
      currentPage: 1,
      pageSize: 10,
      searchMap: {
        cardNum: '',
        name: '',
        patType: '',
        birthday: ''
      },
      payTypeOptions,
      dialogFormVisible: false,
      pojo: {
        id: null,
        cardNum: '',
        name: '',
        birthday: '',
        phone: '',
        money: 0,
        integral: 0,
        payType: '',
        address: ''
      },
      rules: {
        cardNum: [
          {required: true, message: "cardNum cannot be empty", trigger: 'blur'}
        ],
        name: [
          {required: true, message: "name cannot be empty", trigger: 'blur'}
        ],
        payType: [
          {required: true, message: "payType cannot be empty", trigger: 'change'}
        ]
      }
    }
  },

  created() {
    this.fetchData()
  },

  methods: {
    handleSizeChange(val){
      this.pageSize = val;
      this.fetchData();
    },
    handleCurrentChange(val){
      this.currentPage = val;
      this.fetchData();
    },
    fetchData(){
      memberApi.search(this.currentPage,this.pageSize,this.searchMap).then(response =>{
        const resp = response.data
        this.list = resp.data.rows
        this.total = resp.data.total
      })
    },
    handleEdit(id){
      this.handlerAdd()
      memberApi.getById(id).then(response =>{
        const resp = response.data
        if (resp.flag){
          this.pojo = resp.data[0]
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
    handleDelete(id){
      console.log("delete ", id)
      this.$confirm('This will permanently delete the file. Continue?', 'Warning', {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => {
        memberApi.deleteById(id).then(response =>{
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
    resetForm(formName) {
      console.log("重置",formName)
      this.$refs[formName].resetFields();
    },
    addData(formName){
      this.$refs[formName].validate(valid =>{
        console.log("add",this.pojo.id)

        if (valid){
          memberApi.add(this.pojo).then(response =>{
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
    handlerAdd(){
      this.pojo = {}
      this.dialogFormVisible = true

      if (this.$refs['pojoForm']!==undefined) {
        this.$refs['pojoForm'].resetFields()
        // this.pojo.id = null
      }

    }
  },


  filters: {
    payTypeFilter(type){
      const payObj = payTypeOptions.find(obj => obj.type === type)
      return payObj ? payObj.name : null
    }
  }
}
</script>

<style scoped>

</style>