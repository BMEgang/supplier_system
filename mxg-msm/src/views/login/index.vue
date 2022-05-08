<template>
  <div class="login-container">
    <el-form ref="form" :rules="rules" :model="form" label-width="120px" class="login-form">
      <h2 align="center">manage system</h2>
      <el-form-item label="Account" prop="username">
        <el-input v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item label="password" prop="password">
        <el-input v-model="form.password" type="password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('form')">Login</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {login, getUserInfo} from "@/api/login";

export default {
  data() {
    return {
      form: {
        username: '',
        password: '',
      },
      rules:{
        username: [
          { required: true, message: 'Please input user name', trigger: 'blur' },
        ],
        password: [
          { required: true, message: 'Please input password', trigger: 'blur' },
        ]
      }
    }
  },
  methods: {
    onSubmit() {
      console.log('submit!');
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if(valid){
            login(this.form.username,this.form.password).then(response =>{
              const resp = response.data
              if (resp.flag){
                getUserInfo(resp.data.token).then(response =>{
                  const respUser = response.data

                  if (respUser.flag){

                    console.log("userinfo is ", respUser.data)

                    localStorage.setItem('mxg-msm-user', JSON.stringify(respUser.data))
                    localStorage.setItem('mxg-msm-token', respUser.data.token)
                    //go to main page
                    this.$router.push('/');
                  }else{
                    this.$message({
                      message: respUser.message,
                      type:'warning'
                    })
                  }


                })
              }else {
                alert(resp.message);
              }
            })
        }else {
          console.log("validate fail");
          return false;
        }
      });
    }
  }
}
</script>

<style>
.login-form{
  width: 350px;
  margin: 160px auto;
  background-color: aquamarine;
  padding: 30px;
  border-radius: 20px;
}
.login-container{
  position: absolute;
  width: 100%;
  height: 100%;
  background: url("img.png");
}
</style>