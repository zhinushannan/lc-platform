<template>
  <div>

    <el-form ref="itemData" :model="item" label-width="100px" :rules="rules">

      <el-form-item label="逻辑字段名称" prop="fieldLogicName">
        <el-input v-model="item.fieldLogicName" placeholder="请输入字段的逻辑名称，纯英文字符"></el-input>
      </el-form-item>
      <el-form-item label="业务字段名称" prop="fieldBusinessName">
        <el-input v-model="item.fieldBusinessName" placeholder="请输入字段的业务名称"></el-input>
      </el-form-item>

      <el-form-item label="字段类型" prop="fieldJdbcType">
        <el-select v-model="item.fieldJdbcType" placeholder="请选择字段类型" @change="onDbTypeChange(item)">
          <el-option
              v-for="type in dbType"
              :key="type.jdbcType"
              :label="type.jdbcType"
              :value="type.jdbcType">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="字段长度">
        <el-input v-model="item.fieldJdbcLength" placeholder="请填写字段长度，若不填则使用默认长度"></el-input>
      </el-form-item>

      <el-form-item label="是否允许为空" prop="nullable">
        <el-radio v-model="item.nullable" label="true">是</el-radio>
        <el-radio v-model="item.nullable" label="false">否</el-radio>
      </el-form-item>

      <el-form-item label="前端是否展示">
        <el-radio v-model="item.enableShow" label="true">是</el-radio>
        <el-radio v-model="item.enableShow" label="false">否</el-radio>
      </el-form-item>

      <el-form-item label="搜索模式">
        <el-select v-model="item.searchMode" placeholder="请选择">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>

    </el-form>

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  props: {
    item: {},
    dbType: []
  },
  data() {
    const checkFieldLogicName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('字段逻辑名不能为空'));
      }
      callback()
    }
    const checkFieldBusinessName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('字段业务名不能为空'));
      }
      callback()
    }
    const checkFieldJdbcType = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('字段类型不能为空'));
      }
      callback()
    }
    const checkFieldNullable = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请选择是否允许为空'));
      }
      callback()
    }
    return {
      rules: {
        fieldLogicName: [
          {validator: checkFieldLogicName, trigger: 'blur'}
        ],
        fieldBusinessName: [
          {validator: checkFieldBusinessName, trigger: 'blur'}
        ],
        fieldJdbcType: [
          {validator: checkFieldJdbcType, trigger: 'blur'}
        ],
        nullable: [
          {validator: checkFieldNullable, trigger: 'blur'}
        ]
      },
      options: [],
      dfTypeProps: []
    }
  },
  methods: {
    onDbTypeChange(item) {
      let _this = this
      console.log(item)
      let jdbcType = item.fieldJdbcType
      for (let i in _this.dbType) {
        let type = _this.dbType[i]
        if (jdbcType === type.jdbcType) {
          if (type.needLength) {
            item.fieldJdbcLength = type.defaultLength
          } else {
            item.fieldJdbcLength = ''
          }
        }
      }
    }
  },
  mounted() {
    request({
      url: `/common/select-mode`,
      method: 'get'
    }).then((resp) => {
      this.options = resp.data
    })
  }
}

</script>


<style scoped>

</style>