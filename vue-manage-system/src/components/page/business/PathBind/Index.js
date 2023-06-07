import request from "@/utils/request";
import * as buffer from "buffer";
import {f} from "vuedraggable/dist/vuedraggable.common";

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
        edit(row) {
            if (!row.parentId) {
                this.dialog.operate = 'dir'
                this.dialog.title = '修改目录'
            } else {
                this.dialog.operate = 'path'
                this.dialog.title = '修改路径'
            }

            this.dialog.data = JSON.parse(JSON.stringify(row))

            let tableMetaInfoId = row.tableMetaInfoId
            let parentId = row.parentId

            if (!parentId) {
                this.dialog.visible = true
                return
            }

            request({
                url: `/path-bind/query-dir-id?pathId=${parentId}`,
                method: 'get'
            }).then((resp) => {
                this.dirs.records = resp.data
                this.dirs.loading = false
                request({
                    url: `/path-bind/query-table-id?tableId=${tableMetaInfoId}`,
                    method: 'get'
                }).then((resp) => {
                    this.table.records = resp.data
                    this.table.loading = false

                    this.dialog.visible = true
                })
            })
        },
        commit() {
            let opera = ''
            if (!this.dialog.data.id) {
                opera = 'add'
            } else {
                opera = 'modify'
            }

            request({
                url: `/path-bind/${opera}-${this.dialog.operate}`,
                method: 'post',
                data: this.dialog.data
            }).then((resp) => {
                if (resp.code === 200) {
                    this.dialog.visible = false
                    this.$message.success(resp.message)
                    this.list()
                } else {
                    this.$message.error(resp.message)
                }
            })
        },
        changeEnable(row) {
            console.log(row)
            request({
                url: '/path-bind/change-enable',
                method: 'post',
                data: row
            }).then((resp) => {
                if (resp.code === 200) {
                    this.$message.success(resp.message)
                    this.list()
                } else {
                    this.$message.error(resp.message)
                }
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
        },
        viewIcon() {
            window.open("https://element.eleme.cn/#/zh-CN/component/icon", "_blank")
        }
    },
    mounted() {
        this.list()
    }
}