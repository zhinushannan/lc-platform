<template>

  <div>

    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          通用页面
        </el-breadcrumb-item>
        <el-breadcrumb-item>
          通用页面
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="container">

      <div style="margin-bottom: 10px">
        <el-button type="primary" icon="el-icon-plus">添加数据</el-button>
        <el-button type="danger" icon="el-icon-delete">删除选中</el-button>
      </div>


      <el-table
          :data="page.records"
          border
          style="width: 100%">
        <template v-for="item in visibleFields">
          <el-table-column
              :prop="item.logicFieldName"
              :label="item.businessFieldName">
          </el-table-column>
        </template>
        <el-table-column
            fixed="right"
            label="操作"
            width="100">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
            <el-button type="text" size="small">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>

    </div>

    <div>

      <el-dialog
          :title="dialog.title"
          :visible.sync="dialog.visible"
          width="30%">
        <span>{{ dialog.formData }}</span>
        <el-form :model="dialog.formData" status-icon ref="formData" label-width="75px" style="width: 80%; margin: auto" class="demo-ruleForm">
          <template v-for="item in dialog.formStructure">
            <el-form-item :label="item.businessFieldName" :prop="item.logicFieldName">
              <el-input v-model="dialog.formData[item.logicFieldName]" autocomplete="off"></el-input>
            </el-form-item>
          </template>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="closeAndReset">取 消</el-button>
          <el-button type="primary" @click="commit(dialog.opera)">确 定</el-button>
        </span>
      </el-dialog>

    </div>

  </div>

</template>
<script>
import Index from "./Index.js"

export default Index
</script>
<style scoped>

</style>