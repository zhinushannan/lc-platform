import request from "@/utils/request";
import FieldComponent from "@/components/components/business/CreateBusiness/FieldComponent.vue";
import ChoiceShowField from "@/components/components/business/CreateBusiness/ChoiceShowField.vue";
export default {
    name: "Index",
    components: {ChoiceShowField, FieldComponent},
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
                url: `/create-business/check-table-business?businessName=${this.metaInfo.tableInfo.tableBusinessName}`,
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
            active: 1,
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
            tableId: '1664525800188452866',
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
        onConfirm() {
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
                this.$message({
                    dangerouslyUseHTMLString: true,
                    message: message,
                    type: 'error',
                    duration: 0,
                    showClose: true
                });
                return
            }

            request({
                url: '/create-business/create-table',
                method: 'post',
                data: this.metaInfo
            }).then((resp) => {
                if (resp.code === 200) {
                    if (tmp >= 0 && tmp <= 3) {
                        this.active = tmp
                    }
                }
                this.tableId = resp.data
                this.fullscreenLoading = false;
            })
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
        }
    }
}