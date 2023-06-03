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
                formStructure: [],
                formData: {},
                rules: {}
            }
        }
    },
    methods: {
        closeAndReset() {
            this.dialog.visible = false
            this.dialog.formData = JSON.parse(JSON.stringify(this.dialog.emptyFormData))
        }
    },
    mounted() {
        request({
            url: `/business/visible-fields?tableLogicName=${this.tableLogicName}`,
            method: 'get'
        }).then((resp) => {
            this.dialog.formStructure = resp.data
            console.log(this.dialog)
        })
    }
}