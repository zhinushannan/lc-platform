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
                visible: true,
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
        update() {}
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

    }
}