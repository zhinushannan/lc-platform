import request from "@/utils/request";
import FieldComponent from "@/components/components/business/CreateBusiness/FieldComponent.vue";
import ChoiceShowField from "@/components/components/business/CreateBusiness/ChoiceShowField.vue";
import bus from "@/components/common/bus";

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
            metaInfo: {
                tableInfo: {
                    tableLogicName: '',
                    tableBusinessName: ''
                },
                fieldInfos: [],
            },
            dialog: {
                visible: false,
                title: '',
                operate: '',
                item: {},
                dbType: []
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
            _this.dialog.dbType = resp.data
        })
    },
    methods: {
        onConfirm() {

            let tableInfo = this.metaInfo.tableInfo
            if (!tableInfo.tableLogicName) {
                this.$message.error('表逻辑名不能为空')
                return
            }

            if (!tableInfo.tableBusinessName) {
                this.$message.error('表逻辑名不能为空')
                return
            }

            if (this.metaInfo.fieldInfos.length === 0) {
                this.$message.error('至少要有一个字段')
                return
            }

            request({
                url: '/create-business/create-table',
                method: 'post',
                data: this.metaInfo
            }).then((resp) => {
                if (resp.code === 200) {
                    this.tableId = resp.data
                    this.fullscreenLoading = false;
                    this.$message.success('新增成功')

                    this.metaInfo = {
                        tableInfo: {
                            tableLogicName: '',
                            tableBusinessName: ''
                        },
                        fieldInfos: [],
                    }

                    bus.$emit('close_current_tags')
                } else {
                    this.$message.error(resp.message)
                }
            })
        },
        addField() {
            this.dialog.item = {
                fieldLogicName: '',
                fieldBusinessName: '',
                fieldJdbcType: '',
                fieldJdbcLength: '',
                nullable: 'true',
                enableShow: 'true',
                searchMode: 0
            }
            this.dialog.title = '新增字段'
            this.dialog.operate = 'add'
            this.dialog.visible = true
        },
        handleEdit(scope) {
            let index = scope.$index
            this.dialog.item = JSON.parse(JSON.stringify(scope.row))
            this.dialog.item['index'] = index
            this.dialog.title = '修改字段'
            this.dialog.operate = 'modify'
            this.dialog.visible = true
        },
        removeField(index) {
            if (this.metaInfo.fieldInfos.length === 1) {
                this.$message.error("至少要保留一个字段")
                return
            }
            this.metaInfo.fieldInfos.splice(index, 1)
        },
        commitDialog() {
            let currentItem = this.dialog.item

            if (!currentItem.fieldLogicName) {
                this.$message.error('字段逻辑名不能为空')
                return
            }

            if (!currentItem.fieldBusinessName) {
                this.$message.error('字段业务名不能为空')
                return
            }

            if (!currentItem.fieldJdbcType) {
                this.$message.error('字段类型不能为空')
                return
            }

            let fieldInfos = this.metaInfo.fieldInfos
            let message = []
            for (let i in fieldInfos) {
                if (this.dialog.operate === 'modify' && i == this.dialog.item.index) {
                    continue
                }
                if (currentItem.fieldLogicName === fieldInfos[i].fieldLogicName && message.indexOf('存在重复逻辑字段名') === -1) {
                    message.push('存在重复逻辑字段名')
                }
                if (currentItem.fieldBusinessName === fieldInfos[i].fieldBusinessName && message.indexOf('存在重复业务字段名') === -1) {
                    message.push('存在重复业务字段名')
                }
            }
            if (message.length !== 0) {
                this.$message({
                    dangerouslyUseHTMLString: true,
                    message: message.join('<br/><br/>'),
                    type: 'error'
                });
                return
            }

            if (this.dialog.operate === 'add') {
                this.metaInfo.fieldInfos.push(JSON.parse(JSON.stringify(this.dialog.item)))
            } else {
                this.$set(this.metaInfo.fieldInfos, this.dialog.item.index, JSON.parse(JSON.stringify(this.dialog.item)))
            }
            this.closeDialog()
        },
        closeDialog() {
            this.dialog.visible = false
            this.dialog.title = ''
            this.dialog.operate = ''
            this.dialog.item = {}
        },
        handleClose(done) {
            this.$confirm('关闭后未保存的数据将丢失，是否关闭？')
                .then(_ => {
                    this.closeDialog()
                    done();
                })
                .catch(_ => {
                });
        }
    }
}