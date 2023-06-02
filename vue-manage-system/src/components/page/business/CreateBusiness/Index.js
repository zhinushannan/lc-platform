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
                url: `/create-business/check-table-logic?logicName=${this.metaInfo.tableInfo.tableLogicName}`,
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
                url: `/create-business/check-table-logic?logicName=${this.metaInfo.tableInfo.tableBusinessName}`,
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
                    tableLogicName: '',
                    tableBusinessName: ''
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
                    tableLogicName: [
                        {validator: checkTableLogicName, trigger: 'blur'}
                    ],
                    tableBusinessName: [
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
                // this.fullscreenLoading = true;

                let message = ""

                let fields = this.metaInfo.fieldInfos
                // 字段 逻辑名 重复校验
                let logicName = {}
                for (let i in fields) {
                    if (!logicName[fields[i]['fieldLogicName']]) {
                        logicName[fields[i]['fieldLogicName']] = 0
                    }
                    logicName[fields[i]['fieldLogicName']]++
                }
                for (let i in logicName) {
                    if (logicName[i] > 1) {
                        if (message === "") {
                            message = "存在重复字段逻辑名："
                        }
                        message += i + ", "
                    }
                }
                if (message !== "") {
                    message = message.substring(0, message.length - 2)
                    message += "<br/><br/>"
                }
                // 字段 业务名 重复校验
                let businessName = {}
                for (let i in fields) {
                    if (!businessName[fields[i]['fieldBusinessName']]) {
                        businessName[fields[i]['fieldBusinessName']] = 0
                    }
                    businessName[fields[i]['fieldBusinessName']]++
                }
                for (let i in businessName) {
                    if (businessName[i] > 1) {
                        if (message.endsWith("<br/><br/>")) {
                            message += "存在重复字段业务名："
                        }
                        message += i + ", "
                    }
                }
                if (message !== "") {
                    message = message.substring(0, message.length - 2)

                    console.log(message)

                    this.$message({
                        dangerouslyUseHTMLString: true,
                        message: message,
                        type: 'error',
                        duration: 0,
                        showClose: true
                    });
                    return
                }


                // 字段 长度 合法校验

                // request({
                //     url: '/create-business/save-table-info',
                //     method: 'post',
                //     data: this.metaInfo
                // }).then((resp) => {
                //     console.log(this.metaInfo)
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
