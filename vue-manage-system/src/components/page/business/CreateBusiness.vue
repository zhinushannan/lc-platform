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
                <el-form ref="form" :model="dataLogic" label-width="100px">
                    <el-divider>表信息</el-divider>
                    <el-form-item label="表逻辑名称">
                        <el-input v-model="dataLogic.name"></el-input>
                    </el-form-item>
                    <el-form-item label="表业务名称">
                        <el-input v-model="dataLogic.name"></el-input>
                    </el-form-item>

                    <div v-for="(item, index) in dataLogic.fieldInfos">
                        <el-divider>字段{{ index + 1 }}<el-link @click="removeField(index)">(点击删除)</el-link></el-divider>
                        <el-form-item label="逻辑字段名称">
                            <el-input v-model="dataLogic.name"></el-input>
                        </el-form-item>
                        <el-form-item label="业务字段名称">
                            <el-input v-model="dataLogic.name"></el-input>
                        </el-form-item>
                        <el-form-item label="字段类型">
                            <el-input v-model="dataLogic.name"></el-input>
                        </el-form-item>
                        <el-form-item label="字段长度">
                            <el-input v-model="dataLogic.name"></el-input>
                        </el-form-item>
                        <el-form-item label="是否允许为空">
                            <el-input v-model="dataLogic.name"></el-input>
                        </el-form-item>
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
                    <el-button style="margin-top: 12px;" @click="jump(1)" :disabled="active === 2" v-if="active !== 2">
                        下一步
                    </el-button>
                    <el-button style="margin-top: 12px;" @click="finish" v-if="active === 2">完成创建</el-button>
                </div>
            </div>


        </div>

    </div>

</template>

<script>
export default {
    name: "CreateBusiness",
    data() {
        return {
            active: 0,
            fieldInfoCount: 1,
            dataLogic: {
                tableInfo: {},
                fieldInfos: [
                    {},
                ],
            }

        }
    },
    methods: {
        jump(step) {
            let tmp = this.active + step
            if (tmp >= 0 && tmp <= 3) {
                this.active = tmp
            }
        },
        finish() {
            alert("完成创建")
        },
        addField() {
            this.dataLogic.fieldInfos.push({})
        },
        removeField(index) {
            if (this.dataLogic.fieldInfos.length === 1) {
                this.$message.error("至少要保留一个字段")
                return
            }
            this.dataLogic.fieldInfos.splice(index, 1)
        }
    }
}
</script>

<style scoped>

</style>