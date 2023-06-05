<template>

  <div>

    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          服务管理
        </el-breadcrumb-item>
        <el-breadcrumb-item>
          创建数据表
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>


    <div class="container">

      <div style="width: 80%; margin: auto">
        <el-form ref="metaInfoForm" :model="metaInfo" label-width="100px" :rules="metaInfoRule">
          <el-divider>表信息</el-divider>
          <el-form-item label="表逻辑名称" prop="tableInfo.tableLogicName">
            <el-input v-model="metaInfo.tableInfo.tableLogicName" placeholder="请输入表逻辑名称，纯英文字符"></el-input>
          </el-form-item>
          <el-form-item label="表业务名称" prop="tableInfo.tableBusinessName">
            <el-input v-model="metaInfo.tableInfo.tableBusinessName" placeholder="请输入表业务名称"></el-input>
          </el-form-item>

          <el-divider>字段信息</el-divider>

          <el-button @click="addField" style="margin-bottom: 10px">添加一个字段</el-button>

          <el-table
              :data="metaInfo.fieldInfos"
              border
              style="width: 100%">
            <el-table-column
                prop="fieldLogicName"
                label="逻辑名称">
            </el-table-column>
            <el-table-column
                prop="fieldBusinessName"
                label="业务名称">
            </el-table-column>
            <el-table-column
                prop="fieldJdbcType"
                label="字段类型">
            </el-table-column>
            <el-table-column
                prop="fieldJdbcLength"
                label="字段长度">
            </el-table-column>
            <el-table-column
                prop="nullable"
                label="是否允许为空">
            </el-table-column>
            <el-table-column
                prop="enableShow"
                label="前端是否展示">
            </el-table-column>
            <el-table-column
                prop="searchMode"
                label="搜索模式">
            </el-table-column>
            <el-table-column
                fixed="right"
                label="操作"
                width="100">
              <template slot-scope="scope">
                <el-button @click="handleClick(scope.row)" type="text" size="small">移除</el-button>
                <el-button type="text" size="small">编辑</el-button>
              </template>
            </el-table-column>
          </el-table>

        </el-form>


        <el-popconfirm
            title="提交后表结构不能修改，是否确认提交？"
            @confirm="onConfirm"
        >
          <el-button slot="reference" type="primary" style="margin-top: 10px">创建数据表</el-button>
        </el-popconfirm>

      </div>

    </div>

    <!-- 添加/修改字段的弹框 -->
    <div>
      <el-dialog
          :title="dialog.title"
          :visible.sync="dialog.visible"
          width="50%"
          :before-close="handleClose">

        <FieldComponent :item="dialog.item" :db-type="dialog.dbType"/>

        <span slot="footer" class="dialog-footer">
          <el-button @click="handleClose">取 消</el-button>
          <el-button type="primary" @click="commitDialog">确 定</el-button>
        </span>
      </el-dialog>
    </div>

  </div>

</template>
<script>
import Index from "@/components/page/business/CreateTable/Index.js";

export default Index
</script>
<style scoped>

</style>