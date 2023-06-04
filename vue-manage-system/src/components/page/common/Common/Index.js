import request from "@/utils/request";

export default {
    name: "Index",
    data() {
        return {
            tableId: '',
            tableLogicName: 'test',
            page: {
                current: 1,
                size: 10,
                records: [],
                total: 0
            },
            // todo rules 校验
            dialog: {
                visible: false,
                title: '新增',
                opera: 'insert',
                formStructure: [],
                formData: {},
                rules: {}
            },
            visibleFields: []
        }
    },
    methods: {
        list() {
            request({
                url: `/crud/${this.tableLogicName}/page`,
                method: 'post',
                data: this.page
            }).then((resp) => {
                console.log(resp)
                this.page = resp.data
            })
        },
        closeAndReset() {
            this.dialog.visible = false
            this.dialog.formData = JSON.parse(JSON.stringify(this.dialog.emptyFormData))
        },
        commit(opera) {
            if (opera === 'insert') {
                this.insert()
            } else if (opera === 'update') {
                this.update()
            }
        },
        insert() {
            request({
                url: `/crud/${this.tableLogicName}`,
                method: 'post',
                data: this.dialog.formData
            }).then((resp) => {
                console.log(resp)
            })
        },
        update() {
            request({
                url: `/crud/${this.tableLogicName}`,
                method: 'put',
                data: this.dialog.formData
            }).then((resp) => {
                console.log(resp)
            })
        },
        addRecord() {
            this.dialog.visible = true
            this.dialog.title = '新增'
            this.dialog.opera = 'insert'
            this.dialog.formData = {}
        },
        modify(row) {
            this.dialog.visible = true
            this.dialog.title = '修改'
            this.dialog.opera = 'update'
            this.dialog.formData = JSON.parse(JSON.stringify(row))
        },
        handleSizeChange(val) {
            this.page.size = val
            this.list()
        },
        handleCurrentChange(val) {
            this.page.current = val
            this.list()
        },
        dataFormatter(row, column, cellValue, index) {
            return cellValue.replace("T", " ")
        }
    },
    mounted() {
        request({
            url: `/business/fields-by-table-logic?tableLogicName=${this.tableLogicName}`,
            method: 'get'
        }).then((resp) => {
            this.dialog.formStructure = resp.data

            for(let i in resp.data) {
                if (resp.data[i].enableShow) {
                    this.visibleFields.push(resp.data[i])
                }
            }
        })

        this.list()

    }
}