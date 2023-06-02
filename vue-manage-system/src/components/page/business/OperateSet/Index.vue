<template>
  <div>


    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          服务管理
        </el-breadcrumb-item>
        <el-breadcrumb-item>
          设置操作选项
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>


    <div class="container">
      <ChoiceShowField :table-id="tableId"/>
    </div>

  </div>
</template>

<script>
import Index from "./Index.js";

export default Index
</script>

<style scoped>

</style>