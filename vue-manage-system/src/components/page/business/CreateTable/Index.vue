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

          <div v-for="(item, index) in metaInfo.fieldInfos">
            <el-divider>字段{{ index + 1 }}
              <el-link @click="removeField(index)">(点击删除)</el-link>
            </el-divider>

            <FieldComponent :item="item" :db-type="dbType"/>

          </div>


          <el-button style="margin-left: 50%;" @click="addField">添加一个字段</el-button>
        </el-form>


        <el-popconfirm
            title="提交后表结构不能修改，是否确认提交？"
            @confirm="onConfirm"
        >
            <el-button slot="reference" type="primary" plain>创建数据表</el-button>
        </el-popconfirm>

      </div>

    </div>

  </div>



</template>
<script>
import Index from "@/components/page/business/CreateTable/Index.js";

export default Index
</script>
<style scoped>

</style>