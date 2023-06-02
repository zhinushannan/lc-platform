<template>

  <div>

    <el-card class="box-card" v-for="item in fieldList">
      <div slot="header" class="clearfix">
        <span>{{ item['logicFieldName'] }} ({{ item['businessFieldName'] }})</span>
        <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
      </div>

      <div>

        <el-form label-position="right" label-width="100px" :model="item">
          <el-form-item label="名称">
            <el-switch
                v-model="item.enableShow"
                active-text="前端展示"
                inactive-text="前端隐藏"
                @change="enableShowChange(item)"
            >
            </el-switch>
          </el-form-item>
          <el-form-item label="活动区域">
            <el-select v-model="item.searchMode" placeholder="请选择" @change="searchModeChange(item)">
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

    </el-card>


  </div>

</template>

<script>

import request from "@/utils/request";

export default {
  props: {
    tableId: ""
  },
  data() {
    return {
      fieldList: [],
      options: [],
      value1: ''
    }
  },
  methods: {
    searchModeChange(item) {
      request({
        url: "/create-business/select-search-field",
        method: "post",
        data: {
          tableId: this.$props.tableId,
          fieldId: item.id,
          selectMode: item.searchMode
        }
      })
    },
    enableShowChange(item) {
      request({
        url: "/create-business/select-show-field",
        method: "post",
        data: {
          tableId: this.$props.tableId,
          fieldId: item.id,
          showEnable: item.enableShow
        }
      })
    }
  },
  mounted() {
    request({
      url: `/business/fields?tableId=${this.$props.tableId}`,
      method: 'get',
    }).then((resp) => {
      this.fieldList = resp.data
    })

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