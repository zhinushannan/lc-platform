import request from "@/utils/request";

export default {
    name: "Index",
    data() {
        return {
            page: {
                current: 1,
                size: 10,
                records: [],
                total: 0
            },
            dialog: {
                visible: false,
                operate: '',
                title: '',
                data: {},

            },
            dirs: {
                loading: false,
                records: []
            },
            table: {
                loading: false,
                records: []
            }
        }
    },
    methods: {
        list() {
            request({
                url: '/path-bind/page',
                method: 'post',
                data: this.page
            }).then((resp) => {
                this.page = resp.data
            })
        },
        dirsData(query) {
            this.dirs.loading = true
            request({
                url: `/path-bind/query-dir?name=${query}`,
                method: 'get'
            }).then((resp) => {
                this.dirs.records = resp.data
                this.dirs.loading = false
            })
        },
        tableData(query) {
            this.table.loading = true
            request({
                url: `/path-bind/query-table?tableBusinessName=${query}`,
                method: 'get'
            }).then((resp) => {
                this.table.records = resp.data
                this.table.loading = false
            })
        },
        addDir() {
            this.dialog.operate = 'dir'
            this.dialog.title = '新增目录'
            this.dialog.visible = true
        },
        addPath() {
            this.dialog.operate = 'path'
            this.dialog.title = '新增路径'
            this.dialog.visible = true
        },
        commit() {
            request({
                url: `/path-bind/add-${this.dialog.operate}`,
                method: 'post',
                data: this.dialog.data
            }).then((resp) => {
                console.log(resp)
            })
        },

        resetDialog() {
            this.dialog = {
                visible: false,
                operate: '',
                title: '',
                data: {}
            }
        },
        handleClose(done) {
            this.$confirm('关闭后未提交数据将不会被保存，是否关闭？')
                .then(_ => {
                    this.resetDialog()
                    done();
                })
                .catch(_ => {
                });
        },
        dataFormatter(row, column, cellValue, index) {
            return cellValue.replace("T", " ")
        },
        handleCurrentChange(val) {
            this.page.current = val
            this.list()
        }
    },
    mounted() {
        this.list()
    }
}