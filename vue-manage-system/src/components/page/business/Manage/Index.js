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
            }
        }
    },
    methods: {
        list() {
            request({
                url: '/business/page',
                method: 'post',
                data: this.page
            }).then((resp) => {
                this.page = resp.data
            })
        },

        addTable() {
            this.$router.push("/business/create-table")
        },
        editBusiness(scope) {
            this.$router.push({
                path: "/business/operate-set",
                query: {
                    tableId: scope.id
                }
            })
        },
        delTable(row) {
            request({
                url: `/business/delete?tableId=${row.id}`,
                method: 'delete'
            }).then((resp) => {
                this.$message.success("删除成功！")
                this.list()
            })
        },


        createTimeFormatter(row, column, cellValue, index) {
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