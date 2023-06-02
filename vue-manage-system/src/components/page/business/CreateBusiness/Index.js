import request from "@/utils/request";
import FieldComponent from "@/components/components/business/CreateBusiness/FieldComponent.vue";

export default {
    name: "Index",
    components: {FieldComponent},
    comments: {
        FieldComponent
    },
    data() {
        const checkTableLogicName = (rule, value, callback) => {
            if (!value) {
                return callback(new Error('表逻辑名不能为空'));
            }
            request({
                url: `/create-business/check-table-logic?logicName=${this.metaInfo.tableInfo.logicName}`,
                method: 'get'
            }).then((resp) => {
                if (resp.code !== 200) {
                    return callback(new Error('存在重复表逻辑名'));
                } else {
                    callback()
                }
            })
        }
        const checkTableBusinessName = (rule, value, callback) => {
            if (!value) {
                return callback(new Error('表业务名不能为空'));
            }
            request({
                url: `/create-business/check-table-logic?logicName=${this.metaInfo.tableInfo.businessName}`,
                method: 'get'
            }).then((resp) => {
                if (resp.code !== 200) {
                    return callback(new Error('存在重复表业务名'));
                } else {
                    callback()
                }
            })
        }
        return {
            dbType: [],
            active: 0,
            fieldInfoCount: 1,
            metaInfo: {
                tableInfo: {
                    logicName: '',
                    businessName: ''
                },
                fieldInfos: [
                    {
                        fieldLogicName: '',
                        fieldBusinessName: '',
                        fieldJdbcType: '',
                        fieldJdbcLength: '',
                        nullable: 'true'
                    }
                ],
            },
            metaInfoRule: {
                tableInfo: {
                    logicName: [
                        {validator: checkTableLogicName, trigger: 'blur'}
                    ],
                    businessName: [
                        {validator: checkTableBusinessName, trigger: 'blur'}
                    ]
                }
            },
            fullscreenLoading: false
        }
    },
    mounted() {
        let _this = this
        request({
            url: '/common/db-type',
            method: 'get'
        }).then((resp) => {
            _this.dbType = resp.data
        })
    },
    methods: {
        jump(step) {
            let tmp = this.active + step
            if (tmp === 1) {
                // todo 保存 表 和 字段信息
                this.fullscreenLoading = true;

                console.log(this.metaInfo)

                this.fullscreenLoading = false;

                // request({
                //     url: '/create-business/save-table-info',
                //     method: 'post',
                //     data: this.metaInfo
                // }).then((resp) => {
                //     console.log(resp)
                //     // if (tmp >= 0 && tmp <= 3) {
                //     //     this.active = tmp
                //     // }
                //     this.fullscreenLoading = false;
                // })
            }
        },
        finish() {
            alert("完成创建")
        },
        addField() {
            this.metaInfo.fieldInfos.push({
                fieldLogicName: '',
                fieldBusinessName: '',
                fieldJdbcType: '',
                fieldJdbcLength: '',
                nullable: 'true'
            })
        },
        removeField(index) {
            if (this.metaInfo.fieldInfos.length === 1) {
                this.$message.error("至少要保留一个字段")
                return
            }
            this.metaInfo.fieldInfos.splice(index, 1)
        },

        onDbTypeChange(item) {
            let _this = this
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
    }
}
