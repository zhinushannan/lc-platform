<template>

  <div>

    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          服务管理
        </el-breadcrumb-item>
        <el-breadcrumb-item>
          服务列表
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="container">
      <el-table
          :data="page.records"
          border
          style="width: 100%">
        <el-table-column
            fixed
            prop="logicTableName"
            label="表逻辑名">
        </el-table-column>
        <el-table-column
            prop="businessTableName"
            label="表业务名">
        </el-table-column>
        <el-table-column
            prop="createTime"
            :formatter="createTimeFormatter"
            label="创建时间">
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作">
          <template slot-scope="scope">
            <el-button @click="editBusiness(scope.row)" type="text" size="small">编辑业务</el-button>
            &nbsp;
            <el-popconfirm
                title="删除后所关联的路由、数据等都会被清空且不可恢复，是否确认删除？"
                @confirm="delTable(scope.row)"
            >
              <el-button slot="reference" type="text" size="small" style="color: red">删除表</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>

  </div>

</template>

<script>
import Index from "./Index.js"

export default Index
</script>

<style scoped>

</style>