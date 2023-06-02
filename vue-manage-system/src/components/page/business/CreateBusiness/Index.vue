<template>

  <div>

    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          服务管理
        </el-breadcrumb-item>
        <el-breadcrumb-item>
          创建服务
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="container">
      <el-steps :active="active" finish-status="success" align-center>
        <el-step title="创建数据逻辑" description="创建数据库表和业务名称的对应关系"></el-step>
        <el-step title="选择可见字段" description="您想要在前端展示的字段"></el-step>
        <el-step title="选择搜索字段" description="选择您希望支持搜索的字段"></el-step>
      </el-steps>

      <!-- 创建数据逻辑 -->
      <div v-if="active === 0" style="width: 80%; margin: auto">
        <el-form ref="metaInfoForm" :model="metaInfo" label-width="100px" :rules="metaInfoRule">
          <el-divider>表信息</el-divider>
          <el-form-item label="表逻辑名称" prop="tableInfo.tableLogicName">
            <el-input v-model="metaInfo.tableInfo.tableLogicName" placeholder="请输入表逻辑名称，纯英文字符"></el-input>
          </el-form-item>
          <el-form-item label="表业务名称" prop="tableInfo.tableBusinessName">
            <el-input v-model="metaInfo.tableInfo.tableBusinessName" placeholder="请输入表业务名称"></el-input>
          </el-form-item>

          <div v-for="(item, index) in metaInfo.fieldInfos">
            <el-divider>字段{{ index + 1 }}
              <el-link @click="removeField(index)">(点击删除)</el-link>
            </el-divider>

            <FieldComponent :item="item" :db-type="dbType" />

          </div>


          <el-button style="margin-left: 50%;" @click="addField">添加一个字段</el-button>
        </el-form>
      </div>

      <!-- 选择可见字段 -->
      <div v-if="active === 1">
        选择可见字段
      </div>

      <!-- 选择搜索字段 -->
      <div v-if="active === 2">
        选择搜索字段
      </div>

      <!-- 按钮 -->
      <div>
        <!-- 上下步 -->
        <div>
          <el-button style="margin-top: 12px;" @click="jump(-1)" :disabled="active === 0">上一步</el-button>
          <el-button style="margin-top: 12px;" @click="jump(1)" :disabled="active === 2" v-if="active !== 2"
                     v-loading.fullscreen.lock="fullscreenLoading">
            下一步
          </el-button>
          <el-button style="margin-top: 12px;" @click="finish" v-if="active === 2">完成创建</el-button>
        </div>
      </div>


    </div>

  </div>

</template>

<script>
import Index from './Index.js'

export default Index
</script>

<style scoped>

</style>