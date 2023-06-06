<template>

  <div>

    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          服务管理
        </el-breadcrumb-item>
        <el-breadcrumb-item>
          服务绑定
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>


    <div class="container">

      <div style="margin-bottom: 10px">
        <el-button type="primary" icon="el-icon-plus" @click="addDir()">新增目录</el-button>
        <el-button type="primary" icon="el-icon-plus" @click="addPath()">新增路径</el-button>
      </div>

      <el-table
          :data="page.records"
          style="width: 100%;margin-bottom: 20px;"
          row-key="id"
          border
          :tree-props="{children: 'children', hasChildren: true}">
        <el-table-column
            prop="name"
            label="目录名称">
        </el-table-column>
        <el-table-column
            prop="prefix"
            label="目录前缀/路径">
        </el-table-column>
        <el-table-column
            prop="createTime"
            :formatter="dataFormatter"
            label="创建时间">
        </el-table-column>
        <el-table-column
            prop="updateTime"
            :formatter="dataFormatter"
            label="更新时间">
        </el-table-column>

      </el-table>
    </div>

    <!-- 新增/修改 目录/路径 的弹框 -->
    <div>
      <el-dialog
          :title="dialog.title"
          :visible.sync="dialog.visible"
          width="30%"
          :before-close="handleClose">
        <el-form label-position="right" label-width="100px" :model="dialog.data">
          <el-form-item label="选择目录" v-if="dialog.operate === 'path'">
            <el-select
                v-model="dialog.data.parentId"
                filterable
                remote
                reserve-keyword
                placeholder="请输入关键词"
                :remote-method="dirsData"
                :loading="dirs.loading">
              <el-option
                  v-for="item in dirs.records"
                  :key="item.id"
                  :label="item.name + '(' + item.prefix + ')'"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="选择数据表" v-if="dialog.operate === 'path'">
            <el-select
                v-model="dialog.data.tableMetaInfoId"
                filterable
                remote
                reserve-keyword
                placeholder="请输入关键词"
                :remote-method="tableData"
                :loading="table.loading"
            >
              <el-option
                  v-for="item in table.records"
                  :key="item.id"
                  :label="item.logicTableName + '(' + item.businessTableName + ')'"
                  :value="item.id"
                  :disabled="item.pathBindResp !== null"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item :label="dialog.operate === 'path' ? '路径名称' : '目录名称'">
            <el-input v-model="dialog.data.name"></el-input>
          </el-form-item>
          <el-form-item label="目录前缀" v-if="dialog.operate === 'dir'">
            <el-input v-model="dialog.data.prefix"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="handleClose">取 消</el-button>
          <el-button type="primary" @click="commit()">确 定</el-button>
        </span>
      </el-dialog>
    </div>

  </div>

</template>
<script>
import Index from "./Index.js"

export default Index;
</script>
<style scoped>

</style>