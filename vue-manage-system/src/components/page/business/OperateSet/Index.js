import ChoiceShowField from "@/components/components/business/CreateBusiness/ChoiceShowField.vue";

export default {
    name: "Index",
    components: {ChoiceShowField},
    data() {
        return {
            tableId: this.$route.query.tableId
        }
    },
    created() {
        this.tableId = this.$route.query.tableId
    }
}