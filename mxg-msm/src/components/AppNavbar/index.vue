<template>
  <div>
    <div class="navbar">
      <el-menu default-active="/home" class="el-menu-vertical-demo" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b" :router="true">
        <el-menu-item index="/home">
          <i class="el-icon-s-home"></i>
          <span slot="title">main page</span>
        </el-menu-item>
        <el-menu-item index="/member/">
          <i class="el-icon-user-solid"></i>
          <span slot="title">VIP management</span>
        </el-menu-item>
        <el-menu-item index="/supplier/">
          <i class="el-icon-s-cooperation"></i>
          <span slot="title">supplier management</span>
        </el-menu-item>
        <el-menu-item index="/goods/">
          <i class="el-icon-s-goods"></i>
          <span slot="title">goods management</span>
        </el-menu-item>
        <el-menu-item index="/staff/">
          <i class="el-icon-user"></i>
          <span slot="title">staff management</span>
        </el-menu-item>

      </el-menu>
    </div>
  </div>
</template>

<style>
.el-menu{
  border-right: none;
}
</style>