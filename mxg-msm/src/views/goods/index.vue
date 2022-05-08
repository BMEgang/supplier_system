<template>
  <div>
    <el-form ref="searchForm" :inline="true" :model="searchMap" style="margin-top: 20px">
      <el-form-item prop="name">
        <el-input v-model="searchMap.name" placeholder="商品名称"></el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-input v-model="searchMap.code" placeholder="商品编号"></el-input>
      </el-form-item>
      <el-form-item prop="supplierName">
        <el-input readonly v-on:click.native="dialogSupplierVisible = true" v-model="searchMap.supplierName" placeholder="选择供应商"></el-input>
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
      <el-table-column type="index" label="序号" width="60"></el-table-column>
      <el-table-column prop="name" label="商品名称"></el-table-column>
      <el-table-column prop="code" label="商品编码"></el-table-column>
      <el-table-column prop="spec" label="商品规格"></el-table-column>
      <el-table-column prop="retailPrice" label="零售价"></el-table-column>
      <el-table-column prop="purchasePrice" label="进货价"></el-table-column>
      <el-table-column prop="storageNum" label="库存数量"></el-table-column>
      <el-table-column prop="supplierName" label="供应商"></el-table-column>

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

    <el-dialog title="选择供应商" :visible.sync="dialogSupplierVisible" width="500px">
      <supplier @option-supplier="optionSupplier" :isDialog="true"></supplier>
    </el-dialog>

    <el-dialog title="编辑商品" :visible.sync="dialogFormVisible" width="500px">
      <el-form :rules="rules" ref="pojoForm" label-width="100px" label-position="right" :model="pojo">
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="pojo.name"></el-input>
        </el-form-item>
        <el-form-item label="商品编码" prop="code">
          <el-input v-model="pojo.code"></el-input>
        </el-form-item>
        <el-form-item label="商品规格" prop="spec">
          <el-input v-model="pojo.spec"></el-input>
        </el-form-item>
        <el-form-item label="零售价" prop="retailPrice">
          <el-input v-model="pojo.retailPrice"></el-input>
        </el-form-item>
        <el-form-item label="进货价" prop="purchasePrice">
          <el-input v-model="pojo.purchasePrice"></el-input>
        </el-form-item>
        <el-form-item label="库存数量" prop="storageNum">
          <el-input
              v-model="pojo.storageNum"
          >

          </el-input>
        </el-form-item>
        <el-form-item label="供应商" prop="supplierName">
          <el-input v-model="pojo.supplierName" readonly
                    @click.native="editOptionSupplier"
                    placeholder="选择供应商"></el-input>
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
import goodsApi from "@/api/goods";
import supplier from "@/views/supplier";
import supplierApi from "@/api/supplier";

export default {
  components: {supplier},
  data(){
    return{
      list: [],
      pageSize: 10,
      currentPage: 1,
      total: 0,
      dialogFormVisible: false,
      dialogSupplierVisible:false,
      searchMap: {
        name: '',
        code: '',
        supplierName: ''
      },
      pojo: {
        id: null,
        supplierId: null,
        name: '',
        code: '',
        spec: '',
        retailPrice: 0.0,
        purchasePrice: 0.0,
        storageNum: 0,
        supplierName: ''
      },
      isEdit: false,
      rules: {
        name: [
          {required: true, message: "name cannot be empty", trigger: 'blur'}
        ],
        code: [
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
      goodsApi.search(this.currentPage,this.pageSize,this.searchMap).then(response =>{
        const resp = response.data.data
        // console.log(resp)
        this.list = resp.records
        this.total = resp.total
      })
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
        if (valid){
          goodsApi.add(this.pojo).then(response =>{
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
          return false;
        }

      })
    },
    UpdateData(formName){
      console.log("updateData",this.pojo.id)
      this.$refs[formName].validate(valid => {
        if (valid){
          goodsApi.Update(this.pojo).then(response =>{
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
    optionSupplier(currentRow){
      console.log('parent',currentRow)
      if (this.isEdit){
        this.pojo.supplierName = currentRow.name
        this.pojo.supplierId = currentRow.id
      }else{
        this.searchMap.supplierName = currentRow.name
        this.searchMap.supplierId = currentRow.id
      }
      this.isEdit=false
      this.dialogSupplierVisible=false
    },
    editOptionSupplier(){
      this.isEdit=true
      this.dialogSupplierVisible=true
    },
    handleEdit(id){
      this.handlerAdd()
      goodsApi.getById(id).then(response =>{
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
  }
}
</script>

<style scoped>

</style>